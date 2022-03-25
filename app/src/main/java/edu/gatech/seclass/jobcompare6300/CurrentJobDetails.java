package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.nio.charset.StandardCharsets;

public class CurrentJobDetails extends AppCompatActivity {

    private EditText titleID;
    private EditText companyID;
    private EditText locationID;
    private EditText colID;
    private EditText ysID;
    private EditText ybID;
    private EditText teleworkID;
    private EditText leaveID;
    private EditText gymID;
    private Button saveButton;
    private Button cancelButton;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job_details);

        titleID = (EditText) findViewById(R.id.titleID);
        companyID = (EditText) findViewById(R.id.companyID);
        locationID = (EditText) findViewById(R.id.locationID);
        colID = (EditText) findViewById(R.id.colID);
        ysID = (EditText) findViewById(R.id.ysID);
        ybID = (EditText) findViewById(R.id.ybID);
        teleworkID = (EditText) findViewById(R.id.teleworkID);
        leaveID = (EditText) findViewById(R.id.leaveID);
        gymID = (EditText) findViewById(R.id.gymID);
        saveButton  = (Button) findViewById(R.id.saveButtonID);
        cancelButton = (Button) findViewById(R.id.cancelButtonID);

        db = new DBHelper(this);

        Cursor res = db.getCurrentJobData();

        if (res != null && (res.getCount() > 0)){
            if(res.moveToFirst()){
                do{
                    titleID.setText(res.getString(res.getColumnIndex("title")));
                    companyID.setText(res.getString(res.getColumnIndex("company")));
                    locationID.setText(res.getString(res.getColumnIndex("location")));
                    colID.setText(res.getString(res.getColumnIndex("col")));
                    ysID.setText(String.format("%.2f", res.getDouble(res.getColumnIndex("salary"))));
                    ybID.setText(String.format("%.2f", res.getDouble(res.getColumnIndex("bonus"))));
                    teleworkID.setText(res.getString(res.getColumnIndex("telework")));
                    leaveID.setText(res.getString(res.getColumnIndex("leave")));
                    gymID.setText(String.format("%.2f", res.getDouble(res.getColumnIndex("gym"))));
                } while (res.moveToNext());
            }

        }

        if(!res.isClosed()){
            res.close();
        }

        if (saveButton != null){
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String titleIDValue = titleID.getText().toString();
                    String companyIDValue = companyID.getText().toString();
                    String locationIDValue = locationID.getText().toString();

                    double ysIDValue = 0;
                    double ybIDValue = 0;
                    int teleworkIDValue = 0;
                    int leaveIDValue = 0;
                    double gymIDValue = 0;
                    int colIDValue = 0;
                    boolean isValid = true;

                    try {
                        ysIDValue = Double.parseDouble(ysID.getText().toString());
                    } catch (NumberFormatException e) {
                        ysID.setError("Invalid yearly salary value");
                        isValid = false;
                    }
                    try {
                        ybIDValue = Double.parseDouble(ybID.getText().toString());
                    } catch (NumberFormatException e) {
                        ybID.setError("Invalid yearly bonus value");
                        isValid = false;
                    }
                    try {
                        teleworkIDValue = Integer.parseInt(teleworkID.getText().toString());
                    } catch (NumberFormatException e) {
                        teleworkID.setError("Invalid remote days value");
                        isValid = false;
                    }
                    try {
                        leaveIDValue = Integer.parseInt(leaveID.getText().toString());
                    } catch (NumberFormatException e) {
                        leaveID.setError("Invalid leave days value");
                        isValid = false;
                    }
                    try {
                        gymIDValue = Double.parseDouble((gymID.getText().toString()));
                    } catch (NumberFormatException e) {
                        gymID.setError("Invalid gym value");
                        isValid = false;
                    }
                    try {
                        colIDValue = Integer.parseInt((colID.getText().toString()));
                    } catch (NumberFormatException e) {
                        colID.setError("Invalid cost of living index value");
                        isValid = false;
                    }

                    if(isValid){
                        Cursor res2 = db.getCurrentJobData();
                        if(res2 != null && (res2.getCount() > 0)){
                            int id = 0;
                            if(res2.moveToFirst()){
                                do{
                                    id  = res2.getInt(res2.getColumnIndex("id"));
                                } while (res2.moveToNext());

                            }
                            db.updateJob(id, titleIDValue, companyIDValue, locationIDValue, colIDValue, ysIDValue, ybIDValue, teleworkIDValue, leaveIDValue, gymIDValue, 1);
                        } else {
                            db.insertJob(titleIDValue, companyIDValue, locationIDValue, colIDValue, ysIDValue, ybIDValue, teleworkIDValue, leaveIDValue, gymIDValue, 1);
                        }

                        if(!res2.isClosed()){
                            res2.close();
                        }

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
            });
        }

        if(cancelButton != null){
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }
    }
}
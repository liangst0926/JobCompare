package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdjustComparisonSettings extends AppCompatActivity {

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
        setContentView(R.layout.activity_adjust_comparison_settings);

        ysID = (EditText) findViewById(R.id.ysComparisonID);
        ybID = (EditText) findViewById(R.id.ybComparisonID);
        teleworkID = (EditText) findViewById(R.id.teleworkComparisonID);
        leaveID = (EditText) findViewById(R.id.leaveComparisonID);
        gymID = (EditText) findViewById(R.id.gymComparisonID);
        saveButton  = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        db = new DBHelper(this);

        Cursor res = db.getComparisonSettingsData();

        if (res != null && (res.getCount() > 0)){
            if(res.moveToFirst()){
                do{
                    ysID.setText(res.getString(res.getColumnIndex("salary")));
                    ybID.setText((res.getString(res.getColumnIndex("bonus"))));
                    teleworkID.setText(res.getString(res.getColumnIndex("telework")));
                    leaveID.setText(res.getString(res.getColumnIndex("leave")));
                    gymID.setText(res.getString(res.getColumnIndex("gym")));
                } while (res.moveToNext());
            }

        } else {
            db.insertComparisonSettings(1, 1, 1, 1, 1);
            ysID.setText("1");
            ybID.setText("1");
            teleworkID.setText(("1"));
            leaveID.setText("1");
            gymID.setText("1");
        }

        if(!res.isClosed()){
            res.close();
        }

        if (saveButton != null){
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int ysIDValue = 0;
                    int ybIDValue = 0;
                    int teleworkIDValue = 0;
                    int leaveIDValue = 0;
                    int gymIDValue = 0;
                    boolean isValid = true;
                    try {
                        ysIDValue = Integer.parseInt(ysID.getText().toString());
                    } catch (NumberFormatException e){
                        ysID.setError("Invalid weight value");
                        isValid = false;
                    }
                    try {
                        ybIDValue = Integer.parseInt(ybID.getText().toString());
                    } catch (NumberFormatException e){
                        ybID.setError("Invalid weight value");
                        isValid = false;
                    }
                    try {
                        teleworkIDValue = Integer.parseInt(teleworkID.getText().toString());
                    } catch (NumberFormatException e){
                        teleworkID.setError("Invalid weight value");
                        isValid = false;
                    }
                    try {
                        leaveIDValue = Integer.parseInt(leaveID.getText().toString());
                    } catch (NumberFormatException e){
                        leaveID.setError("Invalid weight value");
                        isValid = false;
                    }
                    try {
                        gymIDValue = Integer.parseInt((gymID.getText().toString()));
                    } catch (NumberFormatException e){
                        gymID.setError("Invalid weight value");
                        isValid = false;
                    }

                    if(isValid){
                        Cursor res2 = db.getComparisonSettingsData();
                        if(res2 != null && (res2.getCount() > 0)){
                            int id = 0;
                            if(res2.moveToFirst()){
                                do{
                                    id  = res2.getInt(res2.getColumnIndex("id"));
                                } while (res2.moveToNext());

                            }
                            db.updateComparisonSettings(id, ysIDValue, ybIDValue, teleworkIDValue, leaveIDValue, gymIDValue);
                        } else {
                            db.insertComparisonSettings(ysIDValue, ybIDValue, teleworkIDValue, leaveIDValue, gymIDValue);
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
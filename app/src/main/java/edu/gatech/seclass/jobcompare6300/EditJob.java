package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditJob extends AppCompatActivity {

    private Job selectedJob;
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
        setContentView(R.layout.activity_edit_job);

        selectedJob = (Job) getIntent().getSerializableExtra("selectedJob");
        titleID = (EditText) findViewById(R.id.titleEditJobID);
        companyID = (EditText) findViewById(R.id.companyEditJobID);
        locationID = (EditText) findViewById(R.id.locationEditJobID);
        colID = (EditText) findViewById(R.id.colEditJobID);
        ysID = (EditText) findViewById(R.id.ysEditJobID);
        ybID = (EditText) findViewById(R.id.ybEditJobID);
        teleworkID = (EditText) findViewById(R.id.teleworkEditJobID);
        leaveID = (EditText) findViewById(R.id.leaveEditJobID);
        gymID = (EditText) findViewById(R.id.gymEditJobID);
        saveButton  = (Button) findViewById(R.id.saveButtonEditJobID);
        cancelButton = (Button) findViewById(R.id.cancelButtonEditJobID);

        db = new DBHelper(this);

        titleID.setText(selectedJob.getTitle());
        companyID.setText(selectedJob.getCompany());
        locationID.setText(selectedJob.getLocation());
        colID.setText(String.valueOf(selectedJob.getColIndex()));
        ysID.setText(String.format("%.2f", selectedJob.getSalary()));
        ybID.setText(String.format("%.2f", selectedJob.getBonus()));
        teleworkID.setText(String.valueOf(selectedJob.getTelework()));
        leaveID.setText(String.valueOf(selectedJob.getLeave()));
        gymID.setText(String.format("%.2f", selectedJob.getGym()));

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
                        db.updateJob(selectedJob.getId(), titleIDValue, companyIDValue, locationIDValue, colIDValue, ysIDValue, ybIDValue, teleworkIDValue, leaveIDValue, gymIDValue, selectedJob.getCurrentJob());
                        startActivity(new Intent(getApplicationContext(), JobRanking.class));
                    }
                }
            });
        }

        if(cancelButton != null){
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), JobRanking.class));
                }
            });
        }

    }
}
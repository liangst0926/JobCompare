package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EnterJobOffer extends AppCompatActivity {

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
    private Button enterButton;
    private Button compareButton;
    private Button backButton;
    private Button compareCurrentButton;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_offer);

        titleID = (EditText) findViewById(R.id.titleJobOfferID);
        companyID = (EditText) findViewById(R.id.companyJobOfferID);
        locationID = (EditText) findViewById(R.id.locationJobOfferID);
        colID = (EditText) findViewById(R.id.colJobOfferID);
        ysID = (EditText) findViewById(R.id.ysJobOfferID);
        ybID = (EditText) findViewById(R.id.ybJobOfferID);
        teleworkID = (EditText) findViewById(R.id.teleworkJobOfferID);
        leaveID = (EditText) findViewById(R.id.leaveJobOfferID);
        gymID = (EditText) findViewById(R.id.gymJobOfferID);
        saveButton  = (Button) findViewById(R.id.saveButtonJobOfferID);
        cancelButton = (Button) findViewById(R.id.cancelButtonJobOfferID);
        enterButton = (Button) findViewById(R.id.enterButtonJobOfferID);
        compareButton = (Button) findViewById(R.id.compareButtonJobOfferID);
        backButton = (Button) findViewById(R.id.backButtonJobOfferID);
        compareCurrentButton = (Button) findViewById(R.id.compareWithCurrentID);

        db = new DBHelper(this);

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
                        db.insertJob(titleIDValue, companyIDValue, locationIDValue, colIDValue, ysIDValue, ybIDValue, teleworkIDValue, leaveIDValue, gymIDValue, 0);

                        Job job = new Job();
                        job.setCurrentJob(0);
                        job.setTitle(titleIDValue);
                        job.setCompany(companyIDValue);
                        job.setLocation(locationIDValue);
                        job.setColIndex(colIDValue);
                        job.setSalary(ysIDValue);
                        job.setBonus(ybIDValue);
                        job.setTelework(teleworkIDValue);
                        job.setLeave(leaveIDValue);
                        job.setGym(gymIDValue);

                        if(db.getCurrentJob() != null){
                            compareCurrentButton.setEnabled(true);
                            compareCurrentButton.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View view){
                                    ArrayList<Job> selectedJobs = new ArrayList<Job>();
                                    selectedJobs.add(db.getCurrentJob());
                                    selectedJobs.add(job);
                                    Intent intent = new Intent(EnterJobOffer.this, JobComparison.class);
                                    intent.putExtra("selectedJobsList", selectedJobs);
                                    startActivity(intent);
                                }
                            });
                        }

                        if (compareButton != null){
                            compareButton.setEnabled(false);
                            if (db.numberOfRows() >= 2){
                                compareButton.setEnabled(true);
                                compareButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(getApplicationContext(), JobRanking.class));
                                    }
                                });
                            }
                        }
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

        if (enterButton != null){
            enterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), EnterJobOffer.class));
                }
            });
        }

        if (compareButton != null){
            compareButton.setEnabled(false);
            if (db.numberOfRows() >= 2){
                compareButton.setEnabled(true);
                compareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), JobRanking.class));
                    }
                });
            }
        }

        if (backButton != null){
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }

        if(compareCurrentButton != null){
            compareCurrentButton.setEnabled(false);
        }
    }
}
package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class JobComparison extends AppCompatActivity {
    private ArrayList<Job> selectedJobs;

    TextView job1ID;
    TextView job1titleID;
    TextView job1companyID;
    TextView job1locationID;
    TextView job1colID;
    TextView job1ysID;
    TextView job1ybID;
    TextView job1teleworkID;
    TextView job1leaveID;
    TextView job1gymID;

    TextView job2ID;
    TextView job2titleID;
    TextView job2companyID;
    TextView job2locationID;
    TextView job2colID;
    TextView job2ysID;
    TextView job2ybID;
    TextView job2teleworkID;
    TextView job2leaveID;
    TextView job2gymID;

    Button backButtonID;
    Button compareAnotherButtonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_comparison);

        selectedJobs = (ArrayList<Job>) getIntent().getSerializableExtra("selectedJobsList");

        Job job1 = selectedJobs.get(0);
        Job job2 = selectedJobs.get(1);

        job1ID = (TextView) findViewById(R.id.job1ID);
        job1titleID = (TextView) findViewById(R.id.job1titleID);
        job1companyID = (TextView) findViewById(R.id.job1companyID);
        job1locationID = (TextView) findViewById(R.id.job1locationID);
        job1colID = (TextView) findViewById(R.id.job1colID);
        job1ysID = (TextView) findViewById(R.id.job1ysID);
        job1ybID = (TextView) findViewById(R.id.job1ybID);
        job1teleworkID = (TextView) findViewById(R.id.job1teleworkID);
        job1leaveID = (TextView) findViewById(R.id.job1leaveID);
        job1gymID = (TextView) findViewById(R.id.job1gymID);

        job2ID = (TextView) findViewById(R.id.job2ID);
        job2titleID = (TextView) findViewById(R.id.job2titleID);
        job2companyID = (TextView) findViewById(R.id.job2companyID);
        job2locationID = (TextView) findViewById(R.id.job2locationID);
        job2colID = (TextView) findViewById(R.id.job2colID);
        job2ysID = (TextView) findViewById(R.id.job2ysID);
        job2ybID = (TextView) findViewById(R.id.job2ybID);
        job2teleworkID = (TextView) findViewById(R.id.job2teleworkID);
        job2leaveID = (TextView) findViewById(R.id.job2leaveID);
        job2gymID = (TextView) findViewById(R.id.job2gymID);

        backButtonID = (Button) findViewById(R.id.backButtonJobComparisonID);
        compareAnotherButtonID = (Button) findViewById(R.id.anotherComparisonButtonID);

        job1ID.setText("Job " + String.valueOf(job1.getRank()));
        job1titleID.setText(job1.getTitle());
        job1locationID.setText(job1.getLocation());
        job1companyID.setText(job1.getCompany());
        job1colID.setText(String.valueOf(job1.getColIndex()));
        job1ysID.setText(String.format("%.2f", (job1.getSalary() * (100.0 / job1.getColIndex()))));
        job1ybID.setText(String.format("%.2f", (job1.getBonus() * (100.0 / job1.getColIndex()))));
        job1teleworkID.setText(String.valueOf(job1.getTelework()));
        job1leaveID.setText(String.valueOf(job1.getLeave()));
        job1gymID.setText(String.format("%.2f", job1.getGym()));

        job2ID.setText("Job " + String.valueOf(job2.getRank()));
        job2titleID.setText(job2.getTitle());
        job2locationID.setText(job2.getLocation());
        job2companyID.setText(job2.getCompany());
        job2colID.setText(String.valueOf(job2.getColIndex()));
        job2ysID.setText(String.format("%.2f", (job2.getSalary() * ( 100.0 / job2.getColIndex()))));
        job2ybID.setText(String.format("%.2f", (job2.getBonus() * ( 100.0 / job2.getColIndex()))));
        job2teleworkID.setText(String.valueOf(job2.getTelework()));
        job2leaveID.setText(String.valueOf(job2.getLeave()));
        job2gymID.setText(String.format("%.2f", job2.getGym()));

        if(backButtonID != null){
            backButtonID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }

        if(compareAnotherButtonID != null){
            compareAnotherButtonID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), JobRanking.class));
                }
            });
        }

    }
}
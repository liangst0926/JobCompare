package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JobRanking extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    private RecyclerViewAdapter adapter;
    private DBHelper db;
    private Button backButton;
    private Button compareJobButton;
    RecyclerView recyclerView;
    private ArrayList<Job> selectedJobs;
    int index;
    private Button deleteJobButton;
    private Button editJobButton;
    private EditText editJob;
    private EditText deleteJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_ranking);

        db = new DBHelper(this);
        backButton = (Button) findViewById(R.id.backButtonJobRankingID);
        compareJobButton = (Button) findViewById(R.id.compareJobButtonJobRankingID);
        deleteJobButton = (Button) findViewById(R.id.deleteJobButtonID);
        editJobButton = (Button) findViewById(R.id.editJobButtonID);
        editJob  = (EditText) findViewById(R.id.editJobID);
        deleteJob = (EditText) findViewById(R.id.deleteJobID);
        selectedJobs = new ArrayList<Job>();

        Cursor res = db.getComparisonSettingsData();
        int salaryWeight = 0;
        int bonusWeight = 0;
        int teleworkWeight = 0;
        int leaveWeight = 0;
        int gymWeight = 0;

        if (res != null && (res.getCount() > 0)){
            if(res.moveToFirst()){
                do{
                    salaryWeight = Integer.parseInt(res.getString(res.getColumnIndex("salary")));
                    bonusWeight = Integer.parseInt((res.getString(res.getColumnIndex("bonus"))));
                    teleworkWeight = Integer.parseInt(res.getString(res.getColumnIndex("telework")));
                    leaveWeight = Integer.parseInt(res.getString(res.getColumnIndex("leave")));
                    gymWeight = Integer.parseInt(res.getString(res.getColumnIndex("gym")));
                } while (res.moveToNext());
            }
        }

        if(!res.isClosed()){
            res.close();
        }

        ArrayList<Job> jobsList = new ArrayList<Job>();
        jobsList = db.getAllJobs();

        for ( Job job : jobsList){
            job.setRankScore(computeJobScore(job, salaryWeight, bonusWeight, gymWeight, leaveWeight, teleworkWeight));
        }

        Collections.sort(jobsList, new Comparator<Job>(){
            @Override
            public int compare(Job job1, Job job2){
                if(job1.rankScore > job2.rankScore){
                    return 1;
                }
                if(job1.rankScore < job2.rankScore){
                    return -1;
                }
                return 0;
            }
        });

        index = 0;
        for (int i = 0; i < jobsList.size(); i++){
            jobsList.get(i).setRank(i+1);
            if(jobsList.get(i).getCurrentJob() == 1){
                index = i;
            }
        }

        recyclerView = findViewById(R.id.recycler_view);
        setRecyclerView(jobsList);

        if(backButton != null){
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }

        if(compareJobButton != null){
            compareJobButton.setEnabled(false);
        }

        if(deleteJobButton != null){
            ArrayList<Job> finalJobsList = jobsList;
            deleteJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int deleteJobRank = 0;
                    int id = 0;
                    try {
                        deleteJobRank = Integer.parseInt(deleteJob.getText().toString());
                        for (Job job : finalJobsList){
                            if(job.getRank() == deleteJobRank){
                                id = job.getId();
                                db.deleteJob(id);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        }
                    } catch (NumberFormatException e){
                        deleteJob.setError("Invalid job rank");
                    }

                }
            });
        }

        if(editJobButton != null){
            ArrayList<Job> finalJobsList = jobsList;
            editJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int editJobRank = 0;
                    try {
                        editJobRank = Integer.parseInt(editJob.getText().toString());
                        for (Job job : finalJobsList){
                            if(job.getRank() == editJobRank){
                                Intent intent = new Intent(JobRanking.this, EditJob.class);
                                intent.putExtra("selectedJob", job);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e){
                        editJob.setError("Invalid job rank");
                    }

                }
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        if(compareJobButton.isEnabled() && selectedJobs.contains(adapter.getItem(position))){
            selectedJobs.remove(adapter.getItem(position));
            view.setBackgroundColor(Color.WHITE);
            compareJobButton.setEnabled(false);
        }

        if (selectedJobs.size() < 2){
            view.setSelected(!view.isSelected());
            if(view.isSelected()){
                selectedJobs.add(adapter.getItem(position));
                view.setBackgroundColor(Color.CYAN);
            } else {
                selectedJobs.remove(adapter.getItem(position));
                view.setBackgroundColor(Color.WHITE);
            }
            if (selectedJobs.size() == 2){
                compareJobButton.setEnabled(true);
                compareJobButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(JobRanking.this, JobComparison.class);
                        intent.putExtra("selectedJobsList", selectedJobs);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public double computeJobScore(Job job, int salaryWeight, int bonusWeight, int gymWeight, int leaveWeight, int teleworkWeight){
        double weightSum = salaryWeight + bonusWeight + gymWeight + leaveWeight + teleworkWeight;
        double salary = job.getSalary() * (100/job.getColIndex());
        double bonus = job.getBonus()  * (100/job.getColIndex());
        double gym = job.getGym();
        int leave = job.getLeave();
        int telework = job.getTelework();
        double score = 0;

        score = (double)salaryWeight/weightSum * salary + (double)bonusWeight/weightSum * bonus + (double)gymWeight/weightSum * gym + (double)leaveWeight/weightSum * (leave * salary / 260.0) - (double)teleworkWeight/weightSum * ((260.0 - 52.0 * telework) * (salary / 260.0) / 8.0);
        return score;
    }

    private void setRecyclerView(ArrayList<Job> jobsList){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, jobsList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

}
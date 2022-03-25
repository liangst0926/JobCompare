package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button currentJobButtonID;
    private Button jobOffersButtonID;
    private Button comparisonSettingsButtonID;
    private Button compareJobOffersButtonID;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        currentJobButtonID = (Button) findViewById(R.id.currentJobButton);
        jobOffersButtonID = (Button) findViewById(R.id.jobOffersButton);
        comparisonSettingsButtonID = (Button) findViewById(R.id.comparisonSettingsButton);
        compareJobOffersButtonID = (Button) findViewById(R.id.compareJobOffersButton);

        if (currentJobButtonID != null){
            currentJobButtonID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), CurrentJobDetails.class));
                }
            });
        }

        if (jobOffersButtonID != null){
            jobOffersButtonID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), EnterJobOffer.class));
                }
            });
        }

        if (comparisonSettingsButtonID != null){
            comparisonSettingsButtonID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), AdjustComparisonSettings.class));
                }
            });
        }

        if(compareJobOffersButtonID != null){
            compareJobOffersButtonID.setEnabled(false);
            if (db.numberOfRows() >= 2){
                compareJobOffersButtonID.setEnabled(true);
                compareJobOffersButtonID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), JobRanking.class));
                    }
                });
            }
        }
    }
}
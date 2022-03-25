package edu.gatech.seclass.jobcompare6300;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "jobCompare6300.db";
    public static final String TITLE_COLUMN = "title";
    public static final String COMPANY_COLUMN = "company";
    public static final String LOCATION_COLUMN = "location";
    public static final String COL_COLUMN = "col";
    public static final String SALARY_COLUMN = "salary";
    public static final String BONUS_COLUMN = "bonus";
    public static final String TELEWORK_COLUMN = "telework";
    public static final String LEAVE_COLUMN = "leave";
    public static final String GYM_COLUMN = "gym";
    public static final String ID_COLUMN = "id";
    public static final String CURRENT_JOB_COLUMN = "currentJob";
    public static final String JOBS_TABLE = "jobs";
    public static final String COMPARISON_SETTINGS_TABLE = "comparisonSettings";

    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table jobs (id integer primary key, title text, company text, location text, col integer, salary double, bonus double, telework integer, leave integer, gym double, currentJob integer)");
        sqLiteDatabase.execSQL("create table comparisonSettings(id integer primary key, salary integer, bonus integer, telework integer, leave integer, gym integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS jobs");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS comparisonSettings");
        onCreate(sqLiteDatabase);
    }

    public boolean insertJob(String title, String company, String location, int col, double salary, double bonus, int telework, int leave, double gym, int currentJob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_COLUMN, title);
        contentValues.put(COMPANY_COLUMN, company);
        contentValues.put(LOCATION_COLUMN, location);
        contentValues.put(COL_COLUMN, col);
        contentValues.put(SALARY_COLUMN, salary);
        contentValues.put(BONUS_COLUMN, bonus);
        contentValues.put(TELEWORK_COLUMN, telework);
        contentValues.put(LEAVE_COLUMN, leave);
        contentValues.put(GYM_COLUMN, gym);
        contentValues.put(CURRENT_JOB_COLUMN, currentJob);
        db.insert(JOBS_TABLE, null, contentValues);
        return true;
    }

    public boolean updateJob(int id, String title, String company, String location, int col, double salary, double bonus, int telework, int leave, double gym, int currentJob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_COLUMN, title);
        contentValues.put(COMPANY_COLUMN, company);
        contentValues.put(LOCATION_COLUMN, location);
        contentValues.put(COL_COLUMN, col);
        contentValues.put(SALARY_COLUMN, salary);
        contentValues.put(BONUS_COLUMN, bonus);
        contentValues.put(TELEWORK_COLUMN, telework);
        contentValues.put(LEAVE_COLUMN, leave);
        contentValues.put(GYM_COLUMN, gym);
        contentValues.put(CURRENT_JOB_COLUMN, currentJob);
        db.update(JOBS_TABLE, contentValues, "id = ?", new String[] {Integer.toString(id)});
        return true;
    }

    public boolean deleteJob(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(JOBS_TABLE, "id = ?", new String[] {Integer.toString(id)});
        return true;
    }

    public boolean insertComparisonSettings(double salary, double bonus, int telework, int leave, double gym){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SALARY_COLUMN, salary);
        contentValues.put(BONUS_COLUMN, bonus);
        contentValues.put(TELEWORK_COLUMN, telework);
        contentValues.put(LEAVE_COLUMN, leave);
        contentValues.put(GYM_COLUMN, gym);
        db.insert(COMPARISON_SETTINGS_TABLE, null, contentValues);
        return true;
    }

    public boolean updateComparisonSettings(int id, double salary, double bonus, int telework, int leave, double gym){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("salary", salary);
        contentValues.put("bonus", bonus);
        contentValues.put("telework", telework);
        contentValues.put("leave", leave);
        contentValues.put("gym", gym);
        db.update(COMPARISON_SETTINGS_TABLE, contentValues, "id = ?", new String[] {Integer.toString(id)});
        return true;
    }

    public Cursor getJobData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from jobs where id =" + id + "", null);
        return res;
    }

    public Cursor getCurrentJobData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from jobs where currentJob = 1", null);
        return res;
    }

    public Job getCurrentJob(){
        Job job = new Job();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from jobs where currentJob = 1", null);

        try {
            if(cursor.moveToFirst()){
                do{
                    job.setTitle(cursor.getString(cursor.getColumnIndex(TITLE_COLUMN)));
                    job.setCompany(cursor.getString(cursor.getColumnIndex(COMPANY_COLUMN)));
                    job.setLocation(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN)));
                    job.setColIndex(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_COLUMN))));
                    job.setTelework(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TELEWORK_COLUMN))));
                    job.setLeave(Integer.parseInt(cursor.getString(cursor.getColumnIndex(LEAVE_COLUMN))));
                    job.setSalary(Double.parseDouble(String.valueOf(cursor.getDouble(cursor.getColumnIndex(SALARY_COLUMN)))));
                    job.setBonus(Double.parseDouble(String.valueOf(cursor.getDouble(cursor.getColumnIndex(BONUS_COLUMN)))));
                    job.setGym(Double.parseDouble(String.valueOf(cursor.getString(cursor.getColumnIndex(GYM_COLUMN)))));
                    job.setCurrentJob(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CURRENT_JOB_COLUMN))));
                    job.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COLUMN))));
                } while (cursor.moveToNext());
            } else {
                return null;
            }
        } finally {
            try {cursor.close();} catch (Exception ignore){}
        }

        return job;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, JOBS_TABLE);
        return numRows;
    }

    public Cursor getComparisonSettingsData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from comparisonSettings", null);
        return res;
    }

    public ArrayList<Job> getAllJobs(){
        ArrayList<Job> list = new ArrayList<Job>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from jobs", null);
        try {
            if(cursor.moveToFirst()){
                do{
                    Job job = new Job();
                    job.setTitle(cursor.getString(cursor.getColumnIndex(TITLE_COLUMN)));
                    job.setCompany(cursor.getString(cursor.getColumnIndex(COMPANY_COLUMN)));
                    job.setLocation(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN)));
                    job.setColIndex(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_COLUMN))));
                    job.setTelework(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TELEWORK_COLUMN))));
                    job.setLeave(Integer.parseInt(cursor.getString(cursor.getColumnIndex(LEAVE_COLUMN))));
                    job.setSalary(Double.parseDouble(String.valueOf(cursor.getDouble(cursor.getColumnIndex(SALARY_COLUMN)))));
                    job.setBonus(Double.parseDouble(String.valueOf(cursor.getDouble(cursor.getColumnIndex(BONUS_COLUMN)))));
                    job.setGym(Double.parseDouble(String.valueOf(cursor.getString(cursor.getColumnIndex(GYM_COLUMN)))));
                    job.setCurrentJob(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CURRENT_JOB_COLUMN))));
                    job.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COLUMN))));
                    list.add(job);
                } while (cursor.moveToNext());
            }
        } finally {
            try {cursor.close();} catch (Exception ignore){}
        }
        return list;

    }

}

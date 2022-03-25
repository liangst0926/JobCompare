package edu.gatech.seclass.jobcompare6300;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Job implements Serializable {

    double salary;
    double bonus;
    int colIndex;
    double gym;
    int telework;
    int leave;
    String company;
    String location;
    double rankScore;
    int rank;
    String title;
    int currentJob;
    int id;

    public Job() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(int currentJob) {
        this.currentJob = currentJob;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public int getTelework() {
        return telework;
    }

    public void setTelework(int telework) {
        this.telework = telework;
    }

    public double getBonus() {
        return bonus;
    }

    public double getGym() {
        return gym;
    }

    public void setGym(double gym) {
        this.gym = gym;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public String getLocation() {
        return location;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRankScore(double rankScore){
        this.rankScore = rankScore;
    }

    public double getRankScore(){
        return rankScore;
    }

}

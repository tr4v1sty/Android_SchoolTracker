package com.travis.c196_project.Models;

public class Assessment {

    private long assessmentId;
    private long courseId;
    private String assessmentName;
    private String assessmentType;
    private String assessmentGoalDate;

    public Assessment() {
    }

    @Override
    public String toString() {
        return assessmentName + "\n Date "
                + assessmentGoalDate
                + "\n Type: "
                + assessmentType;
    }

    public long getAssessmentId() {
        return assessmentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public String getAssessmentGoalDate() {
        return assessmentGoalDate;
    }

    public int getAssessmentNotification() {
        return 1;
    }

    public void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setAssessmentGoalDate(String assessmentGoalDate) {
        this.assessmentGoalDate = assessmentGoalDate;
    }
}

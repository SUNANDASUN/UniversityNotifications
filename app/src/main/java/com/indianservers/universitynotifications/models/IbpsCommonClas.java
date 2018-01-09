package com.indianservers.universitynotifications.models;

/**
 * Created by Prabhu on 04-01-2018.
 */

public class IbpsCommonClas
{
    public IbpsCommonClas(){

    }

    public IbpsCommonClas(String examName, String examId) {
        this.examName = examName;
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    private String examName,examId;
}

package com.indianservers.universitynotifications.classes;


public class SetClass {
    public int setId, topicId, subjectID, setStatus, settotalQuestions;
    public String setName;

    public SetClass() {
    }

    public SetClass(int prmkey, int topicId, int setId, String setName) {
        this.setId = setId;
        this.topicId = topicId;
        this.subjectID = subjectID;
        this.setStatus = setStatus;
        this.settotalQuestions = settotalQuestions;
        this.setName = setName;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getSetStatus() {
        return setStatus;
    }

    public void setSetStatus(int setStatus) {
        this.setStatus = setStatus;
    }

    public int getSettotalQuestions() {
        return settotalQuestions;
    }

    public void setSettotalQuestions(int settotalQuestions) {
        this.settotalQuestions = settotalQuestions;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }
}

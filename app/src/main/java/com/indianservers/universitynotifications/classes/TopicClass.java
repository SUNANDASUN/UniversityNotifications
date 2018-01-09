package com.indianservers.universitynotifications.classes;

/**
 * Created by hp on 29-May-17.
 */

public class TopicClass {
    public int TopicId, subjectId, topicStatus, totalQuestions;
    public String TopicName;

    public TopicClass() {

    }

    public TopicClass(int topicId, int subjectId, int topicStatus, int totalQuestions, String topicName) {
        TopicId = topicId;
        this.subjectId = subjectId;
        this.topicStatus = topicStatus;
        this.totalQuestions = totalQuestions;
        TopicName = topicName;
    }

    public int getTopicId() {
        return TopicId;
    }

    public void setTopicId(int topicId) {
        TopicId = topicId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTopicStatus() {
        return topicStatus;
    }

    public void setTopicStatus(int topicStatus) {
        this.topicStatus = topicStatus;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }
}


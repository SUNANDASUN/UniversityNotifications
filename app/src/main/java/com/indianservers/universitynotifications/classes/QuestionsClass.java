package com.indianservers.universitynotifications.classes;

/**
 * Created by hp on 30-May-17.
 */

public class QuestionsClass {
    private int qid, setid, tid, sid, correctAnswer, isfav;
    private String question;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;

    public String getOpt5() {
        return opt5;
    }

    public void setOpt5(String opt5) {
        this.opt5 = opt5;
    }

    private String opt5;
    private String desc;

    public QuestionsClass(String q, String opt1, String opt2, String opt3, String opt4,String opt5, String CA) {
        Q = q;
        Opt1 = opt1;
        Opt2 = opt2;
        Opt3 = opt3;
        Opt4 = opt4;
        opt5 = opt5;
        this.CA = CA;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getCA() {
        return CA;
    }

    public void setCA(String CA) {
        this.CA = CA;
    }

    private String Q,Opt1,Opt2,Opt3,Opt4,CA;

    public QuestionsClass() {
    }

    public QuestionsClass(int qid, int setid, int tid, int correctAnswer, int isfav, String question, String opt1, String opt2, String opt3, String opt4, String description) {
        this.qid = qid;
        this.setid = setid;
        this.tid = tid;
        this.sid = sid;
        this.correctAnswer = correctAnswer;
        this.isfav = isfav;
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;

        this.opt4 = opt4;
        this.desc = description;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getSetid() {
        return setid;
    }

    public void setSetid(int setid) {
        this.setid = setid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getIsfav() {
        return isfav;
    }

    public void setIsfav(int isfav) {
        this.isfav = isfav;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }
}

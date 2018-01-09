package com.indianservers.universitynotifications.adapters;

/**
 * Created by Prabhu on 26-12-2017.
 */

public class SingleGridview {
    public SingleGridview(){

    }
    public SingleGridview(String qno) {
        this.qno = qno;
    }

    public String getQno() {
        return qno;
    }

    public void setQno(String qno) {
        this.qno = qno;
    }

    private String qno;

}

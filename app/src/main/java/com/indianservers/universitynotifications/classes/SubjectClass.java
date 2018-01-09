package com.indianservers.universitynotifications.classes;

/**
 * Created by hp on 29-May-17.
 */

public class SubjectClass {
    public int id;
    public String SName;

    public SubjectClass() {

    }

    public SubjectClass(int id1, String sname) {
        this.id = id1;
        this.SName = sname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }
}

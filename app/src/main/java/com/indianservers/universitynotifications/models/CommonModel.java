package com.indianservers.universitynotifications.models;

/**
 * Created by JNTUH on 28-10-2017.
 */

public class CommonModel {


    public String getNotificationname() {
        return notificationname;
    }

    public void setNotificationname(String notificationname) {
        this.notificationname = notificationname;
    }

    public String getNotificationdate() {
        return notificationdate;
    }

    public void setNotificationdate(String notificationdate) {
        this.notificationdate = notificationdate;
    }

    private String notificationname;
    private String notificationdate;
    private String image;
    private String notificationdesc;

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    private String weblink;


    public String getNotificationdesc() {
        return notificationdesc;
    }

    public void setNotificationdesc(String notificationdesc) {
        this.notificationdesc = notificationdesc;
    }



    public CommonModel(String notificationname, String notificationdate, String notificationdesc, String image,String weblink) {
        this.notificationname = notificationname;
        this.notificationdate = notificationdate;
        this.notificationdesc = notificationdesc;
        this.image = image;
        this.weblink = weblink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public CommonModel(){

    }
}

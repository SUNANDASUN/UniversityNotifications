package com.indianservers.universitynotifications;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 5/26/2017.
 */

public class MyHolder {

    public TextView notification, notdate,desc, imgurl,weblink;
    public MyHolder(View itemView) {


        notification = (TextView) itemView.findViewById(R.id.subject);
        notdate = (TextView) itemView.findViewById(R.id.ndate);
        desc = (TextView)itemView.findViewById(R.id.desc);
        imgurl = (TextView)itemView.findViewById(R.id.imglist);
        weblink = (TextView)itemView.findViewById(R.id.weblink);


    }
}

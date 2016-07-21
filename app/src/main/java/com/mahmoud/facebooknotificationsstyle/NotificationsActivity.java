package com.mahmoud.facebooknotificationsstyle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.mahmoud.Adapter.NotificationsAdapter;
import com.mahmoud.Model.NotificationModel;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    private ListView lvNotifications;
    private ArrayList<NotificationModel> listNotifications;
    private NotificationsAdapter adapter;
    private ArrayList<String> listDates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        init();
        getNotificationsData();
        setAdapter();
    }

    private void init() {
        lvNotifications = (ListView) findViewById(R.id.lv_notifications);
    }

    private void getNotificationsData(){
        listNotifications = new ArrayList<>();

        listDates.add("2016-7-21 16:44:00");
        listDates.add("2016-7-21 16:30:00");
        listDates.add("2016-7-21 15:00:00");
        listDates.add("2016-7-21 8:00:00");
        listDates.add("2016-7-21 00:00:00");
        listDates.add("2016-7-20 16:00:00");
        listDates.add("2016-7-20 11:00:00");
        listDates.add("2016-7-18 11:00:00");
        listDates.add("2016-6-25 11:00:00");
        listDates.add("2015-5-5 11:00:00");

        for (int i = 0; i < 10; i++) {
            NotificationModel notificationModel = new NotificationModel();
            notificationModel.setId(i);
            notificationModel.setBody(i + 1 + ": " + getResources().getString(R.string.test_body));
            notificationModel.setDate(listDates.get(i));
            notificationModel.setImage("");
            notificationModel.setIcon("");
            if(i % 3 == 0)
                notificationModel.setIsSeen(true);
            else
                notificationModel.setIsSeen(false);

            listNotifications.add(notificationModel);
        }
    }

    private void setAdapter() {
        adapter = new NotificationsAdapter(this, listNotifications);
        lvNotifications.setAdapter(adapter);

    }
}

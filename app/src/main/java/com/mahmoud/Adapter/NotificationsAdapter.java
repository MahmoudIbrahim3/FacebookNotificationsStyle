package com.mahmoud.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mahmoud.Control.Utilities;
import com.mahmoud.Model.NotificationModel;
import com.mahmoud.facebooknotificationsstyle.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ibrahim on 3/17/2016.
 */
public class NotificationsAdapter extends BaseAdapter{


    private final ArrayList<NotificationModel> listNotifications;
    Activity activity;
    private LayoutInflater inflater;
    private String TAG = "NotificationsAdapter";

    public NotificationsAdapter(Activity activity, ArrayList<NotificationModel> listNotifications){
        this.activity = activity;
        this.listNotifications = listNotifications;

        Utilities.initParameters(activity);

    }

    @Override
    public int getCount() {
        return listNotifications.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.activity_notifications_item, parent, false);

        return fillView(position, convertView);
    }

    private View fillView(int position, View view){
        TextView tvBody = (TextView) view.findViewById(R.id.tv_body);
        TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        LinearLayout llBackground = (LinearLayout) view.findViewById(R.id.ll_background);

        tvBody.setText(listNotifications.get(position).getBody());

        tvDate.setText(Utilities.getDate(activity, listNotifications.get(position).getDate()));

        Boolean isSeen = listNotifications.get(position).getIsSeen();
        if(isSeen) {
            llBackground.setBackgroundResource(R.color.blue_light_back_list_item);
            tvDate.setTextColor(activity.getResources().getColor(R.color.blue_date_color));
        }
        else {
            llBackground.setBackgroundResource(R.color.white);
            tvDate.setTextColor(activity.getResources().getColor(R.color.gray_date_color));
        }

        return view;
    }
}

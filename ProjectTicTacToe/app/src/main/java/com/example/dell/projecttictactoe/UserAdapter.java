package com.example.dell.projecttictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> lstUser;

    public  UserAdapter(Context context, ArrayList<User> lstUser)
    {
        this.context = context;
        this.lstUser = lstUser;
    }

    @Override
    public int getCount() {
        return lstUser.size();
    }

    @Override
    public Object getItem(int position) {
        return lstUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.user_adapter, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.userName);

        User user = new User();
        user = lstUser.get(position);

        tv.setText(user.getUsername().toString());

        return convertView;
    }
}

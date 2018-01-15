package com.example.trantrunghieu.chatchit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.trantrunghieu.chatchit.R;
import com.example.trantrunghieu.chatchit.models.Friend;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Tran Trung Hieu on 1/13/18.
 */

public class AdapterFriend extends ArrayAdapter<Friend> {

    public AdapterFriend(Context context, int resource, List<Friend> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.itemfriend, null);
        }
        Friend p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txvUsername = (TextView) view.findViewById(R.id.name);
            TextView txvFullname = (TextView) view.findViewById(R.id.message);
            ImageView img = (ImageView)view.findViewById(R.id.image);
            txvUsername.setText(p.getUsername());
            txvFullname.setText(p.getFullName());
            Picasso.with(getContext()).load(p.getImage()).into(img);
        }
        return view;
    }
}
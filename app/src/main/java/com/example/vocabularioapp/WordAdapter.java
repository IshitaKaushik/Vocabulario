package com.example.vocabularioapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<Word> {
    private int mResourceId;
    public WordAdapter(Context context, ArrayList<Word> objects,int resourceId) {
        super(context, 0, objects);
        mResourceId=resourceId;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        Word current =getItem(position);
        View listItem =convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        TextView miwokTextView =(TextView) listItem.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(current.getMiwokTranslation());
        TextView defaultTextView =(TextView) listItem.findViewById(R.id.default_text_view);
        defaultTextView.setText(current.getDefaultTranslation());
        ImageView imageView=(ImageView) listItem.findViewById(R.id.asset_image_view);
        if(current.getIsImage())
            imageView.setImageResource(current.getImageId());
        else
            imageView.setVisibility(View.GONE);
        View textCont=listItem.findViewById(R.id.linear_layout_id);
        int color= ContextCompat.getColor(getContext(),mResourceId);
        textCont.setBackgroundColor(color);


        return listItem;
    }
}


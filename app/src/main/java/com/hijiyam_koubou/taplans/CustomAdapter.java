package com.hijiyam_koubou.taplans;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

 //   int[] colors;
    ArrayList<GoogleCalendarColors> googleCalendarColorList;


    public CustomAdapter(Context context, int resource, String[] strings, ArrayList<GoogleCalendarColors> googleCalendarColorList) {

        super( context, resource, strings );
        this.googleCalendarColorList = googleCalendarColorList;
      //  this.colors = colors;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent ) {

        if( convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from( getContext() );
            convertView = inflater.inflate( android.R.layout.simple_spinner_dropdown_item, parent, false );
            //** android.R.layout.simple_spinner_dropdown_item で inflate **//
        }

        this.setCustomTextView( (TextView) convertView, position );
        return convertView;
    }


    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {

        if( convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from( getContext() );
            convertView = inflater.inflate( android.R.layout.simple_spinner_item, parent, false );
            //** android.R.layout.sinple_spinner_item で inflate **//
        }

        this.setCustomTextView( (TextView) convertView, position );

        return convertView;
    }


    //** Spinnerの中身のTextViewを作る **//
    private void setCustomTextView( TextView textView, int position ) {

        textView.setText( super.getItem( position ) );
        GoogleCalendarColors gcItem = googleCalendarColorList.get(position);
        textView.setBackgroundColor(Color.parseColor(gcItem.HEXStr));
        textView.setTextColor(Color.parseColor(gcItem.FontColorStr));
    }
}


/*
* Spinnerの文字色をJavaで指定する     https://qiita.com/ztrehagem/items/aa76f07f96deb3bf4784
*
* */
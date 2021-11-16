package com.example.sctapp;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class studentListAdapter2 extends ArrayAdapter<student> {

    private Context mContext;
    int mResource;

    public studentListAdapter2(Context context, int resource, List<student> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    public View getView(int position, View convertView, ViewGroup parent){

        String name = getItem(position).getName();
        String grade = getItem(position).getGrade();
        String student_id = getItem(position).getStudent_id();

        student student = new student(name, grade, student_id);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        if (name == "Name") {
            TextView studentName = (TextView) convertView.findViewById(R.id.textView1);
            studentName.setTypeface(null, Typeface.BOLD);
            TextView studentGrade = (TextView) convertView.findViewById(R.id.textView2);
            studentGrade.setTypeface(null, Typeface.BOLD);
            TextView studentid = (TextView) convertView.findViewById(R.id.textView3);
            studentid.setTypeface(null, Typeface.BOLD);
            studentName.setText(name);
            studentGrade.setText(grade);
            studentid.setText(student_id);
        }
        else {
            TextView studentName = (TextView) convertView.findViewById(R.id.textView1);
            TextView studentGrade = (TextView) convertView.findViewById(R.id.textView2);
            TextView studentid = (TextView) convertView.findViewById(R.id.textView3);
            studentName.setText(name);
            studentGrade.setText(grade);
            studentid.setText(student_id);
        }

        return convertView;
    }
}

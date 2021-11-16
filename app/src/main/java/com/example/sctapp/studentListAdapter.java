package com.example.sctapp;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class studentListAdapter extends ArrayAdapter<student> {

    private Context mContext;
    int mResource;

    public studentListAdapter(Context context, int resource, List<student> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    public View getView(int position, View convertView, ViewGroup parent){

        String name = getItem(position).getName();
        String grade = getItem(position).getGrade();
        String bus_number = getItem(position).getBus_number();
        String seat_number = getItem(position).getSeat_number();
        String student_id = getItem(position).getStudent_id();
        boolean highlight = getItem(position).getHighlight();

        student student = new student(name, grade, bus_number, seat_number, student_id, highlight);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        if (name == "Name") {
            TextView studentName = (TextView) convertView.findViewById(R.id.textView1);
            studentName.setTypeface(null, Typeface.BOLD);
            TextView studentGrade = (TextView) convertView.findViewById(R.id.textView2);
            studentGrade.setTypeface(null, Typeface.BOLD);
            TextView studentBustNumber = (TextView) convertView.findViewById(R.id.textView3);
            studentBustNumber.setTypeface(null, Typeface.BOLD);
            TextView studentid = (TextView) convertView.findViewById(R.id.textView4);
            studentid.setTypeface(null, Typeface.BOLD);
            studentName.setText(name);
            studentGrade.setText(grade);
            studentBustNumber.setText(bus_number);
            studentid.setText(student_id);
        }
        else if(highlight == true){
            TextView studentName = (TextView) convertView.findViewById(R.id.textView1);
            studentName.setTypeface(null, Typeface.BOLD);
            TextView studentGrade = (TextView) convertView.findViewById(R.id.textView2);
            studentGrade.setTypeface(null, Typeface.BOLD);
            TextView studentBustNumber = (TextView) convertView.findViewById(R.id.textView3);
            studentBustNumber.setTypeface(null, Typeface.BOLD);
            TextView studentid = (TextView) convertView.findViewById(R.id.textView4);
            studentid.setTypeface(null, Typeface.BOLD);
            name = "<span style='background-color:yellow'>" + name + "</span>";
            studentName.setText(Html.fromHtml(name));
            grade = "<span style='background-color:yellow'>" + grade + "</span>";
            studentGrade.setText(Html.fromHtml(grade));
            bus_number = "<span style='background-color:yellow'>" + bus_number + "</span>";
            studentBustNumber.setText(Html.fromHtml(bus_number));
            student_id = "<span style='background-color:yellow'>" + student_id + "</span>";
            studentid.setText(Html.fromHtml(student_id));
        }
        else {
            TextView studentName = (TextView) convertView.findViewById(R.id.textView1);
            TextView studentGrade = (TextView) convertView.findViewById(R.id.textView2);
            TextView studentBustNumber = (TextView) convertView.findViewById(R.id.textView3);
            TextView studentid = (TextView) convertView.findViewById(R.id.textView4);
            studentName.setText(name);
            studentGrade.setText(grade);
            studentBustNumber.setText(bus_number);
            studentid.setText(student_id);
        }


        return convertView;
    }
}

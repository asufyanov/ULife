package com.example.user.ulife;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.koushikdutta.ion.Ion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by user on 20.03.2015.
 */
public class EventListAdapter extends BaseAdapter {
    List<Events> events;
    LayoutInflater inflater;


    public EventListAdapter(Context context, List<Events>events){
        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.events=events;
    }

    @Override
    public int getCount() {
        return events.size();
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
    public View getView(int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder=null;
        if (convertView==null){
            Log.d("viewTest", "pos="+position);

            convertView=inflater.inflate(R.layout.event_item, null);
            viewHolder=new ViewHolder();

            viewHolder.dateView=(TextView)convertView.findViewById(R.id.dateView);
            viewHolder.nameView=(TextView)convertView.findViewById(R.id.nameView);
            viewHolder.priceView=(TextView)convertView.findViewById(R.id.priceView);
            viewHolder.organizationView=(TextView)convertView.findViewById(R.id.organizationView);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
            //viewHolder.dayTimeView=(TextView)convertView.findViewById(R.id.dayTimeView);
            viewHolder.addressView=(TextView)convertView.findViewById(R.id.addressView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)convertView.getTag();
            Log.d("viewTest", "+pos="+position);


        }



        Date date=events.get(position).getDate();



        Calendar calendar= Calendar.getInstance();


        DateFormat df = new SimpleDateFormat("MMMMMMM dd, yyyy, kk:mm", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        String subdateStr = df.format(date);




        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.get(Calendar.DAY_OF_MONTH);
        String monthString;
        String dayTimeString="";


        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH)-1;
        int dayOfweek=calendar.get(Calendar.DAY_OF_WEEK)-3;






        switch (month) {
            case 1:  monthString = "Января";            break;
            case 2:  monthString = "Февраля";           break;
            case 3:  monthString = "Марта";             break;
            case 4:  monthString = "Апреля";            break;
            case 5:  monthString = "Мая";               break;
            case 6:  monthString = "Июня";              break;
            case 7:  monthString = "Июля";              break;
            case 8:  monthString = "Августа";           break;
            case 9:  monthString = "Сентября";          break;
            case 10: monthString = "Октября";           break;
            case 11: monthString = "Ноября";            break;
            case 12: monthString = "Декабря";           break;
            default: monthString = "Invalid month";     break;
        }
        switch (dayOfweek+1) {
            case 1:  dayTimeString+= "Понедельник";     break;
            case 2:  dayTimeString+= "Вторник";         break;
            case 3:  dayTimeString+= "Среда";           break;
            case 4:  dayTimeString+= "Четверг";         break;
            case 5:  dayTimeString+= "Пятница";         break;
            case 6:  dayTimeString+= "Суббоота";        break;
            case 7:  dayTimeString+= "Воскресенье";     break;
            case 8:  dayTimeString+= "Августа";         break;
            case 9:  dayTimeString+= "Сентября";        break;
            case 10: dayTimeString+= "Октября";         break;
            case 11: dayTimeString+= "Ноября";          break;
            case 12: dayTimeString+= "Декабря";         break;
            default: dayTimeString+= "Invalid month";   break;
        }


        Log.d("TAG", "AAAAAAAAAAAAA");
        viewHolder.organizationView.setText(events.get(position).getParseObject("organization").getString("name"));
        viewHolder.nameView.setText(events.get(position).getName());
        viewHolder.dateView.setText(events.get(position).getDate().toGMTString());
        viewHolder.priceView.setText(""+events.get(position).getPrice()+" tenge");
        //viewHolder.dayTimeView.setText(dayTimeString);
        viewHolder.addressView.setText(events.get(position).getAddress());

     //   Ion.with(viewHolder.imageView).load(events.get(position).getParseFile("image").getUrl());

        //viewHolder.imageView.setImageResource(R.drawable.bg);

        Ion.with(parent.getContext())
                .load(events.get(position).getParseFile("image").getUrl())
                .withBitmap()
                .placeholder(R.drawable.loading)
                .intoImageView(viewHolder.imageView);


        return convertView;

    }

    private class ViewHolder{
        TextView dateView;
        TextView nameView;
        TextView priceView;
        TextView organizationView;
        ImageView imageView;
        //TextView dayTimeView;
        TextView addressView;

    }
}

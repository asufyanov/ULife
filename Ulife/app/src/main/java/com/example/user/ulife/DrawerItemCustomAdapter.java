package com.example.user.ulife;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 30.06.15.
 */
public class DrawerItemCustomAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ObjectDrawerItem> data=null;

    public DrawerItemCustomAdapter(Context context, List<ObjectDrawerItem> data){
        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.data=data;

        Log.d("Drawer", "data size="+data.size());

    }

    @Override
    public int getCount() {
        return data.size();
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

        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.navdrawer_item, null);
            viewHolder=new ViewHolder();
            viewHolder.imageViewIcon=(ImageView)convertView.findViewById(R.id.imageViewIcon);
            viewHolder.textViewName=(TextView)convertView.findViewById(R.id.textViewName);



        } else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.imageViewIcon.setImageResource(data.get(position).icon);
        viewHolder.textViewName.setText(data.get(position).name);

        Log.d("Drawer", "name"+data.get(position).name);


        return convertView;

    }

    private class ViewHolder{
        ImageView imageViewIcon;
        TextView textViewName;

    }
}

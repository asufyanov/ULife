package com.example.user.ulife;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by user on 26.06.15.
 */
public class OrganizationListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<Organization> organizations;
    MainActivity parentActivity;



    public OrganizationListAdapter(Context context, List<Organization> organizations){

        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        parentActivity=(MainActivity)context;

        this.organizations=organizations;
        Log.d("listAdapter", this.organizations.toString());
        Log.d("listAdapter", parentActivity.myFollowedOrganizations.toString());




    }
    @Override
    public int getCount() {
        return organizations.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder=null;

        if (convertView==null){
            Log.d("viewTest", "pos="+position);
            convertView=inflater.inflate(R.layout.organization_item, null);
            viewHolder=new ViewHolder();
            viewHolder.nameView=(TextView)convertView.findViewById(R.id.addressView);
            viewHolder.isFollowed=(Button)convertView.findViewById(R.id.isFollowed);
            viewHolder.shortDesc=(TextView)convertView.findViewById(R.id.shortDesc);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);






        } else {
            viewHolder=(ViewHolder)convertView.getTag();
            Log.d("viewTest", "+pos="+position);

        }

        viewHolder.nameView.setText(organizations.get(position).getName());
        /*if (parentActivity==null) Log.d("listAdapter", "parentnull");
        if (parentActivity.myFollowedOrganizations==null) Log.d("listAdapter", "2nd null");*/
        if (parentActivity.followedOrgIDs.contains(organizations.get(position).getObjectId())) viewHolder.isFollowed.setText("Отписаться");
        else  viewHolder.isFollowed.setText("Подписаться");

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.isFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parentActivity.followedOrgIDs.contains(organizations.get(position).getObjectId())){
                    //UNFOLLOW
                    parentActivity.followedOrgIDs.remove(organizations.get(position).getObjectId());
                    finalViewHolder.isFollowed.setText("Подписаться");
                    parentActivity.unfollow(organizations.get(position));
                } else{
                    parentActivity.followedOrgIDs.add(organizations.get(position).getObjectId());
                    finalViewHolder.isFollowed.setText("Отписаться");
                    parentActivity.follow(organizations.get(position));

                }
                Log.d("TRACKFOLLOWED", parentActivity.myFollowedOrganizations.toString());
            }
        });

        String shortD=organizations.get(position).getShortDescription();



        Ion.with(parent.getContext())
                .load(organizations.get(position).getParseFile("image").getUrl())
                .withBitmap()
                .placeholder(R.drawable.bg)


                .intoImageView(viewHolder.imageView);

       viewHolder.shortDesc.setText(shortD);


        return convertView;
    }


    private class ViewHolder{
        TextView nameView;
        TextView shortDesc;
        Button isFollowed;
        ImageView imageView;

    }
}

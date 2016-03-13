package com.example.user.ulife;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import org.w3c.dom.Text;


public class DisplayOrganizationFragment extends Fragment {
    TextView nameView;
    TextView shortDesc;
    ImageView imageView;
    ParseFile image;


    public DisplayOrganizationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_display_organization, container, false);

        nameView=(TextView)v.findViewById(R.id.nameView);
        shortDesc=(TextView)v.findViewById(R.id.shortDesc);
        imageView=(ImageView)v.findViewById(R.id.imageView);




        return v;
    }



    @Override
    public void onResume() {
        super.onResume();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Немного магии...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        //onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 30000);


        ParseQuery<Organization> query = ParseQuery.getQuery("Organization");

        query.getInBackground(getArguments().getString("organization"), new GetCallback<Organization>() {

            public void done(Organization object, ParseException e) {
                if (e == null) {
                    image = object.getImage();


                    Ion.with(getActivity())
                            .load(image.getUrl())
                            .withBitmap()
                            .placeholder(R.drawable.loading)
                            .intoImageView(imageView);

                    //new DownloadImageTask((ImageView) imageView).execute(image.getUrl());

                    nameView.setText(object.getName());
                    shortDesc.setText(object.getDescription());



                } else {
                    // something went wrong
                }
                progressDialog.dismiss();
            }
        });
    }
}

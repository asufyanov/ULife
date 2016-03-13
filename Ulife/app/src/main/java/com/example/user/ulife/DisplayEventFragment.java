package com.example.user.ulife;

import android.app.ProgressDialog;
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


public class DisplayEventFragment extends Fragment {
    TextView dateView;
    TextView nameView;
    TextView priceView;
    TextView organizationView;
    ImageView imageView;
    TextView addressView;
    TextView descritpion;

    ParseFile image;

    public DisplayEventFragment() {
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
        View v=inflater.inflate(R.layout.fragment_display_event, container, false);
        dateView=(TextView)v.findViewById(R.id.dateView);
        nameView=(TextView)v.findViewById(R.id.nameView);
        priceView=(TextView)v.findViewById(R.id.priceView);
        organizationView=(TextView)v.findViewById(R.id.organizationView);
        imageView=(ImageView)v.findViewById(R.id.imageView);
        addressView=(TextView)v.findViewById(R.id.addressView);
        descritpion=(TextView)v.findViewById(R.id.description);
        return v;
    }

    @Override
    public void onResume() {
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

        super.onResume();
        ParseQuery<Events> query = ParseQuery.getQuery("Events");

        query.getInBackground(getArguments().getString("event"), new GetCallback<Events>() {

            public void done(Events object, ParseException e) {
                if (e == null) {
                    image=object.getImage();


                    Ion.with(getActivity())
                            .load(image.getUrl())
                            .withBitmap()
                            .placeholder(R.drawable.loading)
                            .intoImageView(imageView);


                    //new DownloadImageTask((ImageView) imageView).execute(image.getUrl());

                    priceView.setText(""+object.getPrice()+" tenge");
                    nameView.setText(object.getName());
                    dateView.setText(object.getDate().toGMTString());
                    addressView.setText(object.getAddress());
                    Organization org=(Organization)object.getOrganization();
                    organizationView.setText(org.getName());
                    descritpion.setText(object.getDescription());




                } else {
                    // something went wrong
                }
                progressDialog.dismiss();
            }
        });
    }
    // TODO: Rename method, update argument and hook method into UI event




}

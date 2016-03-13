package com.example.user.ulife;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FirstLoginFragment extends Fragment {

    Button signUpBtn;
    Button signInBtn;

    public FirstLoginFragment() {
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
        View v=inflater.inflate(R.layout.fragment_first_login, container, false);
        signUpBtn=(Button)v.findViewById(R.id.btnSignUp);
        signInBtn=(Button)v.findViewById(R.id.btnSingIn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Fragment fragment=new SignUpFragment();
                LoginActivity parentActivity=(LoginActivity)getActivity();
                parentActivity.openFragment(fragment);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Fragment fragment=new SignInFragment();
                LoginActivity parentActivity=(LoginActivity)getActivity();
                parentActivity.openFragment(fragment);
            }
        });
        return v;

    }



}

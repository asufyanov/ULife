package com.example.user.ulife;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class SignInFragment extends Fragment {
    Button signBtn;
    EditText editUsername;
    EditText editPass;
    TextView regTextView;
    LoginActivity parentActivity;
    
    

    public SignInFragment() {
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
        View v=inflater.inflate(R.layout.fragment_sign_in, container, false);
        parentActivity=(LoginActivity)getActivity();
        signBtn=(Button)v.findViewById(R.id.btnSingIn);
        editPass=(EditText)v.findViewById(R.id.etPass);
        editUsername=(EditText)v.findViewById(R.id.etUserName);
        regTextView=(TextView)v.findViewById(R.id.link_signup);
        
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(editUsername.getText().toString(), editPass.getText().toString());
            }

            
        });
        regTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new SignUpFragment();
                parentActivity.openFragment(fragment);

            }
        });
        
        
        return v;
    }

    private void signIn(String username, String pass) {
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
        ParseUser.logInInBackground(username, pass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }


}

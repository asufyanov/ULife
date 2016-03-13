package com.example.user.ulife;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText etEmail;
    EditText etPassword;
    EditText etUsername;
    EditText etPassConf;
    Button regBtn;


    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etPassword = (EditText) v.findViewById(R.id.etPass);
        etUsername = (EditText) v.findViewById(R.id.etUserName);
        regBtn = (Button) v.findViewById(R.id.btnSignUp);
        etPassConf = (EditText) v.findViewById(R.id.etPassConf);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(etEmail.getText().toString(), etPassword.getText().toString(), etUsername.getText().toString());
            }


        });


        return v;
    }

    private void signUp(String email, String password, String userName) {

        if (validate() == false) {
            Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

            regBtn.setEnabled(true);
            return;
        }
        regBtn.setEnabled(false);

        ParseUser newUser = new ParseUser();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUsername(userName);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Магия начинается...");
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

        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                String errorMessage="";

                if (e == null) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                    progressDialog.dismiss();
                    getActivity().finish();
                    return;


                } else if (e.getCode() == ParseException.USERNAME_TAKEN) {
                    errorMessage="username занят";


                } else if (e.getCode()==ParseException.EMAIL_TAKEN) {
                    errorMessage="email зянат";
                } else errorMessage="Ошибка регистрации";

                Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                regBtn.setEnabled(true);


            }
        });


    }

    public boolean validate() {
        boolean valid = true;

        String name = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String passConf = etPassConf.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            etUsername.setError("Как минимум 3 символа");
            valid = false;
        } else {
            etUsername.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Введите верный email");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPassword.setError("От 4-ех до 10 символов");
            valid = false;
        } else {
            etPassword.setError(null);
        }
        if (!passConf.equals(password)) {
            etPassConf.setError("Пароли не совпадают");
            valid = false;
        } else etPassConf.setError(null);

        return valid;
    }


}

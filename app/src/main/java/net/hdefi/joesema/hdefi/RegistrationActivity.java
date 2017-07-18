package net.hdefi.joesema.hdefi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText password;
    private Button register;
    private EditText conPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            // if the user is already logged in take them to the ConceptsActivity
            finish();
            startActivity(new Intent(RegistrationActivity.this, ConceptsActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        email = (EditText) findViewById(R.id.etRegEmail);
        password = (EditText) findViewById(R.id.etRegPassword);
        register = (Button) findViewById(R.id.bRegister);
        conPassword = (EditText) findViewById(R.id.etRegPasswordCon);

        email.setOnClickListener(this);
        password.setOnClickListener(this);
        register.setOnClickListener(this);
        conPassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == register){
            registerUser();
        }
    }

    private void registerUser() {

        String rEmail = email.getText().toString().trim();
        String rPassword = password.getText().toString().trim();
        String rConPassword = conPassword.getText().toString().trim();

        // if the email is empty
        if(TextUtils.isEmpty(rEmail)){
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // if the password is empty
        if(TextUtils.isEmpty(rPassword)){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        // if the password is empty
        if(TextUtils.isEmpty(rConPassword)){
            Toast.makeText(this, "Please enter confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }

        // text was entered in both email and password
        // show progressBar
        // TODO: add additional authentication for correct psu emails and passwords
        // TODO: figure out email authentication
        if(!rEmail.contains("@psu.edu")){
            Toast.makeText(this, "Please enter a PSU email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(rPassword.length() < 8){
            Toast.makeText(this, "Please enter a password 8 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!rPassword.equals(rConPassword)){
            Toast.makeText(this, "Both passwords must match", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(rEmail, rPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // if the registration is complete than start the ConceptsActivity
                    // send email verification

                    sendEmailVerification();
                    progressDialog.dismiss();

                }else{
                    Toast.makeText(RegistrationActivity.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void sendEmailVerification(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()) {

                        // keeps user signed out until email is verified
                        Toast.makeText(RegistrationActivity.this, "Check your email and verify your account", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                    }


                }
            });
        }

    }




}







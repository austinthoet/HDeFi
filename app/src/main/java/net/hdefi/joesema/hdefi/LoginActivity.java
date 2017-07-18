package net.hdefi.joesema.hdefi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private EditText emailPSU;
    private EditText passwordPSU;
    private TextView registration;
    private FirebaseAuth firebaseAuth;


    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //check user is logged in or not
        if(firebaseAuth.getCurrentUser() != null){
            //start profile activity
            finish();
            startActivity(new Intent(LoginActivity.this, ConceptsActivity.class));
        }

        emailPSU = (EditText) findViewById(R.id.etLogEmail);
        passwordPSU = (EditText) findViewById(R.id.etLogPassword);
        registration = (TextView) findViewById(R.id.tvRegister);
        loginButton = (Button) findViewById(R.id.bLogin);

        // used for messages while loading
        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(this);
        // to take user to registration activity
        registration.setOnClickListener(this);


    }

    private void userLogin() {
        String lEmail = emailPSU.getText().toString().trim();
        String lPassword = passwordPSU.getText().toString().trim();

        // if the email is empty
        if(TextUtils.isEmpty(lEmail)){
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        // if the password is empty
        if(TextUtils.isEmpty(lPassword)){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(lEmail, lPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            // if task is successful then start profile activity
                            // heck if email verification was complete
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if(user.isEmailVerified()){
                                finish();
                                startActivity(new Intent(LoginActivity.this, ConceptsActivity.class));
                            }else{
                                Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                            }




                        }
                    }
                });


    }


    @Override
    public void onClick(View v) {
        if(v == loginButton){
            userLogin();
        }

        if(v == registration){
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }


}

package net.hdefi.joesema.hdefi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    /**
     * TODO: Add Authentication from Firebase to Login
     */



    private Button login;
    private EditText etEmailPSU;
    private EditText etPasswordPSU;
    private TextView registration;

    private ProgressDialog progressDialog;

    // Used for authentication for users
    private FirebaseAuth mAuth;

    // Detects if the auth state is changed
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //used for messages while loading
        progressDialog = new ProgressDialog(this);

        etEmailPSU = (EditText) findViewById(R.id.etLogEmail);
        etPasswordPSU = (EditText) findViewById(R.id.etLogPassword);
        registration = (TextView) findViewById(R.id.tvRegister);

        login = (Button) findViewById(R.id.bLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ConceptsActivity.class));
            }
        });

        // to take user to registration activity
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });


    }



}

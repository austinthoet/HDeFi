package net.hdefi.joesema.hdefi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText password;
    private Button register;

    /**
     * TODO: Add Authentication from Firebase to Register
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        email = (EditText) findViewById(R.id.etRegEmail);
        password = (EditText) findViewById(R.id.etRegPassword);
        register = (Button) findViewById(R.id.bRegister);

        email.setOnClickListener(this);
        password.setOnClickListener(this);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}

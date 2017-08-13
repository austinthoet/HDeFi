package net.hdefi.joesema.hdefi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadingsActivity extends AppCompatActivity {


    private Button concepts, formulas, messages, readings;

    // used to get firebase user
    private FirebaseAuth firebaseAuth;

    private EditText title, description;

    // used to store data
    private DatabaseReference mDatabase;

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);

        mProgressDialog = new ProgressDialog(this);

        title = (EditText) findViewById(R.id.etTitle);
        description = (EditText) findViewById(R.id.etDescription);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Reading");

        // if teacher set editable
        if(firebaseAuth.getCurrentUser().getEmail().toString().equals("jps5775@psu.edu")){
            title.setEnabled(true);
            description.setEnabled(true);
        }else{ // if students set not editable
            title.setEnabled(false);
            description.setEnabled(false);
        }

        /**
         * finding references
         */
        concepts = (Button) findViewById(R.id.bConcepts);
        formulas = (Button) findViewById(R.id.bFormulas);
        messages = (Button) findViewById(R.id.bMessages);
        readings = (Button) findViewById(R.id.bReadings);



        /**
         * Listeners for all buttons
         */

        concepts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReadingsActivity.this, ConceptsActivity.class));
            }
        });

        formulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReadingsActivity.this, FormulasActivity.class));
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReadingsActivity.this, MessagesActivity.class));
            }
        });

        readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReadingsActivity.this, ReadingsActivity.class));
            }
        });


    }

    public void onStart(){
        super.onStart();

        // get the info and set it
        // TODO: fix this so that it updates

        // for the title
        final TextView post_titleRead = (TextView) findViewById(R.id.etTitle);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post_titleRead.setText(dataSnapshot.child("title").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // for the description
        final TextView post_descRead = (TextView) findViewById(R.id.etDescription);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post_descRead.setText(dataSnapshot.child("description").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    // saves data for readings to database for teacher
    private void saveReadings(){
        String theTitle = title.getText().toString();
        String theDescription = description.getText().toString();


        if(!TextUtils.isEmpty(theTitle) && !TextUtils.isEmpty(theDescription)){
            mProgressDialog.setMessage("Saving...");
            mProgressDialog.show();

            //create unique id for each post
            //DatabaseReference newPost = mDatabase.push();

            // setting a child called "Title" to the value of the data entered by user
            mDatabase.child("title").setValue(theTitle);
            mDatabase.child("description").setValue(theDescription);

            // Once post completes
            mProgressDialog.dismiss();

            Toast.makeText(this, "Reading Saved", Toast.LENGTH_SHORT).show();

        }else{// if the posts are empty
            Toast.makeText(this, "Make sure you fill in the Title and Description", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu_logout, menu);
        if(firebaseAuth.getCurrentUser().getEmail().toString().equals("jps5775@psu.edu")){
            getMenuInflater().inflate(R.menu.main_menu_savereading, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // logs out the user if this is selected

        if (item.getItemId() == R.id.action_logout) {
            //log the user out
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }else if(item.getItemId() == R.id.action_saveReading){
            saveReadings();
        }

        return super.onOptionsItemSelected(item);
    }


}







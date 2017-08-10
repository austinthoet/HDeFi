package net.hdefi.joesema.hdefi;

import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ReadingsActivity extends AppCompatActivity {


    private Button concepts, formulas, messages, readings;
    // used to get firebase user
    private FirebaseAuth firebaseAuth;
    private EditText title, description;
    // used to store data
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);

        title = (EditText) findViewById(R.id.etTitle);
        description = (EditText) findViewById(R.id.etDescription);

        firebaseAuth = FirebaseAuth.getInstance();

        // if teacher set editable
        if(firebaseAuth.getCurrentUser().getEmail().toString().equals("jps5775@psu.edu")){
            title.setEnabled(true);
            title.setEnabled(true);
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



        // TODO: save the info in the title and description

    }

    // saves data for readings to database for teacher
    private void saveReadings(){
        String theTitle = title.getText().toString().trim();
        String theDescription = description.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //TODO: finish method to save reading

        Toast.makeText(this, "Reading Saved", Toast.LENGTH_SHORT).show();

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
            //TODO: save the data for reading in database
            saveReadings();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     *  Inner Class to save data for readings
     */
    private class TeacherReading{
        public String readingTitle;
        public String readingDescription;

        public TeacherReading(String readingTitle, String readingDescription){
            this.readingTitle = readingTitle;
            this.readingDescription = readingDescription;
        }


    }

}







package net.hdefi.joesema.hdefi;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ConceptsActivity extends AppCompatActivity{

    private FirebaseAuth firebaseAuth;


    private Button concepts, formulas, messages, readings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concepts);


        //TODO: Zoom works but can't pane photo

        /**
         * finding references
         */

        firebaseAuth = FirebaseAuth.getInstance();

        // if the user is not logged in take them back to the login screen
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


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
                startActivity(new Intent(ConceptsActivity.this, ConceptsActivity.class));
            }
        });

        formulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConceptsActivity.this, FormulasActivity.class));
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConceptsActivity.this, MessagesActivity.class));
            }
        });

        readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConceptsActivity.this, ReadingsActivity.class));
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu_logout, menu);

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
        }

        return super.onOptionsItemSelected(item);
    }

}

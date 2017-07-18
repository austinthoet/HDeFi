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

    //Zoom features
    ScaleGestureDetector scaleGestureDetector;
    private ImageView conceptsImage;
    private float scale = 1f;

    private FirebaseAuth firebaseAuth;


    private Button concepts, formulas, messages, readings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concepts);


        //TODO: Zoom works but can't pane photo
        //TODO: Set a logout button here


        /**
         * finding references
         */

        firebaseAuth = FirebaseAuth.getInstance();

        // if the user is not logged in take them back to the login screen
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        conceptsImage = (ImageView) findViewById(R.id.imConcepts);

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


        //for zooming

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener{

        float onScaleBegin = 0;
        float onScaleEnd = 0;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            scale *= detector.getScaleFactor();
            conceptsImage.setScaleX(scale);
            conceptsImage.setScaleY(scale);

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Toast.makeText(getApplicationContext(), "Scaling Begin", Toast.LENGTH_SHORT).show();
            onScaleBegin = scale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

            onScaleEnd = scale;

            if(onScaleEnd > onScaleBegin){
                Toast.makeText(getApplicationContext(), "Scaled up by: " + String.valueOf(onScaleEnd/onScaleBegin), Toast.LENGTH_SHORT).show();
            }else if(onScaleEnd < onScaleBegin){
                Toast.makeText(getApplicationContext(), "Scaled down by: " + String.valueOf(onScaleBegin/onScaleEnd), Toast.LENGTH_SHORT).show();
            }

        }
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

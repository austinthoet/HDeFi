package net.hdefi.joesema.hdefi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MessagesActivity extends AppCompatActivity {

    private Button concepts, formulas, messages, readings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
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
                startActivity(new Intent(MessagesActivity.this, ConceptsActivity.class));
            }
        });

        formulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, FormulasActivity.class));
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, MessagesActivity.class));
            }
        });

        readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, ReadingsActivity.class));
            }
        });

    }
}

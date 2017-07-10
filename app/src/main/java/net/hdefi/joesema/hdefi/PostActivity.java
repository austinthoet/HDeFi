package net.hdefi.joesema.hdefi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PostActivity extends AppCompatActivity {

    /**
     * TODO: Create Posts and allow the use of Firebase Database
     */

    private EditText etTitle;
    private EditText etPost;
    private Button bSubmit;

    private DatabaseReference mDatabase;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // shows progress to the user
        mProgressDialog = new ProgressDialog(this);

        /**
         *
         * TODO: Fix Database- currently doesn't run cause of auth
         *
         *
         */
        // gets child directory called "Blog" of Firebase database
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");


        etTitle = (EditText) findViewById(R.id.etTitle);
        etPost = (EditText) findViewById(R.id.etPost);
        bSubmit = (Button) findViewById(R.id.bSubmit);

        /**
         *  Takes care of Posting to the Database Server
         */
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, ConceptsActivity.class));
                //startPosting();
            }
        });

    }

    private void startPosting() {
        String title_val = etTitle.getText().toString().trim();
        String post_val = etPost.getText().toString().trim();

        // if the title and post are not empty then allow the posting
        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(post_val)){
            mProgressDialog.setMessage("Posting to Blog...");
            mProgressDialog.show();

            // setting a child called "Title" to the value of the data entered by user
            mDatabase.child("Title").setValue(title_val);

            mDatabase.child("Content").setValue(post_val);

            //.push() makes a random key

            mProgressDialog.dismiss();

            // go back to the blog page once completed
            startActivity(new Intent(PostActivity.this, MessagesActivity.class));
        }
    }
}

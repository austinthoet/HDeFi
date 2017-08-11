package net.hdefi.joesema.hdefi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

import static net.hdefi.joesema.hdefi.R.id.etTitle;

public class PostActivity extends AppCompatActivity {

    private EditText etPostTitle;
    private EditText etPostDescription;
    private Button bSubmitPost;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // shows progress to the user
        mProgressDialog = new ProgressDialog(this);

        // points to the child 'Blog' instead of root dir
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        etPostTitle = (EditText) findViewById(etTitle);
        etPostDescription = (EditText) findViewById(R.id.etPost);
        bSubmitPost = (Button) findViewById(R.id.bSubmit);

        /**
         *  Takes care of Posting with startPosting method in listener
         */
        bSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, ConceptsActivity.class));
                startPosting();
            }
        });

    }

    private void startPosting() {
        String title_val = etPostTitle.getText().toString().trim();
        String post_val = etPostDescription.getText().toString().trim();
        String myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


        // if the title and post are not empty then allow the posting
        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(post_val)){
            mProgressDialog.setMessage("Posting to Blog...");
            mProgressDialog.show();

            //create unique id for each post
            DatabaseReference newPost = mDatabase.push();

            // setting a child called "Title" to the value of the data entered by user
            newPost.child("title").setValue(title_val);
            newPost.child("description").setValue(post_val);
            newPost.child("email").setValue(mCurrentUser.getEmail());// use to identify user
            newPost.child("date").setValue(myDate);


            // Once post completes
            mProgressDialog.dismiss();

            //Display Completion Toast
            Toast.makeText(this, "Posted on Blog", Toast.LENGTH_SHORT).show();

            // go back to the blog page once completed
            startActivity(new Intent(PostActivity.this, MessagesActivity.class));

        }else{// if the posts are empty
            Toast.makeText(this, "Make sure you fill in the Title and Description", Toast.LENGTH_SHORT).show();
        }
    }
}

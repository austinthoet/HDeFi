package net.hdefi.joesema.hdefi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BlogSingleActivity extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private TextView singleBlogPostTitle;
    private TextView singleBlogPostDescription;
    private TextView singleBlogPostEmail;
    private TextView singleBlogPostDate;
    private Button singleBlogPostDelete;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mPost_key = getIntent().getExtras().getString("blog_id");
        mAuth = FirebaseAuth.getInstance();

        singleBlogPostTitle = (TextView) findViewById(R.id.Single_postTitle);
        singleBlogPostDescription = (TextView) findViewById(R.id.Single_postDescription);
        singleBlogPostEmail = (TextView) findViewById(R.id.Single_postEmail);
        singleBlogPostDate = (TextView) findViewById(R.id.Single_postDate);

        singleBlogPostDelete = (Button) findViewById(R.id.Single_postDelete);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_description = (String) dataSnapshot.child("description").getValue();
                String post_email = (String) dataSnapshot.child("email").getValue();
                String post_date = (String) dataSnapshot.child("date").getValue();

                singleBlogPostTitle.setText(post_title);
                singleBlogPostDescription.setText(post_description);
                singleBlogPostEmail.setText(post_email);
                singleBlogPostDate.setText(post_date);

                if(mAuth.getCurrentUser().getEmail().equals(post_email)){
                    singleBlogPostDelete.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        singleBlogPostDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPost_key).removeValue();

                Intent messagesIntent = new Intent(BlogSingleActivity.this, MessagesActivity.class);
                startActivity(messagesIntent);
            }
        });

        //Toast.makeText(BlogSingleActivity.this, mPost_key, Toast.LENGTH_SHORT).show();

    }
}

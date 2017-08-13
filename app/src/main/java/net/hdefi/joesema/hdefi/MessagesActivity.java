package net.hdefi.joesema.hdefi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessagesActivity extends AppCompatActivity {

    private Button concepts, formulas, messages, readings;
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));

        // we want access to the child "Blog" not the root directory
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        firebaseAuth = FirebaseAuth.getInstance();



        /**
         * finding references
         */
        concepts = (Button) findViewById(R.id.bConcepts2);
        formulas = (Button) findViewById(R.id.bFormulas2);
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


    protected void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(

                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setDate(model.getDate());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //take user to single blog post
                        //Toast.makeText(MessagesActivity.this, post_key, Toast.LENGTH_SHORT).show();

                        Intent singleBlogIntent = new Intent(MessagesActivity.this, BlogSingleActivity.class);
                        singleBlogIntent.putExtra("blog_id", post_key);
                        startActivity(singleBlogIntent);

                    }
                });

            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title){

            TextView post_title = (TextView) mView.findViewById(R.id.postTitle);
            post_title.setText(title);

        }

        public void setDescription(String description){

            TextView post_description = (TextView) mView.findViewById(R.id.postDescription);
            post_description.setText(description);
        }

        public void setEmail(String email){

            TextView post_email = (TextView) mView.findViewById(R.id.postEmail);
            post_email.setText(email);

        }

        public void setDate(String date){

            TextView post_date = (TextView) mView.findViewById(R.id.postDate);
            post_date.setText(date);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu_add, menu);
        if(firebaseAuth.getCurrentUser().getEmail().toString().equals("jps5775@psu.edu")){
            getMenuInflater().inflate(R.menu.main_menu_clear, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         //  if the item clicked in the MessagesActivity is the Add button from the
         //  menu resource, start the PostActivity.

        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(MessagesActivity.this, PostActivity.class));
        }else if(item.getItemId() == R.id.action_clear){
            //clear the blog
            mDatabase.removeValue();
        }

        return super.onOptionsItemSelected(item);
    }



}

package com.example.ujian_2021133002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import java.util.Random;

public class add_publ extends AppCompatActivity {

    private EditText eTitle,eAuthor,eYear,eJournal,eLink;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private String title,author,year,journal,link,key,uid;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publ);
        Random rand = new Random();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference("articles");
        button = findViewById(R.id.insert);
        eTitle = (EditText) findViewById(R.id.title);
        eAuthor = (EditText) findViewById(R.id.author);
        eYear = (EditText) findViewById(R.id.year);
        eJournal = (EditText) findViewById(R.id.journal);
        eLink = (EditText) findViewById(R.id.link);
        button.setOnClickListener(v -> {
            key = String.valueOf(rand.nextInt());
            title = eTitle.getText().toString();
            author = eAuthor.getText().toString();
            year = eYear.getText().toString();
            journal = eJournal.getText().toString();
            link = eLink.getText().toString();
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    while(snapshot.child(key).exists()){
                        key = String.valueOf(rand.nextInt());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mRef.child(key).setValue(new articles(title, link, author, journal, year, uid, key));
            Toast.makeText(this,"Data Successfully Inserted",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Publication.class);
            startActivity(intent);
        });
    }
}
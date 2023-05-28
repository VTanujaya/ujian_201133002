package com.example.ujian_2021133002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class edit_publ extends AppCompatActivity {
    private EditText eTitle,eAuthor,eYear,eJournal,eLink;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private String title,author,year,journal,link,key,uid;
    private Button button;
    private articles articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_publ);
        Random rand = new Random();
        mData = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mRef = mData.getReference("articles");
        button = findViewById(R.id.insert);
        eTitle = (EditText) findViewById(R.id.title);
        eAuthor = (EditText) findViewById(R.id.author);
        eYear = (EditText) findViewById(R.id.year);
        eJournal = (EditText) findViewById(R.id.journal);
        eLink = (EditText) findViewById(R.id.link);
        eTitle.setText(getIntent().getExtras().getString("title"));
        eAuthor.setText(getIntent().getExtras().getString("author"));
        eYear.setText(getIntent().getExtras().getString("year"));
        eJournal.setText(getIntent().getExtras().getString("journal"));
        eLink.setText(getIntent().getExtras().getString("link"));
        button.setOnClickListener(v -> {
            key = getIntent().getExtras().getString("key");
            title = eTitle.getText().toString();
            author = eAuthor.getText().toString();
            year = eYear.getText().toString();
            journal = eJournal.getText().toString();
            link = eLink.getText().toString();
            mRef.child(key).setValue(new articles(title, link, author, journal, year, uid, key));
            Toast.makeText(this,"Data Successfully Updated",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Publication.class);
            startActivity(intent);
        });
    }
    }

package com.example.ujian_2021133002;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Publication extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static ArrayList<articles> arrayList  = new ArrayList<>();
    private RecyclerView list;
    private Button btnCreate;
    public static Activity Fa;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);
        btnCreate = (Button) findViewById(R.id.add_item);
        list = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList.clear();
        Fa =this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("articles");
        getUsers();
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new CustomAdapter(this,arrayList, mUser));
        btnCreate.setOnClickListener(v -> {
            Intent add = new Intent(this, add_publ.class);
            startActivity(add);
        });
    }
    public void getUsers(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            String title, link, authors, publication, year, user_id,key;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    title =dataSnapshot.child("title").getValue().toString();
                    link = dataSnapshot.child("link").getValue().toString();
                    authors = dataSnapshot.child("authors").getValue().toString();
                    publication = dataSnapshot.child("publication").getValue().toString();
                    year = dataSnapshot.child("year").getValue().toString();
                    user_id = dataSnapshot.child("user_id").getValue().toString();
                    key = dataSnapshot.getKey();
                    arrayList.add(new articles(title, link, authors, publication, year,user_id,key));;
                }
                list.setAdapter(new CustomAdapter(Publication.this, arrayList,mUser));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.publication);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.main:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.publication:
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public static void cleanList(){
        arrayList.clear();
    }
}

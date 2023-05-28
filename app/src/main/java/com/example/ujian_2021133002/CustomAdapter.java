package com.example.ujian_2021133002;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        ArrayList<articles> articles;
        Context context;
        articles article;
        private PublicationWebView webView;
        public String url;
        private FirebaseUser user;
        private FirebaseDatabase mDatabase;
        private DatabaseReference mRef;
        private String uid;


public CustomAdapter(Context context, ArrayList<articles> articles, FirebaseUser user) {
        this.context = context;
        this.articles = articles;
        this.user = user;
        }

@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
        }

@Override
public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
    mDatabase = FirebaseDatabase.getInstance();
    mRef = mDatabase.getReference("articles");
    article = articles.get(position);
    uid = user.getUid();
        // set the data in items
        holder.article.setText(article.getTitle());
        holder.author.setText(article.getAuthors());
        holder.year.setText(article.getYear());
        holder.link.setText(article.getLink());
        holder.key.setText(article.getKey());
        holder.journal.setText(article.getPublication());
        if(uid.equals(article.getUser_id())){
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
        }else{
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);

        }

        // implement setOnClickListener event on item view.
    holder.itemView.setOnClickListener(view -> {
        url = holder.link.getText().toString();
        Intent web = new Intent(context, PublicationWebView.class);
        web.putExtra("url",url);
        context.startActivity(web);
    });
        holder.delete.setOnClickListener(v -> {
            String temp = holder.key.getText().toString();
            mRef.child(temp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(context,"Data Removed",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,"Failed to remove data",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Publication.cleanList();
        });
        holder.edit.setOnClickListener(v -> {
            Intent edit = new Intent(context, edit_publ.class);
            edit.putExtra("key",holder.key.getText().toString());
            edit.putExtra("title",holder.article.getText().toString());
            edit.putExtra("year",holder.year.getText().toString());
            edit.putExtra("author",holder.author.getText().toString());
            edit.putExtra("link",holder.link.getText().toString());
            edit.putExtra("journal",holder.journal.getText().toString());
            context.startActivity(edit);
        });
}


@Override
public int getItemCount() {
        return articles.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView article, author, year, link, key, journal;// init the item view's
    Button edit, delete;

    public MyViewHolder(View itemView) {
        super(itemView);

        // get the reference of item view's
        article = (TextView) itemView.findViewById(R.id.articleTitle);
        author = (TextView) itemView.findViewById(R.id.author);
        year = (TextView) itemView.findViewById(R.id.year);
        link = (TextView) itemView.findViewById(R.id.link);
        edit = (Button)  itemView.findViewById(R.id.editbtn);
        delete = (Button) itemView.findViewById(R.id.deletebtn);
        key = (TextView) itemView.findViewById(R.id.key);
        journal = (TextView) itemView.findViewById(R.id.journal);


    }

}
}

package com.example.quicknotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton add_note_btn;
    RecyclerView recycler_view;
    ImageButton menu_icon_imagebtn;
    Notesadapter notesadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_note_btn =findViewById(R.id.add_note_btn);
        recycler_view=findViewById(R.id.recycler_view);
        menu_icon_imagebtn=findViewById(R.id.menu_icon_imagebtn);



        add_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Notes_detail_activity.class));
            }
        });


        menu_icon_imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

        setRecycler_view();


    }

    void showMenu(){
        PopupMenu popupMenu =new PopupMenu(MainActivity.this,menu_icon_imagebtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,loginactivity.class));
                    finish();
                    return  true;
                }
                return false;
            }
        });
    }

    void setRecycler_view(){
        Query query=Utility.getCollectionReferenceForNotes().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Notesdatabase> options=new FirestoreRecyclerOptions.Builder<Notesdatabase>()
                .setQuery(query,Notesdatabase.class).build();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        notesadapter=new Notesadapter(options,this);
        recycler_view.setAdapter(notesadapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notesadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notesadapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesadapter.notifyDataSetChanged();
    }
}
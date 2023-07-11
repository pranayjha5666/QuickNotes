package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class Notes_detail_activity extends AppCompatActivity {

    EditText notes_title_edittext,notes_content_edittext;
    TextView add_notes_page_title;

    ImageButton savenotes_btn;

    String title,content,docId;

    boolean isEditMode=false;
    TextView delete_note_textview_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_detail);

        notes_title_edittext=findViewById(R.id.notes_title_edittext);
        notes_content_edittext=findViewById(R.id.notes_content_edittext);
        savenotes_btn=findViewById(R.id.save_icon_imagebtn);
        add_notes_page_title=findViewById(R.id.add_notes_page_title);
        delete_note_textview_btn=findViewById(R.id.delete_note_textview_btn);

//        receive data from intent

        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        docId=getIntent().getStringExtra("docId");

        if(docId!=null && !docId.isEmpty()){
            isEditMode=true;
        }


        notes_title_edittext.setText(title);
        notes_content_edittext.setText(content);

        if(isEditMode){
            add_notes_page_title.setText("Edit Your Notes");
            delete_note_textview_btn.setVisibility(View.VISIBLE);

        }



        savenotes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String notes_title=notes_title_edittext.getText().toString();
               String notes_contet=notes_content_edittext.getText().toString();
               if(notes_title==null||notes_title.isEmpty()){
                   notes_title_edittext.setError("Title is required");
                   return;
               }

               Notesdatabase notesdatabase=new Notesdatabase();
               notesdatabase.setTitle(notes_title);
               notesdatabase.setContent(notes_contet);
               notesdatabase.setTimestamp(Timestamp.now());

               saveNoteToFirebase(notesdatabase);
            }
        });

        delete_note_textview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                Copy all saveNoteToFirebase content and change accordingly
                DocumentReference documentReference;
                documentReference=Utility.getCollectionReferenceForNotes().document(docId);



                documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Notes_detail_activity.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(Notes_detail_activity.this, "Failed while deleting the notes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }

    void saveNoteToFirebase(Notesdatabase notesdatabase){
        DocumentReference documentReference;

        if(isEditMode){
            documentReference=Utility.getCollectionReferenceForNotes().document(docId);
        }
        else {
            documentReference=Utility.getCollectionReferenceForNotes().document();
        }




        documentReference.set(notesdatabase).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Notes_detail_activity.this, "Notes added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(Notes_detail_activity.this, "Failed while adding the notes", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
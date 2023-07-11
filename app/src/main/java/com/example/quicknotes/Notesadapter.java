package com.example.quicknotes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Notesadapter extends FirestoreRecyclerAdapter<Notesdatabase, Notesadapter.NoteViewholder> {

    Context context;

    public Notesadapter(@NonNull FirestoreRecyclerOptions<Notesdatabase> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewholder holder, int position, @NonNull Notesdatabase notesdatabase) {
        holder.title_textview.setText(notesdatabase.title);
        holder.content_textview.setText(notesdatabase.content);
        holder.timestamp_textview.setText(Utility.timestampToString(notesdatabase.timestamp));
//        String docId=this.getSnapshots().getSnapshot(position);
        String docid=this.getSnapshots().getSnapshot(position).getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Notes_detail_activity.class);
                intent.putExtra("title",notesdatabase.title);
                intent.putExtra("content",notesdatabase.content);
                intent.putExtra("docId",docid);
                context.startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public NoteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_root_item,parent,false);
        return new NoteViewholder(view);
    }

    class NoteViewholder extends RecyclerView.ViewHolder{

        TextView title_textview,content_textview,timestamp_textview;

        public NoteViewholder(@NonNull View itemView) {
            super(itemView);
            title_textview=itemView.findViewById(R.id.notes_title_textview);
            content_textview=itemView.findViewById(R.id.notes_content_textview);
            timestamp_textview=itemView.findViewById(R.id.notes_timestamp_textview);

        }
    }

}

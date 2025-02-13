package com.example.quicknotes;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;


public class Utility {

    static CollectionReference getCollectionReferenceForNotes(){
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection(";").document(currentUser.getUid()).collection("My_Notes");

    }


    static String timestampToString(Timestamp timestamp){
//        return new SimpleDateFormat("DD/MM/YYYY").format(timestamp.toDate());
        return new SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate());

//        return new android.icu.text.SimpleDateFormat("DD/MM/YYYY").format(timestamp.toDate());
    }

}

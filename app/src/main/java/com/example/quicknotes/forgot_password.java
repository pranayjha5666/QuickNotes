package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class forgot_password extends AppCompatActivity {
    Button forgot_btn;
    EditText forgot_email_edittext;
    String email;
    TextView forgot_login;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot_btn=findViewById(R.id.forgot_btn);
        forgot_login=findViewById(R.id.forgot_login_btn);
        forgot_email_edittext=findViewById(R.id.forgot_email_edittext);
        auth = FirebaseAuth.getInstance();

        forgot_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forgot_password.this,loginactivity.class));
            }
        });

        forgot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatedata();
            }
        });



    }
    public void validatedata() {
        email = forgot_email_edittext.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgot_email_edittext.setError("Email is invalid");
        } else {
            forgotPassword();
        }
    }

    public void forgotPassword(){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgot_password.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(forgot_password.this,loginactivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(forgot_password.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
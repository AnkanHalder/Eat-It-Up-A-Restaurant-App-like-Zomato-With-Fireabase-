package com.example.jiraiya.eatitup;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;

public class SignIn extends AppCompatActivity {

    Button signin;
    TextInputLayout userName,userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Link XML with Java(UI with Code)
        signin = (Button)findViewById(R.id.signInSignIn);
        userName = (TextInputLayout)findViewById(R.id.mobileNo);
        userPassword = (TextInputLayout)findViewById(R.id.password);

        //Get Instance of the Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Get the table name user from the DB
        final DatabaseReference table = database.getReference("User");



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String un = userName.getEditText().getText().toString();
                final String pass = userPassword.getEditText().getText().toString();

                final ProgressDialog pd =new ProgressDialog(SignIn.this);
                pd.setMessage("Please Wait ...");
                pd.show();

                //If not Empty
                if(!un.equals("") && !pass.equals("")) {


                    table.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            pd.dismiss();
                            if(dataSnapshot.child(un).exists()) {
                                //Get the user name and get the all the value from the DB in the object of user
                                User user = dataSnapshot.child(un).getValue(User.class);
                                if (user.getPassword().equals(pass))
                                    Toast.makeText(SignIn.this, "Sign In Successful !!", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(SignIn.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(SignIn.this, "No User Found", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                    Toast.makeText(SignIn.this, "Fields cannot be Empty!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

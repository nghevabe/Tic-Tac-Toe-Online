package com.example.dell.projecttictactoe;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    public static String UserName;

    private ValueEventListener DBfire;

    private  DatabaseReference myRef;

    ArrayList<User> lstAcc = new ArrayList<>();

    EditText eduser, edpass;

    Button btnLogin;

    TextView txtRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Init();
        Log.v("Linh","Xin Chao");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = eduser.getText().toString();
                String pass = edpass.getText().toString();

                if(CheckLogin(username,pass))
                {
                    UserName = username;

                    Intent myIntent = new Intent(Login.this, MyService.class);

                //    startService(myIntent);

                    Intent intend = new Intent(Login.this, SelectMode.class);
                    startActivity(intend);
                } else {

                    Toast.makeText(Login.this,
                            "Login Fail", Toast.LENGTH_SHORT).show();

                    eduser.setText("");
                    edpass.setText("");

                }

            }
        });

        txtRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intend = new Intent(Login.this, Register.class);
                startActivity(intend);

            }
        });


    }

    public void Init()
    {

        btnLogin = (Button) findViewById(R.id.button);

        txtRes = (TextView) findViewById(R.id.register);

        eduser = (EditText) findViewById(R.id.edUser);
        edpass = (EditText) findViewById(R.id.edPass);


        LoadData();

    }

    public boolean CheckLogin(String userInput, String passInput)
    {
         boolean check = false;

        for(User user: lstAcc)
        {

            String username = user.getUsername().toString();
            String pass = user.getPassword().toString();

            if(userInput.equals(username) && passInput.equals(pass))
            {

                check = true;


            }


        }

        return check;

    }

    public void LoadData()
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        myRef = database.getReference("User");

        // Read from the database
       DBfire = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> lstUser = dataSnapshot.getChildren();

                for (DataSnapshot user: lstUser) {

                    String username =  user.getKey();

                    User user1 = new User();
                    user1.setUsername(username);


                    DataSnapshot snapPass = user.child("pass");
                    DataSnapshot snapMail = user.child("mail");
                    DataSnapshot snapMes = user.child("message");

                    String pass = snapPass.getValue(String.class);
                    String mail = snapMail.getValue(String.class);
                    String mes = snapMes.getValue(String.class);

                    user1.setPassword(pass);
                    user1.setMail(mail);
                    user1.setMessage(mes);

                    lstAcc.add(user1);

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });

    }



    @Override
    protected void onStart(){
        super.onStart();
        Log.v("Page 1","MainActivity onStart invoked");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // UpdateStatus(Login.UserName,"offline");
        Log.v("Debug","Page1 onDestroy invoked");
    }

    @Override
    public void onPause(){
        super.onPause();
        myRef.removeEventListener(DBfire);


        Log.v("Debug","Page1 OnPause invoked");
    }

    @Override
    public void onResume(){
        super.onResume();

        Log.v("Debug","Page1 onResume invoked");
    }

    @Override
    protected void onStop(){
        super.onStop();
        //  UpdateStatus(Login.UserName,"offline");
        Log.v("Debug","Page1 onStop invoked");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.v("Debug","Page1 onRestart invoked");
    }


}

package com.example.dell.projecttictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    EditText User, Mail, Pass;
    Button button;

    private ValueEventListener DBfire;

    private  DatabaseReference myRef;

    public ArrayList<String> lstAcc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Init();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = User.getText().toString();
                String mail = Mail.getText().toString();
                String pass = Pass.getText().toString();


                if(CheckRegister(username,pass,mail))
                {
                    CreateAccount(username,pass,mail);

                    Intent intend = new Intent(Register.this, Login.class);
                    startActivity(intend);

                }

                User.setText("");

                Mail.setText("");

                Pass.setText("");

            }
        });



    }


    public void Init()
    {

        User = (EditText) findViewById(R.id.edUser);
        Mail = (EditText) findViewById(R.id.edMail);
        Pass = (EditText) findViewById(R.id.edPass);

        button = (Button) findViewById(R.id.btnRes);

        LoadAllAccount();

    }


    public void CreateAccount(String user, String pass, String mail )
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("User");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference User = Data.child(user);



        DatabaseReference password = User.child("pass");

        DatabaseReference email = User.child("mail");

        DatabaseReference message = User.child("message");

        DatabaseReference moveX = User.child("moveX");

        DatabaseReference moveY = User.child("moveY");

        DatabaseReference status = User.child("status");

        DatabaseReference win = User.child("win");

        DatabaseReference lose = User.child("lose");

        DatabaseReference draw = User.child("draw");

        password.setValue(pass);
        email.setValue(mail);
        message.setValue("none");
        moveX.setValue("none");
        moveY.setValue("none");
        win.setValue("0");
        lose.setValue("0");
        draw.setValue("0");
        status.setValue("offline");

        Toast.makeText(Register.this,
                "Create Account Successful", Toast.LENGTH_SHORT).show();


    }

    public boolean CheckRegister(String userinput, String pass, String mail)
    {
        boolean check = true;

        for(int i=0;i<lstAcc.size();i++)
        {
            if(userinput.equals(lstAcc.get(i)))
            {
                check = false;

                Toast.makeText(Register.this,
                        "This User Name have been used", Toast.LENGTH_SHORT).show();

            }
        }

        if(pass.length() < 6)
        {
            check = false;

            Toast.makeText(Register.this,
                    "password must long than 6 character", Toast.LENGTH_SHORT).show();
        }

        if(mail.contains("@"))
        {

            if(mail.contains(".com")||mail.contains(".vn")||mail.contains(".net")||mail.contains(".edu"))
            {

            } else {
                check = false;
                Toast.makeText(Register.this,
                        "Mail don't match form", Toast.LENGTH_SHORT).show();
            }

        } else {
            check = false;
            Toast.makeText(Register.this,
                    "Mail don't match form", Toast.LENGTH_SHORT).show();
        }
        return check;
    }



    public void LoadAllAccount()
    {
        // Chọn vào database
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

                    lstAcc.add(username);






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

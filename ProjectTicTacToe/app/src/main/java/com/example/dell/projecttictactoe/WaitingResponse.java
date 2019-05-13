package com.example.dell.projecttictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitingResponse extends AppCompatActivity {

    private ValueEventListener DBfire;

    private DatabaseReference myRef;

    private DatabaseReference Mes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_response);

        LoadData();

        UpdateStatus(Login.UserName,"online");
    }


    public void UpdateStatus(String userName, String userStatus)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("User");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference User = Data.child(userName);




        DatabaseReference status = User.child("status");




        status.setValue(userStatus);



    }


    public void UpdateMessage(String userName, String text)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("User");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference User = Data.child(userName);




        DatabaseReference message = User.child("message");




        message.setValue(text);



    }




    public void LoadData()
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
         myRef = database.getReference("User");

         DatabaseReference UserInvite = myRef.child(Login.UserName);

         Mes = UserInvite.child("message");

        // Read from the database
         DBfire = Mes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String message = dataSnapshot.getValue(String.class);

                if(!message.equals("none")&&!message.equals("No"))
                {

                    UpdateMessage(OnlineServer.userInvite,"none");
                    UpdateMessage(Login.UserName,"none");

                    OnlineServer.nameRoom = Login.UserName+"-"+OnlineServer.userInvite;


                    finish();

                    Intent intend = new Intent(WaitingResponse.this, WaitingRoom.class);
                    startActivity(intend);



                }

                if(message.equals("No"))
                {


                    //  UpdateMessage(Login.UserName);

                    NotiDialog();


                }










            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });

    }


    public void NotiDialog()
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                WaitingResponse.this);

        // set title
        alertDialogBuilder.setTitle("Player Deny!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click OK to back!")
                .setCancelable(false)
                .setPositiveButton("Back",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        UpdateMessage(Login.UserName,"none");

                        Mes.removeEventListener(DBfire);

                        Intent intend = new Intent(WaitingResponse.this, OnlineServer.class);
                        startActivity(intend);

                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.v("Debug","MainActivity onStart invoked");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //UpdateStatus(Login.UserName,"offline");
        Log.v("Debug","MainActivity onDestroy invoked");
    }

    @Override
    public void onPause(){
        super.onPause();
        Mes.removeEventListener(DBfire);
        UpdateStatus(Login.UserName,"offline");
        Log.v("Debug","MainActivity OnPause invoked");
    }

    @Override
    public void onResume(){
        super.onResume();
        UpdateStatus(Login.UserName,"online");
        Log.v("Debug","MainActivity onResume invoked");
    }

    @Override
    protected void onStop(){
        super.onStop();

        Log.v("Debug","MainActivity onStop invoked");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        UpdateStatus(Login.UserName,"online");
        Log.v("Debug","MainActivity onRestart invoked");
    }

}

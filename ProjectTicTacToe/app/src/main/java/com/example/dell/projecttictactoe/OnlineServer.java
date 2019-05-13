package com.example.dell.projecttictactoe;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlineServer extends AppCompatActivity {

    Button button;

    private ValueEventListener DBfire;

    private DatabaseReference myRef;

    public static String nameRoom;

    private DatabaseReference Message;

    ListView listView;

    public int direct = 0;

    public static String userInvite;

    ArrayList<User> lstUserOnline = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_server);

        button = (Button) findViewById(R.id.btnMatch);



        UpdateStatus(Login.UserName,"online");

        listView = (ListView) findViewById(R.id.listUserOnl);

        LoadData();

        AcceptInvite();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intend = new Intent(OnlineServer.this, MatchMaking.class);
                startActivity(intend);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = new User();
                user = lstUserOnline.get(position);

                String uname = user.getUsername().toString();
                userInvite = uname;



                Invite();

                SendInvite(uname,Login.UserName);







            }
        });



    }



    public void SendInvite(String username,String text)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("User");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference User = Data.child(username);




        DatabaseReference message = User.child("message");




        message.setValue(text);

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


    public void AcceptInvite()
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("User");

        DatabaseReference UserInvite = myRef.child(Login.UserName);

        Message = UserInvite.child("message");



        // Read from the database
        DBfire =  Message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String message = dataSnapshot.getValue(String.class);

                if(!message.equals("none")) {

                    userInvite = message;



                    NotiDialog();

                }

                //  Message.removeEventListener(DBfire);

                //   Toast.makeText(MyService.this,
                //      message, Toast.LENGTH_SHORT).show();


            }


            public void UpdateMessage(String userName)
            {

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
                DatabaseReference Data = database.getReference("User");

                //Chọn 1 Node (nếu chưa có thì tạo)
                DatabaseReference User = Data.child(userName);



                DatabaseReference message = User.child("message");



                message.setValue("none");


            }

            public void NotiDialog()
            {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        OnlineServer.this);

                // set title
                alertDialogBuilder.setTitle("!Invite!");

                // set dialog message
                alertDialogBuilder
                        .setMessage("You Have Invited From Other Player")
                        .setCancelable(false)
                        .setPositiveButton("Accept",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity

                                SendInvite(userInvite, Login.UserName);
                                CreateRoom(userInvite,Login.UserName);




                                Intent intend = new Intent(OnlineServer.this, WaitingRoom.class);
                                startActivity(intend);

                                Message.removeEventListener(DBfire);
                            }
                        })
                        .setNegativeButton("Deny",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                SendInvite(userInvite, "No");
                                UpdateMessage(Login.UserName);
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });



    }


        public void Invite()
        {



            final FirebaseDatabase database = FirebaseDatabase.getInstance();

            //Tham chiếu 1 Node
            DatabaseReference myRef = database.getReference("User");

            DatabaseReference UserInvite = myRef.child(Login.UserName);

            Message = UserInvite.child("message");



            // Read from the database
            DBfire =  Message.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {



                    String message = dataSnapshot.getValue(String.class);


                    if(message.contains("none"))
                    {

                        nameRoom = Login.UserName+"-"+userInvite;

                        Intent intend = new Intent(OnlineServer.this, WaitingResponse.class);
                        startActivity(intend);

                        Message.removeEventListener(DBfire);
/*
                        CreateRoom(userInvite,Login.UserName);


                        Intent intend = new Intent(OnlineServer.this, WaitingRoom.class);
                        startActivity(intend);

                        Message.removeEventListener(DBfire);

*/

                    }



                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("NOT", "Failed to read value.", error.toException()); }
            });



        }

        public void CreateRoom(String host, String guest)
        {

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
            DatabaseReference Data = database.getReference("PlayingRoom");

            nameRoom = host + "-" + guest;

            //Chọn 1 Node (nếu chưa có thì tạo)
            DatabaseReference room = Data.child(nameRoom);

            DatabaseReference playerX = room.child("PlayerX");

            DatabaseReference playerY = room.child("PlayerY");

            DatabaseReference status = room.child("Status");

            DatabaseReference hostStatus = room.child(host);

            DatabaseReference guestStatus = room.child(guest);

            DatabaseReference turn = room.child("Turn");

            DatabaseReference MyMove = room.child(Login.UserName+" - Move");

            DatabaseReference EnemyMove = room.child(userInvite+" - Move");

            playerX.setValue(host);

            playerY.setValue(guest);

            hostStatus.setValue("none");

            guestStatus.setValue("none");

            status.setValue("none");

            turn.setValue("1");

            MyMove.setValue("none");

            EnemyMove.setValue("none");





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

                    lstUserOnline.clear();
                    Iterable<DataSnapshot> lstUser = dataSnapshot.getChildren();






                    for (DataSnapshot user: lstUser) {

                        String username =  user.getKey();

                        User user1 = new User();

                        user1.setUsername(username);



                        DataSnapshot snapPass = user.child("pass");
                        DataSnapshot snapMail = user.child("mail");
                        DataSnapshot snapMes = user.child("message");
                        DataSnapshot snapStatus = user.child("status");




                        String pass = snapPass.getValue(String.class);
                        String mail = snapMail.getValue(String.class);
                        String mes = snapMes.getValue(String.class);
                        String status = snapStatus.getValue(String.class);

                        if(status.equals("online")&&!user1.getUsername().equals(Login.UserName)) {

                            user1.setPassword(pass);
                            user1.setMail(mail);
                            user1.setMessage(mes);
                            user1.setStatus(status);




                            lstUserOnline.add(user1);




                        }








                    }

                    UserAdapter adapter = new UserAdapter(OnlineServer.this, lstUserOnline );

                    listView.setAdapter(adapter);

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

        UpdateStatus(Login.UserName,"offline");

       // myRef.removeEventListener(DBfire);

        Log.v("Debug","Page1 OnPause invoked");
    }

    @Override
    public void onResume(){
        super.onResume();
        UpdateStatus(Login.UserName,"online");
        Log.v("Debug","Page1 onResume invoked");
    }

    @Override
    protected void onStop(){
        super.onStop();
//        Message.removeEventListener(DBfire);
      //  UpdateStatus(Login.UserName,"offline");
        Log.v("Debug","Page1 onStop invoked");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.v("Debug","Page1 onRestart invoked");
    }

}

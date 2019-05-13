package com.example.dell.projecttictactoe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MatchMaking extends AppCompatActivity {

    public static ArrayList<PlayerSearching> lstPlayer = new ArrayList<>();

    public static String nameWaiting;

    private ValueEventListener DBfire;

    private DatabaseReference myRef;

    private int m=0;

    private int stop = 0;

    private DatabaseReference Message;

    public static int dem=0;

    public int checkPlayer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_making);

        Searching();


        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {

            @Override
            public void run() {
                // change image
                UpdateStatus("Searching");

            }

        }, 5000); // 10000ms delay



        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {

            @Override
            public void run() {

                LoadList();
                Waiting();
            }

        }, 10000); // 10000ms delay


        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {

            @Override
            public void run() {
                // change image
                if(stop==0) {
                    Reload();
                }

            }

        }, 25000); // 10000ms delay


    }

    public void SeeEnemy()
    {




        for (int i=0;i<lstPlayer.size();i=i+2)
        {



           if(i<lstPlayer.size()&&i+1<lstPlayer.size()) {



                PlayerSearching player1 = new PlayerSearching();

                player1 = lstPlayer.get(i);

                final String name1 = player1.getName();
                String number1 = player1.getNumber();
                String status1 = "";
                status1 = player1.getStatus();

                //

                PlayerSearching player2 = new PlayerSearching();

                player2 = lstPlayer.get(i+1);

                final String name2 = player2.getName();
                String number2 = player2.getNumber();
                String status2 = "";
                status2 = player2.getStatus();




                    if(name1.equals(Login.UserName)) {



                        int n1 = Integer.parseInt(number1);
                        int n2 = Integer.parseInt(number2);

                        if (n1 > n2) {

                            if(status1!=null&&status2!=null&&status1.equals("Searching")&&status2.equals("Searching")) {


                                Log.w("CHECKER ", "List : "+lstPlayer.size());

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        // change image
                                        CheckPlayer1(name1,name2);

                                    }

                                }, 1000); // 10000ms delay



                            }
                        } else {
                            Waiting();
                        }
                    }

                    if(name2.equals(Login.UserName))
                    {

                        int n1 = Integer.parseInt(number1);
                        int n2 = Integer.parseInt(number2);

                        if(n2>n1)
                        {


                           if(status1!=null&&status2!=null&&status1.equals("Searching")&&status2.equals("Searching")&&status1!=null&&status2!=null) {

                               Log.w("CHECKER ", "List : "+lstPlayer.size());



                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {

                                   @Override
                                   public void run() {
                                       // change image
                                       CheckPlayer2(name2,name1);

                                   }

                               }, 1000); // 10000ms delay

                            }
                        }else {
                            Waiting();
                        }
                    }



           }

        }

    }



    public void SendMessage(String username1,String username2)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("User");

        //Chọn 1 Node (nếu chưa có thì tạo)
        // Set message for guest
        DatabaseReference User = Data.child(username1);



        DatabaseReference message = User.child("message");



        message.setValue(username2);

        // Set message for yourself

        DatabaseReference User2 = Data.child(username2);



        DatabaseReference message2 = User2.child("message");



     //   message2.setValue(username1);





    }

    public void CreateRoom(String host, String guest)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("PlayingRoom");

        String nameRoom = host + "-" + guest;

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference room = Data.child(nameRoom);

        DatabaseReference playerX = room.child("PlayerX");

        DatabaseReference playerY = room.child("PlayerY");

        DatabaseReference status = room.child("Status");

        DatabaseReference hostStatus = room.child(host);

        DatabaseReference guestStatus = room.child(guest);

        DatabaseReference turn = room.child("Turn");

        DatabaseReference MyMove = room.child(host+" - Move");

        DatabaseReference EnemyMove = room.child(guest+" - Move");

        playerX.setValue(host);

        playerY.setValue(guest);

        hostStatus.setValue("none");

        guestStatus.setValue("none");

        status.setValue("none");

        turn.setValue("1");

        MyMove.setValue("none");

        EnemyMove.setValue("none");


    }


    public void CheckPlayer2(final String name2,final String name1)
    {
        //Log.w("CHICH", "CHECK2 Ngoai");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        myRef = database.getReference("User");

        DatabaseReference UserInvite = myRef.child(name1);

        Message = UserInvite.child("message");



        // Read from the database
        DBfire =  Message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                String message = dataSnapshot.getValue(String.class);


                if(message.contains("none"))
                {
                  //  Log.w("CHICH", "CHECK2 Trong");
                    OnlineServer.nameRoom = name2 + "-" + name1;
                    OnlineServer.userInvite = name1;

                    Log.w("CHECKER ", "Room 2 : "+OnlineServer.nameRoom);

                        Toast.makeText(MatchMaking.this,
                            "Room2: "+OnlineServer.nameRoom, Toast.LENGTH_SHORT).show();



                    //Send Message to Other Player
                    SendMessage(name1, name2);
                    //Set Message My Self

                    //Name room = name2+name1;
                    CreateRoom(name2, name1);
                    stop = 1;

                    Intent intend = new Intent(MatchMaking.this, WaitingRoom.class);
                    startActivity(intend);

                    myRef.removeEventListener(DBfire);
                    Message.removeEventListener(DBfire);
                    Remove();

                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });



    }


    public void CheckPlayer1(final String name1,final String name2)
    {

     //   Log.w("CHICH", "CHECK1 Ngoai");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        myRef = database.getReference("User");

        DatabaseReference UserInvite = myRef.child(name2);

        Message = UserInvite.child("message");



        // Read from the database
        DBfire =  Message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                String message = dataSnapshot.getValue(String.class);
           //     Log.w("CHICH", "CHECK1 Trong");

                if(message.contains("none"))
                {

                    OnlineServer.nameRoom = name1 + "-" + name2;
                    OnlineServer.userInvite = name2;

                    Log.w("CHECKER ", "Room 1 : "+OnlineServer.nameRoom);

                        Toast.makeText(MatchMaking.this,
                            "Room1: "+OnlineServer.nameRoom, Toast.LENGTH_SHORT).show();



                    //Send Message to Other Player
                    SendMessage(name2, name1);
                    //Set Message My Self

                    //Name room = name2+name1;
                    CreateRoom(name1, name2);
                    stop = 1;

                    Intent intend = new Intent(MatchMaking.this, WaitingRoom.class);
                    startActivity(intend);

                    myRef.removeEventListener(DBfire);
                    Message.removeEventListener(DBfire);
                    Remove();


                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });


    }


    public void Waiting()
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        myRef = database.getReference("User");

        DatabaseReference UserInvite = myRef.child(Login.UserName);

        Message = UserInvite.child("message");



        // Read from the database
        DBfire =  Message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // Log.w("CHICH", "WAITING");

                String message = dataSnapshot.getValue(String.class);


                if(!message.contains("none"))
                {


                    OnlineServer.nameRoom = message + "-" + Login.UserName;
                    OnlineServer.userInvite = message;

                    Log.w("CHECKER ", "Room 0 : "+OnlineServer.nameRoom);

                    Toast.makeText(MatchMaking.this,
                            "RoomWaiting: "+OnlineServer.nameRoom, Toast.LENGTH_SHORT).show();

                    stop = 1;

                    //SendInvite(OnlineServer.userInvite,Login.UserName);
                    Log.v("Check","Room: "+OnlineServer.nameRoom);
                    Intent intend = new Intent(MatchMaking.this, WaitingRoom.class);
                    startActivity(intend);

                    Message.removeEventListener(DBfire);
                    myRef.removeEventListener(DBfire);

                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });

    }

    public void LoadList()
    {



        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        myRef = database.getReference("SearchingRoom");



        // Read from the database
        DBfire = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                dem++;
                lstPlayer.clear();

                Iterable<DataSnapshot> listPlayer = dataSnapshot.getChildren();


                for (DataSnapshot user: listPlayer) {

                    String str =  user.getKey();

                    String[] strcut = str.split("-");

                    PlayerSearching player1 = new PlayerSearching();
                    player1.setName(strcut[1]);



                    DataSnapshot snapNumber = user.child("number");
                    DataSnapshot snapStatus = user.child("status");


                    String number = snapNumber.getValue(String.class);
                    String status = snapStatus.getValue(String.class);


                    player1.setNumber(number);
                    player1.setStatus(status);

                    lstPlayer.add(player1);



                }

                   Toast.makeText(MatchMaking.this,
                            "OKIE: "+lstPlayer.size(), Toast.LENGTH_SHORT).show();

                 Log.w("OKIE: ", "Dem : "+dem+" Size: "+lstPlayer.size());

                 if(lstPlayer.size()>1)
                     {
                          SeeEnemy();
                     }else{
                          // myRef.removeEventListener(DBfire);
                           if(Message!=null) {
                              // Message.removeEventListener(DBfire);
                           }
                     }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });

    }

    public void Remove()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("SearchingRoom");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference User = Data.child(nameWaiting);

        User.removeValue();


    }


    public void Reload()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("SearchingRoom");

        Random rand = new Random();

        int  n = rand.nextInt(100) + 1;

        String num = Integer.toString(n);



        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference User = Data.child(nameWaiting);



        DatabaseReference number = User.child("number");
        DatabaseReference status = User.child("status");



        number.setValue(num);
        status.setValue("Searching");



    }

    public void Searching()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("SearchingRoom");

        Random rand = new Random();

        int  n = rand.nextInt(100) + 1;

        String num = Integer.toString(n);

        nameWaiting = num + "-" + Login.UserName;

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference User = Data.child(nameWaiting);



        DatabaseReference number = User.child("number");
        DatabaseReference status = User.child("status");



        number.setValue(num);
        status.setValue("Pending");



    }


    public void UpdateStatus(String userStatus)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("SearchingRoom");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference User = Data.child(nameWaiting);



        DatabaseReference status = User.child("status");



        status.setValue(userStatus);


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
        myRef.removeEventListener(DBfire);

        Message.removeEventListener(DBfire);

        Remove();

        Log.v("Debug","MainActivity OnPause invoked");
    }

    @Override
    public void onResume(){
        super.onResume();

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

        Log.v("Debug","MainActivity onRestart invoked");
    }




}

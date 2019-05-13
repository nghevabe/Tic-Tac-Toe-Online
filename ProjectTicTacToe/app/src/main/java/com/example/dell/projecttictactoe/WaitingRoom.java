package com.example.dell.projecttictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitingRoom extends AppCompatActivity {

    TextView tvHost, tvGuest;
    TextView Player1,Win1,Lose1,Draw1,Status1;
    TextView Player2,Win2,Lose2,Draw2,Status2;
    Button btnReady, btnLeave;

    private ValueEventListener DBfire;

    private DatabaseReference myRef;

    private DatabaseReference Room;

    private DatabaseReference UserRef;

    private DatabaseReference Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_room);


        Init();


        UpdateStatus(Login.UserName,"online");

        LoadMyScore(Login.UserName);



        LoadEnemyScore(OnlineServer.userInvite);

        Waiting();

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStatusWait("ready");
            }
        });

    }

    public void Init()
    {

        Player1 = (TextView) findViewById(R.id.tvPlayer1);
        Player2 = (TextView) findViewById(R.id.tvPlayer2);

        Win1 = (TextView) findViewById(R.id.tvWin);
        Win2 = (TextView) findViewById(R.id.tvWin2);

        Lose1 = (TextView) findViewById(R.id.tvLose);
        Lose2 = (TextView) findViewById(R.id.tvLose2);

        Draw1 = (TextView) findViewById(R.id.tvDraw);
        Draw2 = (TextView) findViewById(R.id.tvDraw2);

        Status1 = (TextView) findViewById(R.id.tvStatus);
        Status2 = (TextView) findViewById(R.id.tvStatus2);

        btnReady = (Button) findViewById(R.id.buttonReady);

        btnLeave = (Button) findViewById(R.id.buttonLeave);

        tvHost = (TextView) findViewById(R.id.tvPlayer1);

        tvGuest = (TextView) findViewById(R.id.tvPlayer2);

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

    public void UpdateStatusWait(String text)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("PlayingRoom");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference room = Data.child(OnlineServer.nameRoom);


        DatabaseReference Player = room.child(Login.UserName);


        Player.setValue(text);


    }


    public void LoadMyScore(final String User)
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        myRef = database.getReference("User");

        DatabaseReference UserRef = myRef.child(User);

        // Read from the database
        DBfire = UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot Win = dataSnapshot.child("win");
                DataSnapshot Lose = dataSnapshot.child("lose");
                DataSnapshot Draw = dataSnapshot.child("draw");



                String win = Win.getValue(String.class);

                String lose = Lose.getValue(String.class);

                String draw = Draw.getValue(String.class);

                Player1.setText(User);

                Win1.setText("Win: "+win);

                Lose1.setText("Lose: "+lose);

                Draw1.setText("Draw: "+draw);



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });

    }


    public void LoadEnemyScore(final String User)
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        myRef = database.getReference("User");

        UserRef = myRef.child(User);

        // Read from the database
        DBfire = UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot Win = dataSnapshot.child("win");
                DataSnapshot Lose = dataSnapshot.child("lose");
                DataSnapshot Draw = dataSnapshot.child("draw");



                String win = Win.getValue(String.class);

                String lose = Lose.getValue(String.class);

                String draw = Draw.getValue(String.class);

                Player2.setText(User);

                Win2.setText("Win: "+win);

                Lose2.setText("Lose: "+lose);

                Draw2.setText("Draw: "+draw);



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
        DatabaseReference myRef = database.getReference("PlayingRoom");

        Room = myRef.child(OnlineServer.nameRoom);


        Log.v("ditme","room: "+OnlineServer.nameRoom);


        // Read from the database
        DBfire =  Room.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot PlayerStatus = dataSnapshot.child(Login.UserName);

                DataSnapshot EnemyStatus = dataSnapshot.child(OnlineServer.userInvite);

                String playerstatus = PlayerStatus.getValue(String.class);

                String enemystatus = EnemyStatus.getValue(String.class);

                Status1.setText(playerstatus);

                Status2.setText(enemystatus);

                Log.v("ditme","playerstaus: "+playerstatus+" enemy: "+enemystatus);

                if(playerstatus.equals("ready")&&enemystatus.equals("ready")) {

                    UpdateMessage(Login.UserName,"none");

                    Intent intend = new Intent(WaitingRoom.this, OnlineGamePlay.class);
                    startActivity(intend);

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
        UpdateStatus(Login.UserName,"offline");
        UpdateStatusWait("none");
        Room.removeEventListener(DBfire);
        UserRef.removeEventListener(DBfire);
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

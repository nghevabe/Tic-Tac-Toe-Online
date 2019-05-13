package com.example.dell.projecttictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineGamePlay extends AppCompatActivity implements View.OnClickListener {

    private ValueEventListener DBfire;

    private DatabaseReference myRef;
    private DatabaseReference Room;
    private DatabaseReference UserRef;

    private int PlayerSource ;

    private int EnemySource ;

    private int PlayerValue ;

    private int EnemyValue ;

    private String turn;

    private String current;

   // public int turn = 2;

    public boolean play = true;


    TextView t1;

    ImageButton b00,b01,b02
            ,b10,b11,b12
            ,b20,b21,b22;

    public int[][] Board =
            {{0,0,0},
             {0,0,0},
             {0,0,0}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_game_play);

        UpdateStatus(Login.UserName,"playing");



        InitView();
        setOnClickListener();

        LoadPlayer();

        if(PlayerValue==1)
        {
        t1.setText("Your Turn");
        }
    }


    public void InitView()
    {
        t1 = (TextView) findViewById(R.id.turner);
        b00 = (ImageButton) findViewById(R.id.btn00);
        b01 = (ImageButton) findViewById(R.id.btn01);
        b02 = (ImageButton) findViewById(R.id.btn02);

        b10 = (ImageButton) findViewById(R.id.btn10);
        b11 = (ImageButton) findViewById(R.id.btn11);
        b12 = (ImageButton) findViewById(R.id.btn12);

        b20 = (ImageButton) findViewById(R.id.btn20);
        b21 = (ImageButton) findViewById(R.id.btn21);
        b22 = (ImageButton) findViewById(R.id.btn22);

        //t1.setText("Player: "+current);

    }


    public void CheckPlayer(String x, String y){

        if(x.equals(Login.UserName))
        {
            PlayerSource = R.mipmap.x;
            PlayerValue = 1;
            EnemyValue = 2;
            EnemySource = R.mipmap.o;
            current = "1";
        }

        if(y.equals(Login.UserName))
        {
            PlayerSource = R.mipmap.o;
            PlayerValue = 2;
            EnemyValue = 1;
            EnemySource = R.mipmap.x;
            current = "2";
        }

    }




    public void PlayerMove(final ImageButton btn, final String x, final String y)
    {
        final int indexX = Integer.parseInt(x);
        final int indexY = Integer.parseInt(y);

        if(Board[indexX][indexY] == 0) {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();

            //Tham chiếu 1 Node
            DatabaseReference myRef = database.getReference("PlayingRoom");

            Room = myRef.child(OnlineServer.nameRoom);


            // Read from the database
            DBfire = Room.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    DataSnapshot Turn = dataSnapshot.child("Turn");

                    DataSnapshot PlayerX = dataSnapshot.child("PlayerX");

                    DataSnapshot PlayerY = dataSnapshot.child("PlayerY");

                    String playx = PlayerX.getValue(String.class);

                    String playy = PlayerY.getValue(String.class);

                    turn = Turn.getValue(String.class);

                   // ShowTurn(turn);

                    CheckPlayer(playx, playy);

                    if (turn.equals(current)) {

                        btn.setImageResource(PlayerSource);

                        Board[indexX][indexY] = PlayerValue;


                        UpdateMove(x, y);

                        ProcessTurn();


                        UpdateTurn(turn);

                    }


                    Room.removeEventListener(DBfire);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("NOT", "Failed to read value.", error.toException());
                }
            });
        }


    }

    public void ShowTurn(String turn)
    {

       // t1.setText("Player: "+turn);

        String value = Integer.toString(PlayerValue);

        if(turn.equals(value) )
        {
            t1.setText("Your Turn");
        }

        if(!turn.equals(value) )
        {
            t1.setText("Enemy Turn");
        }

    }

    public void LoadPlayer()
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        DatabaseReference myRef = database.getReference("PlayingRoom");

        Room = myRef.child(OnlineServer.nameRoom);



        // Read from the database
        DBfire =  Room.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                DataSnapshot Turn = dataSnapshot.child("Turn");

                String t = Turn.getValue(String.class);

                Toast.makeText(OnlineGamePlay.this,
                        "Turn: "+t, Toast.LENGTH_SHORT).show();

                ShowTurn(t);

                DataSnapshot PlayerX = dataSnapshot.child("PlayerX");

                DataSnapshot PlayerY = dataSnapshot.child("PlayerY");

                String playx = PlayerX.getValue(String.class);

                String playy = PlayerY.getValue(String.class);



                turn = Turn.getValue(String.class);



                CheckPlayer(playx,playy);

                DataSnapshot EnemyMove = dataSnapshot.child(OnlineServer.userInvite+" - Move");


                String enemymove = EnemyMove.getValue(String.class);

                if(!enemymove.equals("none")) {
                    String strMove[] = enemymove.split("-");

                    String movex = strMove[0].toString();
                    String movey = strMove[1].toString();

                    if (!movex.equals("none") && !movey.equals("none")) {

                        DrawEnemy(movex, movey);
                        ProcessGame();

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });

    }

    public void UpdateScore(String text, String score)
    {
        int scoreInt = Integer.parseInt(score);
        scoreInt++;

        score = Integer.toString(scoreInt);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        myRef = database.getReference("User");

        UserRef = myRef.child(Login.UserName);



        DatabaseReference win = UserRef.child("win");
        DatabaseReference lose = UserRef.child("lose");
        DatabaseReference draw = UserRef.child("draw");



        if(text.equals("You Win")) {
            win.setValue(score);
        }

        if(text.equals("You Lose"))
        {
            lose.setValue(score);
        }

        if(text.equals("Draw"))
        {
            draw.setValue(score);
        }



        UserRef.removeEventListener(DBfire);
    }

    public void UpScore(final String text)
    {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Tham chiếu 1 Node
        myRef = database.getReference("User");

        UserRef = myRef.child(Login.UserName);

        Toast.makeText(OnlineGamePlay.this,
                "Data : "+text + " - ", Toast.LENGTH_SHORT).show();

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



                if(text.equals("You Win")) {
                    UpdateScore("You Win", win);

                }


                if(text.equals("You Lose")) {
                    UpdateScore("You Lose", lose);
                }

                if(text.equals("Draw")) {
                    UpdateScore("Draw", draw);
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });

    }

    public void DrawEnemy(String chuoiX, String chuoiY)
    {

        int x = Integer.parseInt(chuoiX);
        int y = Integer.parseInt(chuoiY);

        Board[x][y] = EnemyValue;

        if(x==0&&y==0) {
            b00.setImageResource(EnemySource);
        }

        if(x==0&&y==1) {
            b01.setImageResource(EnemySource);
        }

        if(x==0&&y==2) {
            b02.setImageResource(EnemySource);
        }

        //

        if(x==1&&y==0) {
            b10.setImageResource(EnemySource);
        }

        if(x==1&&y==1) {
            b11.setImageResource(EnemySource);
        }

        if(x==1&&y==2) {
            b12.setImageResource(EnemySource);
        }

        //

        if(x==2&&y==0) {
            b20.setImageResource(EnemySource);
        }

        if(x==2&&y==1) {
            b21.setImageResource(EnemySource);
        }

        if(x==2&&y==2) {
            b22.setImageResource(EnemySource);
        }

    }

    public void setOnClickListener()
    {

        b00.setOnClickListener(this);
        b01.setOnClickListener(this);
        b02.setOnClickListener(this);
        b10.setOnClickListener(this);
        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
        b20.setOnClickListener(this);
        b21.setOnClickListener(this);
        b22.setOnClickListener(this);

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
//        Room.removeEventListener(DBfire);
        UpdateStatus(Login.UserName,"offline");

        Log.v("Debug","MainActivity OnPause invoked");
    }

    @Override
    public void onResume(){
        super.onResume();
        UpdateStatus(Login.UserName,"playing");
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
        UpdateStatus(Login.UserName,"playing");
        Log.v("Debug","MainActivity onRestart invoked");
    }


    public void EndGame(String notifi)
    {
        Toast.makeText(OnlineGamePlay.this,
                notifi, Toast.LENGTH_SHORT).show();

        play = false;
    }

    public boolean Draw()
    {

        boolean playing = false;

         for(int i=0;i<3;i++)
         {

             for(int j=0;j<3;j++)
             {

                 if(Board[i][j]==0)
                 {
                     playing = true;
                 }

             }
         }

         return playing;

    }

    public void ProcessGame()
    {

        if (!Draw())
        {
            NotiDialog("Draw");
        }

        // hang ngang 1
        if(Board[0][0]==Board[0][1]&&Board[0][1]==Board[0][2]&&Board[0][2]==PlayerValue)
        {
            NotiDialog("You Win");
        }

        if(Board[0][0]==Board[0][1]&&Board[0][1]==Board[0][2]&&Board[0][2]==EnemyValue)
        {
            NotiDialog("You Lose");
        }


        // hang ngang 2
        if(Board[1][0]==Board[1][1]&&Board[1][1]==Board[1][2]&&Board[1][2]==PlayerValue)
        {
            NotiDialog("You Win");
        }

        if(Board[1][0]==Board[1][1]&&Board[1][1]==Board[1][2]&&Board[1][2]==EnemyValue)
        {
            NotiDialog("You Lose");
        }

        // hang ngang 3
        if(Board[2][0]==Board[2][1]&&Board[2][1]==Board[2][2]&&Board[2][2]==PlayerValue)
        {
            NotiDialog("You Win");
        }

        if(Board[2][0]==Board[2][1]&&Board[2][1]==Board[2][2]&&Board[2][2]==EnemyValue)
        {
            NotiDialog("You Lose");
        }

        // hang doc 1
        if(Board[0][0]==Board[1][0]&&Board[1][0]==Board[2][0]&&Board[2][0]==PlayerValue)
        {
            NotiDialog("You Win");
        }

        if(Board[0][0]==Board[1][0]&&Board[1][0]==Board[2][0]&&Board[2][0]==EnemyValue)
        {
            NotiDialog("You Lose");
        }

        // hang doc 2
        if(Board[0][1]==Board[1][1]&&Board[1][1]==Board[2][1]&&Board[2][1]==PlayerValue)
        {
            NotiDialog("You Win");
        }

        if(Board[0][1]==Board[1][1]&&Board[1][1]==Board[2][1]&&Board[2][1]==EnemyValue)
        {
            NotiDialog("You Lose");
        }


        // hang doc 3
        if(Board[0][2]==Board[1][2]&&Board[1][2]==Board[2][2]&&Board[2][2]==PlayerValue)
        {
            NotiDialog("You Win");
        }

        if(Board[0][2]==Board[1][2]&&Board[1][2]==Board[2][2]&&Board[2][2]==EnemyValue)
        {
            NotiDialog("You Lose");
        }

        //hang cheo 1
        if(Board[0][0]==Board[1][1]&&Board[1][1]==Board[2][2]&&Board[2][2]==PlayerValue)
        {
            NotiDialog("You Win");
        }

        if(Board[0][0]==Board[1][1]&&Board[1][1]==Board[2][2]&&Board[2][2]==EnemyValue)
        {
            NotiDialog("You Lose");
        }


        //hang cheo 2
        if(Board[0][2]==Board[1][1]&&Board[1][1]==Board[2][0]&&Board[2][0]==PlayerValue)
        {
            NotiDialog("You Win");
        }

        if(Board[0][2]==Board[1][1]&&Board[1][1]==Board[2][0]&&Board[2][0]==EnemyValue)
        {
            NotiDialog("You Lose");
        }


    }

    public void ProcessTurn()
    {

        int turnInt = Integer.parseInt(turn);
        turnInt++;

        if(turnInt > 2)
        {
            turnInt = 1;
        }

        turn = Integer.toString(turnInt);
    }

    public void UpdateTurn(String yourTurn)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("PlayingRoom");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference Room = Data.child(OnlineServer.nameRoom);



        DatabaseReference turn = Room.child("Turn");



        turn.setValue(yourTurn);


    }





    public void UpdateMove(String x, String y)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("PlayingRoom");

        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference Room = Data.child(OnlineServer.nameRoom);


        DatabaseReference MyMove = Room.child(Login.UserName+" - Move");


        MyMove.setValue(x+"-"+y);



    }



    public void CreateRoom()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Chọn tên database () (Nếu chưa có thì tạo ra 1 database mới)
        DatabaseReference Data = database.getReference("PlayingRoom");



        //Chọn 1 Node (nếu chưa có thì tạo)
        DatabaseReference room = Data.child(OnlineServer.nameRoom);

        String str[] = OnlineServer.nameRoom.split("-");

        String host = str[0];

        String guest = str[1];

        DatabaseReference hostStatus = room.child(host);

        DatabaseReference guestStatus = room.child(guest);

        DatabaseReference turn = room.child("Turn");

        DatabaseReference hostMove = room.child(host+" - Move");

        DatabaseReference guestMove = room.child(guest+" - Move");



        hostStatus.setValue("none");

        guestStatus.setValue("none");



        turn.setValue("1");

        hostMove.setValue("none");

        guestMove.setValue("none");





    }



    public void NotiDialog(final String text)
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                OnlineGamePlay.this);

        // set title
        alertDialogBuilder.setTitle("Game Over");



        // set dialog message
        alertDialogBuilder
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton("Accept",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity

                        if(text.equals("You Win")) {
                            CreateRoom();
                        }

                        UpScore(text);
                       // UpdateScore("x","x");
                        Intent intend = new Intent(OnlineGamePlay.this, WaitingRoom.class);
                        startActivity(intend);



                    }
                });



        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


        Room.removeEventListener(DBfire);








           // Room.removeEventListener(DBfire);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn00:


                  PlayerMove(b00, "0", "0");


                break;



            case R.id.btn01:


                    PlayerMove(b01, "0", "1");


                break;



            case R.id.btn02:


                    PlayerMove(b02, "0", "2");


                break;


            case R.id.btn10:


                    PlayerMove(b10, "1", "0");



                break;


            case R.id.btn11:

                    PlayerMove(b11, "1", "1");


                break;



            case R.id.btn12:


                    PlayerMove(b12, "1", "2");



                break;


            case R.id.btn20:


                    PlayerMove(b20, "2", "0");


                break;


            case R.id.btn21:


                    PlayerMove(b21, "2", "1");



                break;


            case R.id.btn22:


                    PlayerMove(b22, "2", "2");



                break;
        }


    }
}

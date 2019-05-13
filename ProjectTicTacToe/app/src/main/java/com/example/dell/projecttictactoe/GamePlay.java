package com.example.dell.projecttictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GamePlay extends AppCompatActivity implements View.OnClickListener {

    public int turn = 2;

    public String mode = "Single";

    public String AIstate = "Standby";

    public boolean play = true;


    TextView t1;

    ImageButton b00,b01,b02
               ,b10,b11,b12
               ,b20,b21,b22;

    public int[][] Board = {{0,0,0},
                            {0,0,0},
                            {0,0,0}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);



        InitView();
        setOnClickListener();




    }


    public void InitView()
    { t1 = (TextView) findViewById(R.id.turner);
      b00 = (ImageButton) findViewById(R.id.btn00);
      b01 = (ImageButton) findViewById(R.id.btn01);
      b02 = (ImageButton) findViewById(R.id.btn02);

      b10 = (ImageButton) findViewById(R.id.btn10);
      b11 = (ImageButton) findViewById(R.id.btn11);
      b12 = (ImageButton) findViewById(R.id.btn12);

      b20 = (ImageButton) findViewById(R.id.btn20);
      b21 = (ImageButton) findViewById(R.id.btn21);
      b22 = (ImageButton) findViewById(R.id.btn22);


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

    public void Attack()
    {

        // Hàng Ngang 1
        if(Board[0][0]==1&&Board[0][1]==1&&Board[0][2]==0&&AIstate.equals("Standby"))
        {
            
            AIMove(0,2);

        }

        if(Board[0][1]==1&&Board[0][2]==1&&Board[0][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,0);

        }

        if(Board[0][0]==1&&Board[0][2]==1&&Board[0][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,1);

        }

        // Hàng Ngang 2
        if(Board[1][0]==1&&Board[1][1]==1&&Board[1][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,2);

        }

        if(Board[1][1]==1&&Board[1][2]==1&&Board[1][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,0);

        }

        if(Board[1][0]==1&&Board[1][2]==1&&Board[1][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,1);

        }

        // Hàng Ngang 3
        if(Board[2][0]==1&&Board[2][1]==1&&Board[2][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,2);

        }

        if(Board[2][1]==1&&Board[2][2]==1&&Board[2][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,0);

        }

        if(Board[2][0]==1&&Board[2][2]==1&&Board[2][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,1);

        }


        // Hàng Doc 1
        if(Board[0][0]==1&&Board[1][0]==1&&Board[2][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,0);

        }

        if(Board[2][0]==1&&Board[1][0]==1&&Board[0][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,0);

        }

        if(Board[2][0]==1&&Board[0][0]==1&&Board[1][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,0);

        }

        // Hàng Doc 2
        if(Board[0][1]==1&&Board[1][1]==1&&Board[2][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,1);

        }

        if(Board[2][1]==1&&Board[1][1]==1&&Board[0][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,1);

        }

        if(Board[2][1]==1&&Board[0][1]==1&&Board[1][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,1);

        }

        // Hàng Doc 3
        if(Board[0][2]==1&&Board[1][2]==1&&Board[2][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,2);

        }

        if(Board[2][2]==1&&Board[1][2]==1&&Board[0][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,2);

        }

        if(Board[2][2]==1&&Board[0][2]==1&&Board[1][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,2);

        }

        // Hàng Chéo 1 (\)
        if(Board[0][0]==1&&Board[1][1]==1&&Board[2][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,2);

        }

        if(Board[1][1]==1&&Board[2][2]==1&&Board[0][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,0);

        }

        if(Board[0][0]==1&&Board[2][2]==1&&Board[1][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,1);

        }


        // Hàng Chéo 2 (/)
        if(Board[2][0]==1&&Board[1][1]==1&&Board[0][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,2);

        }

        if(Board[1][1]==1&&Board[0][2]==1&&Board[2][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,0);

        }

        if(Board[0][0]==1&&Board[2][2]==1&&Board[1][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,1);

        }


    }

    public void Block()
    {

        // Hàng Ngang 1
        if(Board[0][0]==2&&Board[0][1]==2&&Board[0][2]==0&&AIstate.equals("Standby"))
        {

            AIMove(0,2);

        }

        if(Board[0][1]==2&&Board[0][2]==2&&Board[0][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,0);

        }

        if(Board[0][0]==2&&Board[0][2]==2&&Board[0][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,1);

        }

        // Hàng Ngang 2
        if(Board[1][0]==2&&Board[1][1]==2&&Board[1][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,2);

        }

        if(Board[1][1]==2&&Board[1][2]==2&&Board[1][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,0);

        }

        if(Board[1][0]==2&&Board[1][2]==2&&Board[1][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,1);

        }

        // Hàng Ngang 3
        if(Board[2][0]==2&&Board[2][1]==2&&Board[2][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,2);

        }

        if(Board[2][1]==2&&Board[2][2]==2&&Board[2][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,0);

        }

        if(Board[2][0]==2&&Board[2][2]==2&&Board[2][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,1);

        }


        // Hàng Doc 1
        if(Board[0][0]==2&&Board[1][0]==2&&Board[2][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,0);

        }

        if(Board[2][0]==2&&Board[1][0]==2&&Board[0][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,0);

        }

        if(Board[2][0]==2&&Board[0][0]==2&&Board[1][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,0);

        }

        // Hàng Doc 2
        if(Board[0][1]==2&&Board[1][1]==2&&Board[2][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,1);

        }

        if(Board[2][1]==2&&Board[1][1]==2&&Board[0][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,1);

        }

        if(Board[2][1]==2&&Board[0][1]==2&&Board[1][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,1);

        }

        // Hàng Doc 3
        if(Board[0][2]==2&&Board[1][2]==2&&Board[2][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,2);

        }

        if(Board[2][2]==2&&Board[1][2]==2&&Board[0][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,2);

        }

        if(Board[2][2]==2&&Board[0][2]==2&&Board[1][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,2);

        }

        // Hàng Chéo 1 (\)
        if(Board[0][0]==2&&Board[1][1]==2&&Board[2][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,2);

        }

        if(Board[1][1]==2&&Board[2][2]==2&&Board[0][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,0);

        }

        if(Board[0][0]==2&&Board[2][2]==2&&Board[1][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,1);

        }


        // Hàng Chéo 2 (/)
        if(Board[2][0]==2&&Board[1][1]==2&&Board[0][2]==0&&AIstate.equals("Standby"))
        {
            AIMove(0,2);

        }

        if(Board[1][1]==2&&Board[0][2]==2&&Board[2][0]==0&&AIstate.equals("Standby"))
        {
            AIMove(2,0);

        }

        if(Board[0][0]==2&&Board[0][2]==2&&Board[1][1]==0&&AIstate.equals("Standby"))
        {
            AIMove(1,1);

        }


    }

    public boolean Play()
    {
        boolean draw = false;
        for(int i=0;i<3;i++)
        {

            for(int j=0;j<3;j++)
            {
                if(Board[i][j]==0)
                {
                    draw = true;
                }
            }
        }

        return draw;

    }

    public void AIturn()
    {
        if(Play()) {
            AIstate = "Standby";

            Attack();
            Block();
            ChoosePosition();
        }else {
            EndGame("DRAW");
        }


    }

    public void ChoosePosition()
    {

        if(AIstate.equals("Standby")) {
            Random rand = new Random();

            int x = rand.nextInt(3);
            int y = rand.nextInt(3);


            if (Board[x][y] == 0) {
                AIMove(x, y);
            } else {
                ChoosePosition();
            }
        }

    }



    public void AIMove(int x, int y)
    {

            AIstate = "Move";

            Board[x][y] = 1;
           // AIstate = "Atk";

            if (x == 0 && y == 0) {
                b00.setImageResource(R.mipmap.o);
            }

            if (x == 0 && y == 1) {
                b01.setImageResource(R.mipmap.o);
            }

            if (x == 0 && y == 2) {
                b02.setImageResource(R.mipmap.o);
            }

            if (x == 1 && y == 0) {
                b10.setImageResource(R.mipmap.o);
            }

            if (x == 1 && y == 1) {
                b11.setImageResource(R.mipmap.o);
            }

            if (x == 1 && y == 2) {
                b12.setImageResource(R.mipmap.o);
            }

            if (x == 2 && y == 0) {
                b20.setImageResource(R.mipmap.o);

            }

            if (x == 2 && y == 1) {
                b21.setImageResource(R.mipmap.o);
            }

            if (x == 2 && y == 2) {
                b22.setImageResource(R.mipmap.o);
            }


    }





    public boolean Move(int x, int y)
    {

        boolean r = false;

        if(play) {

            if (Board[x][y] == 0) {
                Board[x][y] = turn;
                r = true;
                turn++;

            }


            if (turn > 2) {
                turn = 1;
            }
        }








        String t = Integer.toString(turn);



        if(t.equals("1")) {
            t1.setText("Player : O");
        }

        if(t.equals("2")) {
            t1.setText("Player : X");
        }
        return  r;

    }

    public void EndGame(String notifi)
    {
        Toast.makeText(GamePlay.this,
                notifi, Toast.LENGTH_SHORT).show();

        play = false;
    }

    public void ProcessGame()
    {
        // hang ngang 1
        if(Board[0][0]==Board[0][1]&&Board[0][1]==Board[0][2]&&Board[0][2]==1)
        {
            EndGame("O Win");
        }

        if(Board[0][0]==Board[0][1]&&Board[0][1]==Board[0][2]&&Board[0][2]==2)
        {
            EndGame("X Win");
        }


        // hang ngang 2
        if(Board[1][0]==Board[1][1]&&Board[1][1]==Board[1][2]&&Board[1][2]==1)
        {
            EndGame("O Win");
        }

        if(Board[1][0]==Board[1][1]&&Board[1][1]==Board[1][2]&&Board[1][2]==2)
        {
            EndGame("X Win");
        }

        // hang ngang 3
        if(Board[2][0]==Board[2][1]&&Board[2][1]==Board[2][2]&&Board[2][2]==1)
        {
            EndGame("O Win");
        }

        if(Board[2][0]==Board[2][1]&&Board[2][1]==Board[2][2]&&Board[2][2]==2)
        {
            EndGame("X Win");
        }





        // hang doc 1
        if(Board[0][0]==Board[1][0]&&Board[1][0]==Board[2][0]&&Board[2][0]==1)
        {
            EndGame("O Win");
        }

        if(Board[0][0]==Board[1][0]&&Board[1][0]==Board[2][0]&&Board[2][0]==2)
        {
            EndGame("X Win");
        }

        // hang doc 2
        if(Board[0][1]==Board[1][1]&&Board[1][1]==Board[2][1]&&Board[2][1]==1)
        {
            EndGame("O Win");
        }

        if(Board[0][1]==Board[1][1]&&Board[1][1]==Board[2][1]&&Board[2][1]==2)
        {
            EndGame("X Win");
        }


        // hang doc 3
        if(Board[0][2]==Board[1][2]&&Board[1][2]==Board[2][2]&&Board[2][2]==1)
        {
            EndGame("O Win");
        }

        if(Board[0][2]==Board[1][2]&&Board[1][2]==Board[2][2]&&Board[2][2]==2)
        {
            EndGame("X Win");
        }

        //hang cheo 1
        if(Board[0][0]==Board[1][1]&&Board[1][1]==Board[2][2]&&Board[2][2]==1)
        {
            EndGame("O Win");
        }

        if(Board[0][0]==Board[1][1]&&Board[1][1]==Board[2][2]&&Board[2][2]==2)
        {
            EndGame("X Win");
        }


        //hang cheo 2
        if(Board[0][2]==Board[1][1]&&Board[1][1]==Board[2][0]&&Board[2][0]==1)
        {
            EndGame("O Win");
        }

        if(Board[0][2]==Board[1][1]&&Board[1][1]==Board[2][0]&&Board[2][0]==2)
        {
            EndGame("X Win");
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn00:

                    if(SelectMode.mode.equals("Single"))
                    {
                        b00.setImageResource(R.mipmap.x);
                        Board[0][0] = 2;
                        AIturn();
                        ProcessGame();
                    }


                    if(SelectMode.mode.equals("Two")) {
                        if (Move(0, 0)) {
                            if (turn == 1) {

                                b00.setImageResource(R.mipmap.x);
                            }
                            if (turn == 2) {

                                b00.setImageResource(R.mipmap.o);
                            }

                            ProcessGame();
                        }
                    }


                break;

            case R.id.btn01:

                if(SelectMode.mode.equals("Single"))
                {
                    b01.setImageResource(R.mipmap.x);
                    Board[0][1] = 2;
                    AIturn();
                    ProcessGame();
                }

                if(SelectMode.mode.equals("Two")) {
                    if (Move(0, 1)) {
                        if (turn == 1) {

                            b01.setImageResource(R.mipmap.x);
                        }
                        if (turn == 2) {

                            b01.setImageResource(R.mipmap.o);
                        }

                        ProcessGame();
                    }
                }


                break;


            case R.id.btn02:

                if(SelectMode.mode.equals("Single"))
                {
                    b02.setImageResource(R.mipmap.x);
                    Board[0][2] = 2;
                    AIturn();
                    ProcessGame();
                }


                if(SelectMode.mode.equals("Two")) {
                    if (Move(0, 2)) {
                        if (turn == 1) {

                            b02.setImageResource(R.mipmap.x);
                        }
                        if (turn == 2) {

                            b02.setImageResource(R.mipmap.o);
                        }

                        ProcessGame();
                    }
                }

                break;


            case R.id.btn10:

                if(SelectMode.mode.equals("Single"))
                {
                    b10.setImageResource(R.mipmap.x);
                    Board[1][0] = 2;
                    AIturn();
                    ProcessGame();
                }


                if(SelectMode.mode.equals("Two")) {
                    if (Move(1, 0)) {
                        if (turn == 1) {

                            b10.setImageResource(R.mipmap.x);
                        }
                        if (turn == 2) {

                            b10.setImageResource(R.mipmap.o);
                        }

                        ProcessGame();
                    }

                }

                break;


            case R.id.btn11:

                if(SelectMode.mode.equals("Single"))
                {
                    b11.setImageResource(R.mipmap.x);
                    Board[1][1] = 2;
                    AIturn();
                    ProcessGame();
                }


                if(SelectMode.mode.equals("Two")) {
                    if (Move(1, 1)) {
                        if (turn == 1) {

                            b11.setImageResource(R.mipmap.x);
                        }
                        if (turn == 2) {

                            b11.setImageResource(R.mipmap.o);
                        }

                        ProcessGame();
                    }
                }

                break;


            case R.id.btn12:

                if(SelectMode.mode.equals("Single"))
                {
                    b12.setImageResource(R.mipmap.x);
                    Board[1][2] = 2;
                    AIturn();
                    ProcessGame();
                }


                if(SelectMode.mode.equals("Two")) {
                    if (Move(1, 2)) {
                        if (turn == 1) {

                            b12.setImageResource(R.mipmap.x);
                        }
                        if (turn == 2) {

                            b12.setImageResource(R.mipmap.o);
                        }

                        ProcessGame();
                    }
                }

                break;


            case R.id.btn20:

                if(SelectMode.mode.equals("Single"))
                {
                    b20.setImageResource(R.mipmap.x);
                    Board[2][0] = 2;
                    AIturn();
                    ProcessGame();
                }

                if(SelectMode.mode.equals("Two")) {
                    if (Move(2, 0)) {
                        if (turn == 1) {

                            b20.setImageResource(R.mipmap.x);
                        }
                        if (turn == 2) {

                            b20.setImageResource(R.mipmap.o);
                        }

                        ProcessGame();
                    }
                }

                break;


            case R.id.btn21:

                if(SelectMode.mode.equals("Single"))
                {
                    b21.setImageResource(R.mipmap.x);
                    Board[2][1] = 2;
                    AIturn();
                    ProcessGame();
                }


                if(SelectMode.mode.equals("Two")) {
                    if (Move(2, 1)) {
                        if (turn == 1) {

                            b21.setImageResource(R.mipmap.x);
                        }
                        if (turn == 2) {

                            b21.setImageResource(R.mipmap.o);
                        }

                        ProcessGame();
                    }
                }

                break;


            case R.id.btn22:

                if(SelectMode.mode.equals("Single"))
                {
                    b22.setImageResource(R.mipmap.x);
                    Board[2][2] = 2;
                    AIturn();
                    ProcessGame();
                }


                if(SelectMode.mode.equals("Two")) {
                    if (Move(2, 2)) {
                        if (turn == 1) {

                            b22.setImageResource(R.mipmap.x);
                        }
                        if (turn == 2) {

                            b22.setImageResource(R.mipmap.o);
                        }

                        ProcessGame();
                    }
                }

                break;
        }

    }
}

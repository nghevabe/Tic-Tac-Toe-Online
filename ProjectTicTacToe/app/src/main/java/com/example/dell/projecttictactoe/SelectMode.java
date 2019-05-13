package com.example.dell.projecttictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectMode extends AppCompatActivity {

    Button single,two,onl;

    public static String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mode);

        single = (Button) findViewById(R.id.btn1);

        two = (Button) findViewById(R.id.btn2);

        onl = (Button) findViewById(R.id.btn3);

        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mode = "Single";
                Intent intend = new Intent(SelectMode.this, GamePlay.class);
                startActivity(intend);

            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mode = "Two";
                Intent intend = new Intent(SelectMode.this, GamePlay.class);
                startActivity(intend);

            }
        });

        onl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "Onl";
                Intent intend = new Intent(SelectMode.this, OnlineServer.class);
                startActivity(intend);
            }
        });



    }
}

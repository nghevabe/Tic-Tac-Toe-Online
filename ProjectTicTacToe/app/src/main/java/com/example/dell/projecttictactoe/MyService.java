package com.example.dell.projecttictactoe;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyService extends Service {

    private ValueEventListener DBfire;

    private DatabaseReference Message;

   // private DatabaseReference myRef;

  //  private DatabaseReference UserInvite;

    private MediaPlayer mediaPlayer;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();



       // mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.nhac);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
// Chơi nhạc.
       // mediaPlayer.start();
        AcceptInvite();
        return START_STICKY;
    }

    // Hủy bỏ dịch vụ.
    @Override
    public void onDestroy() {
// Giải phóng nguồn dữ nguồn phát nhạc.
        mediaPlayer.release();
        super.onDestroy();
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

                    NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(MyService.this, Notification.CHANNEL_1_ID)
                            .setContentTitle("BoardGame")
                            .setSmallIcon(R.mipmap.small_icon)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("Invite"))
                            .setContentText("Bạn Nhận Được Lời Mời Từ "+message)
                            .setAutoCancel(false)
                            .setOnlyAlertOnce(true);

                    NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nManager.notify(1, alamNotificationBuilder.build());
               }

              //  Message.removeEventListener(DBfire);

             //   Toast.makeText(MyService.this,
                  //      message, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOT", "Failed to read value.", error.toException()); }
        });



    }

}

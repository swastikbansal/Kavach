package com.example.kavach;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String messageBody = smsMessage.getMessageBody();

                    // Compare the messageBody with your desired string and take action accordingly.
                    if (messageBody.equals("I am in an Emergency Situation. I need Help.")) {
                        Toast.makeText(context, "SmS confirmed", Toast.LENGTH_SHORT).show();
                        playAudio(context);
                    }
                }
            }
        }
    }
    private void playAudio(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.test); // Replace with your audio file
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
    }
}


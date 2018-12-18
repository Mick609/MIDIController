package com.example.mick.midicontroller.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.mick.midicontroller.R;
import com.example.mick.midicontroller.services.AppService;
import com.example.mick.midicontroller.services.AudioPlayer;
import com.example.mick.midicontroller.services.SensorService;

public class MainActivity extends AppCompatActivity {
    private SensorService mSensorService;
    private AppService app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = new AppService(this);

        mSensorService = new SensorService(this);
        mSensorService.init();
    }

    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.speedButton:
//                float speed = Float.valueOf(speedText.getText().toString());
//                mAudioPlayer.setSpeed(speed);
//                break;
//            case R.id.pitchButton:
//                float pitch = Float.valueOf(pitchText.getText().toString());
//                mAudioPlayer.setPitch(pitch);
//                break;
//            case R.id.playButton:
//                mAudioPlayer.play();
//                break;
//            case R.id.pauseButton:
//                mAudioPlayer.pause();
//                break;
//            default:
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorService.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorService.resume();
    }
}

package com.example.mick.midicontroller.services;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.example.mick.midicontroller.R;
import com.example.mick.midicontroller.domains.LinearAccelerometerEntry;

import java.util.ArrayList;
import java.util.List;

public class SensorService {
    private SensorManager mSensorManager;
    private AudioPlayer mAudioPlayer;
    private Context mContext;
    private AppService app;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;
    private List<LinearAccelerometerEntry> sensorEntries = new ArrayList<>();
    private int sensorEntriesSize = 20;
    TextView playingInfo;
    TextView timerText;

    //    pause
    private final static int STAGE_1 = 1;

    //    normal speed
    private final static int STAGE_2 = 2;

    //    x1.5 speed
    private final static int STAGE_3 = 3;

    private int currentStage = STAGE_1;

    public SensorService(Context mContext) {
        this.mContext = mContext;
        app = new AppService(mContext);

        playingInfo = (TextView) ((Activity) mContext).findViewById(R.id.playingInfoText);
        timerText = (TextView) ((Activity) mContext).findViewById(R.id.timerText);
        mAudioPlayer = new AudioPlayer(mContext, R.raw.example);
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                SensorEventReceived(sensorEvent);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void init() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensor = null;
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            app.mLog("Linear Accelerometer Exists.");
            registerLinearAccelerometer();
        } else {
            app.mLog("Linear Accelerometer Does not Exist.");
        }
    }

    private void registerLinearAccelerometer() {
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

//        50Hz
        mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void SensorEventReceived(SensorEvent sensorEvent) {
        if (sensorEntries.size() < sensorEntriesSize) {
            LinearAccelerometerEntry newEntry = new LinearAccelerometerEntry(
                    sensorEvent.values[0],
                    sensorEvent.values[1],
                    sensorEvent.values[2],
                    sensorEvent.timestamp);
            newEntry.setEuclideanDistance(app.getEuclideanDistance(sensorEvent.values));

            sensorEntries.add(newEntry);
        } else {
            LinearAccelerometerEntry newEntry = new LinearAccelerometerEntry(
                    sensorEvent.values[0],
                    sensorEvent.values[1],
                    sensorEvent.values[2],
                    sensorEvent.timestamp);
            newEntry.setEuclideanDistance(app.getEuclideanDistance(sensorEvent.values));

            sensorEntries.add(newEntry);
            sensorEntries.remove(0);

            double sum = 0;
            for (int i = 0; i < sensorEntries.size(); i++) {
                sum += sensorEntries.get(i).getEuclideanDistance() * sensorEntries.get(i).getEuclideanDistance();
            }
            double ret = Math.sqrt(sum);
            if (ret <= 10) {
                if (currentStage != STAGE_1) {
                    currentStage = STAGE_1;
                    app.mLog("Change to STAGE_1");
                    mAudioPlayer.pause();

                    Activity activity = (Activity) mContext;
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            playingInfo.setText("Pause");
                        }
                    });
                }
            } else if (ret < 40) {
                if (currentStage != STAGE_2) {
                    currentStage = STAGE_2;
                    app.mLog("Change to STAGE_2");
                    mAudioPlayer.play();
                    mAudioPlayer.setSpeed(1.0f);

                    Activity activity = (Activity) mContext;
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            playingInfo.setText("Normal speed");
                        }
                    });
                }
            } else {
                if (currentStage != STAGE_3) {
                    currentStage = STAGE_3;
                    app.mLog("Change to STAGE_3");
                    mAudioPlayer.play();
                    mAudioPlayer.setSpeed(1.5f);

                    Activity activity = (Activity) mContext;
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            playingInfo.setText("x1.5 speed");
                        }
                    });
                }
            }
        }
        Activity activity = (Activity) mContext;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                timerText.setText(mAudioPlayer.getPosition() + " / " + mAudioPlayer.getDuration());
            }
        });
    }

    public void resume() {
        registerLinearAccelerometer();
    }

    public void pause() {
        mAudioPlayer.pause();
        mSensorManager.unregisterListener(mSensorEventListener);
    }

    public List<Sensor> getSensorList() {
        return mSensorManager.getSensorList(Sensor.TYPE_ALL);
    }
}

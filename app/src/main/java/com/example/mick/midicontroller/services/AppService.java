package com.example.mick.midicontroller.services;

import android.content.Context;
import android.util.Log;

public class AppService {
    private static final String TAG = "MIDI";
    private Context mContext;

    public AppService(Context mContext) {
        this.mContext = mContext;
    }

    public void mLog(String msg) {
        Log.i(TAG, msg);
    }

    public double getEuclideanDistance(float[] data) {
        float sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i] * data[i];
        }
        double ret = Math.sqrt(sum);
        return ret;
    }

    public String getFirstXCharacters(int x, String originalString) {
        String ret = "";
        if (originalString.length() <= x) {
            return originalString;
        } else {
            for (int i = 0; i < x; i++) {
                ret+=originalString.charAt(i);
            }
            return ret;
        }
    }
}

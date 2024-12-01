package com.example.checkmate;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyHostApduService extends HostApduService {
    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        Log.d("HCE", "Received APDU: " + ByteArrayToHexString(bytes));
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return  sharedPref.getString("deviceToken", "null").getBytes();
    }

    @Override
    public void onDeactivated(int reason) {

    }

    private String ByteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for(byte b: bytes)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}

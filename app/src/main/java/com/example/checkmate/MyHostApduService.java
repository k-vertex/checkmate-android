package com.example.checkmate;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

public class MyHostApduService extends HostApduService {
    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        Log.d("HCE", "Received APDU: " + ByteArrayToHexString(bytes));
        return "Hello".getBytes();
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

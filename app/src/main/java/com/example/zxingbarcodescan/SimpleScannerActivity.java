package com.example.zxingbarcodescan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.*;
import android.app.*;
import android.content.*;
import android.util.*;

public class SimpleScannerActivity extends Activity
        implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_simple_scanner);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d("tag", "Scan Result - " + rawResult.getText() + " / "
                + rawResult.getBarcodeFormat().toString());
        Intent intent = new Intent();
        intent.putExtra("BarcodeValue", rawResult.getText());
        intent.putExtra("BarcodeFormat", rawResult.getBarcodeFormat().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}

package com.example.zxingbarcodescan;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/*
 * ZxingBarcodeScan : Barcode scan example using Zxing library
 * Author : DONGGEUN JUNG (Dennis)
 * Email : topsan72@gmail.com / topofsan@naver.com
 */

public class MainActivity extends AppCompatActivity {
    final static int ACT_BARCODE_SCAN = 1004;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textView1);

        // Check Camera permission
        checkPermission();
    }

    // Check Camera permission
    private boolean checkPermission() {
        // 마시멜로우 이후 버전이라면 사용자에게 Permission 을 획득
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // 사용자가 Permission 을 부여하지 않았다면
            // 권한을 요청하는 팝업창을 표시
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        android.Manifest.permission.CAMERA)){
                    Toast.makeText(this,
                            "No Permission to use the Camera",
                            Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]
                                {android.Manifest.permission.CAMERA},
                        111);
                return false;
            }
        }
        return true;
    }

    // 권한 요청 결과 이벤트 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] grantResults) {
        switch (requestCode){
            // Camera 권한 요청 결과 일때
            case 111:
                // 사용자가 권한 부여를 거절 했을때
                if (grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,
                            "Cannot run application because Camera permission is not granted",
                            Toast.LENGTH_SHORT).show();
                }
                // 사용자가 권한을 부여했을때
                else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,
                        permissions, grantResults);
                break;
        }
    }

    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), SimpleScannerActivity.class);
        startActivityForResult(intent, ACT_BARCODE_SCAN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACT_BARCODE_SCAN:
                if (resultCode == Activity.RESULT_OK) {
                    String barcodeValue = data.getStringExtra("BarcodeValue");
                    String barcodeFormat = data.getStringExtra("BarcodeFormat");
                    textView1.setText(barcodeValue + " / " + barcodeFormat);
                }
                break;
        }
    }

}

package com.app.androidkt.googlevisionapi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class HomePage extends AppCompatActivity {
    MaterialCardView qr_scanner,ocr_scanner ;
    Uri imageUri ;
    Bitmap imageBitmap ;
    TextView carIdtxt, companyIdtxt ,carnumbertxt;
    EditText cost , kiloMiters,ekramya,kilom ;
    Button send_to_server_btn ;
    String car_id,company_id , kilo ,costs,ekramyas ,kiloms ;
    ProgressBar p_load ;
    String all_costs ;
    String user_id ;


    static final int CAMERA_RESULT_CODE = 1000;
    final int RequestCameraPermissionID = 1001;
    static final int QR_SCANNER_RESULT_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

    }
}
package com.ca.dell.ecapturer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CaptureActivity extends AppCompatActivity {

    private static int REQ_1 = 1;
    private static String url = "https://api.cognitive.azure.cn/emotion/v1.0/recognize";

    private Handler handler = new Handler();
    private TextView textView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        mImageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);

    }

    public void startCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQ_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("","onActivityResult");
        if(resultCode==RESULT_OK){
            Log.d("","resultCode==RESULT_OK");
            if(requestCode==REQ_1){
                Log.d("","requestCode==REQ_1");
                Bundle bundle =  data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                mImageView.setImageBitmap(bitmap);
                //emotionCapture ec = new emotionCapture(bitmap);
                //ec.recognize();
                new httpThread(url,bitmap,handler,textView).start();
            }
        }
    }
}

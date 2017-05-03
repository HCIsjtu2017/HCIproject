package com.ca.dell.ecapturer;

import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dell on 2017/4/7.
 */
public class httpThread extends Thread {

    private String url;
    private Handler handler;
    private TextView textView;
    private byte[] body;
    private String contxt;

    public httpThread(String url, Bitmap bitmap, Handler handler, TextView textView){
        this.url = url;
        this.handler = handler;
        this.textView = textView;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try
        {
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.body = out.toByteArray();
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();

        try {
            URL httpURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);

            conn.setRequestProperty("Content-Type","application/octet-stream");
            conn.setRequestProperty("Ocp-Apim-Subscription-Key","be31d77c5a144e82ac95901f6ebca02a");

            Log.d("httpThread","setRequestProperty Success");

            OutputStream out = conn.getOutputStream();
            out.write(body);

            Log.d("httpThread","getOutputStream Success");
            //Log.d("httpThread", String.valueOf(conn.getResponseCode()));
            if(conn.getResponseCode()==200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String str;
                while((str=reader.readLine())!=null){
                    sb.append(str);
                }

                contxt = sb.toString();
                //System.out.println(sb);
            }
            //conn.connect();

        } catch (MalformedURLException e) {

            Log.d("httpThread",e.toString());

            e.printStackTrace();
        } catch (IOException e) {

            Log.d("httpThread",e.toString());
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        final long costTime = endTime - startTime;

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("Cost Time: "+costTime+"ms\n"+contxt);

            }
        });

        System.out.println("Cost Time: "+costTime+"ms");
    }

}

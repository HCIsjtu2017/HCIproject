package com.ca.dell.ecapturer;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.net.URI;
/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
*/

/**
 * Created by dell on 2017/4/6.
 */
public class emotionCapture {
    Bitmap bitmap;
    byte[] body;
    static String url = "https://api.cognitive.azure.cn/emotion/v1.0/recognize";
    static String key = "be31d77c5a144e82ac95901f6ebca02a";


    public emotionCapture(Bitmap bitmap) {
        this.bitmap = bitmap;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try{
            out.flush();
            out.close();
        }
        catch(Exception e){

            System.out.println(e.getMessage());
        }
        this.body = out.toByteArray();
    }
/*
    public void recognize(){
        HttpClient httpClient = new DefaultHttpClient();
        try{
            URI uri = new URI(url);
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", key);
            Log.d("tst","recognize 1 success");
            // Request body
            StringEntity reqEntity = new StringEntity(body.toString());
            request.setEntity(reqEntity);
            Log.d("tst","recognize 2 success");
            HttpResponse response = httpClient.execute(request);
            Log.d("tst","recognize 2.5 success");
            HttpEntity entity = response.getEntity();
            Log.d("tst","recognize 3 success");
            if(entity!=null){
                System.out.println(EntityUtils.toString(entity));
            }
            Log.d("tst","recognize 4 success");
            //HttpPost request = new HttpPost(url);
        }
        catch(Exception e){
            Log.d("tst","recognize 5 success");
            Log.d("APIerror",e.toString());
            System.out.println(e.toString());
        }
    }*/
}

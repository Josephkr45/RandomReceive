package com.iot.bcrec.randomreceive;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import java.util.Random;

public class MyService extends Service {
    // global variables and TAG
    public static final String TAG ="Random";
    private boolean cal = false;
    Thread thread;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Log "onCreate" displayed and cal is set to true
        Log.i(TAG,"onCreate service");
        cal = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStart service");
        Bundle Message = intent.getExtras();
        final String dataMessage = Message.getString("Code");
        //Data = Integer.parseInt(dataMessage);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent i1 = new Intent("com.iot.bcrec.randomreceive");
                while(cal) {
                    synchronized (this){
                        try{
                            Random randd = new Random();
                            int value = randd.nextInt(100)+1;
                            wait(5000);
                            /**if(dataMessage.equals(String.valueOf(value))){
                                value = randd.nextInt(100)+1;
                            }*/
                            //Broadcast send with value
                            i1.putExtra("values",String.valueOf(value));
                            Log.i(TAG,value+"");
                            sendBroadcast(i1);

                        }catch (InterruptedException e){
                            return;
                        }
                    }
                }

            }
        };
        thread = new Thread(r);
        thread.start();

        return START_STICKY;
    }
//on destroy the cal= false to stop the loop and the service is stopped
    @Override
    public void onDestroy() {
        cal = false;
        super.onDestroy();
           }
}

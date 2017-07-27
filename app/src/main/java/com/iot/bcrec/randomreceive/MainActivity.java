package com.iot.bcrec.randomreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="Random";
    private int flag = 0;
    Button b1,b2;
    EditText e1;
    Intent i;

    //Broadcast reciever
    BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle Message1 = intent.getExtras();
            String s1 = Message1.getString("values");
            flag = Integer.parseInt(s1);
            Log.i(TAG,"String="+s1);
            e1.setText(String.valueOf(flag));
            //handler.sendEmptyMessage(0);
        }
    };

    /**Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            e1.setText(String.valueOf(flag));
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = new Intent(this,MyService.class);
        e1=(EditText) findViewById(R.id.editText);
        e1.setText(String.valueOf(flag));

        //b1=(Button) findViewById(R.id.startbtn);
        //b2=(Button) findViewById(R.id.stopbtn);

        //if (Integer.parseInt(e1.toString())!=flag){

    }

    //when START button is clicked
    public void onClick(View view){
        //Intent i = new Intent(this,MyService.class);
        i.putExtra("Data",String.valueOf(flag));
        Log.i(TAG,flag+"");
        startService(i);

    }

    //when stop button is clicked
    public void onEnd(View view){
        stopService(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("com.iot.bcrec.randomreceive");
        registerReceiver(mReciever,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReciever);
    }
}

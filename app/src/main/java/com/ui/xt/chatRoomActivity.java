package com.ui.xt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import bupt.jsip_demo.R;
import jsip_ua.SipProfile;
import jsip_ua.impl.DeviceImpl;

public class chatRoomActivity extends AppCompatActivity implements OnClickListener, Handler.Callback {

    private static final String TAG = "chatRoom";
    private SipProfile sipProfile;
    private String sip;
    private String remoteIP;
    private String remotePort;
    private String localUser;
    private String localPort;

    private String localSip;

    Button btnBack;
    Button btnFriendList;

    private Handler handler = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Log.d(TAG,"进来了！");

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnFriendList = (Button) findViewById(R.id.btnFriendList);
        btnFriendList.setOnClickListener(this);

        DeviceImpl.getInstance().setHandler(this.getHandler());
        sipProfile =DeviceImpl.getInstance().getSipProfile();

        Intent intent = getIntent();
        sip = intent.getStringExtra("sip");//远方的sip,应该是chatRoom的sip格式的地址
        remoteIP = intent.getStringExtra("remoteIP");
        remotePort = intent.getStringExtra("remotePort");
        localUser = intent.getStringExtra("localUser");
        localPort = intent.getStringExtra("localPort");
        localSip = "sip:" +sipProfile.getSipUserName() + "@" + sipProfile.getLocalEndpoint();//本地的sip格式的地址

        System.out.println("将要发送到的地址" + sip);

        String loggingMessage = localUser + "进入聊天室";
        DeviceImpl.getInstance().SendMessage(sip, loggingMessage);//把这个消息发给远方的服务器了!
        Log.d(TAG,sip);
        Log.d(TAG,"消息发送完成");

    }

    @Override//按钮点击还没有写
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnBack://会通知大家这个人已经离开了chatRoom
                break;
            case R.id.btnFriendList://会将好友列表刷新给这个人
                break;
        }
    }

    public boolean handleMessage(Message msg){//没写完
        return true;
    }

    public Handler getHandler(){
        return handler;
    }
}

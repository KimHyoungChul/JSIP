package com.ui.xt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import bupt.jsip_demo.R;
import jsip_ua.SipProfile;
import jsip_ua.impl.DeviceImpl;

public class chatRoomActivity extends AppCompatActivity implements OnClickListener, Handler.Callback {

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



    }

    @Override//还没写完最后一起写
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnBack:
                break;
            case R.id.btnFriendList:
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

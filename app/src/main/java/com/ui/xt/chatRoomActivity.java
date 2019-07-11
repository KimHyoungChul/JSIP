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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

    private Button btnBack;
    private Button btnFriendList;
    private Button btnSend;

    private EditText Name;
    private EditText Input;

    private ListView MessagePrint;
    private ArrayAdapter<String> adapter;

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
        btnSend = (Button) findViewById(R.id.btnSend);//发送消息按钮
        btnSend.setOnClickListener(this);

        Name = (EditText) findViewById(R.id.Name);
        Input = (EditText) findViewById(R.id.Input);

        MessagePrint = (ListView) findViewById(R.id.Message);//信息打印区

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

    @Override//按钮点击还没有写
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnBack://会通知大家这个人已经离开了chatRoom
                //to do
                //send byebye
                break;
            case R.id.btnFriendList://会将好友列表刷新给这个人
                Intent intent = new Intent(this, friendList.class);//跳转到好友列表

                intent.putExtra("sip", sip);//聊天室的地址(sip格式的包括用户名,IP,端口号等)
                intent.putExtra("remoteIP", remoteIP);
                intent.putExtra("remotePort", remotePort);
                intent.putExtra("localUser", localUser);
                intent.putExtra("localPort", localPort);

                startActivity(intent);

                break;
            case R.id.btnSend://发送消息的按钮
                if(Input.getText().toString() != null){
                    String receiver = Name.getText().toString();//接收方的名字
                    String message = Input.getText().toString();//要发送的信息
                    //to do
                    //sendMessage
                }
                break;
        }
    }

    public boolean handleMessage(Message msg){//没写完
        //to do
        //setAdapter
        return true;
    }

    public Handler getHandler(){
        return handler;
    }
}

package com.ui.xt;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<String> msgList;
    private ListView MessagePrint;
    private ArrayAdapter<String> adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //System.out.println("正在handle Message" + msgList.get(msgList.size() - 1));
            msgList.add((String)msg.obj);
            //msgList.add("handler能执行");
            adapter.notifyDataSetChanged();//用同一个view去更新界面，当有新的数据进来的时候
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {//给按钮加监听器
        //以及加上适配器，有处理消息更新的handler
        //最后把
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

        //消息列表的适配器
        MessagePrint = (ListView) findViewById(R.id.Message);
        msgList = new ArrayList<String>();//往这里面加东西然后打印出来
        //msgList.add("看看有没有消息");
        adapter = new MessageAdapter(this, msgList);
        MessagePrint.setAdapter(adapter);//给listview加上适配器

        //handler.handleMessage(new Message());//开始处理，刷新界面

        DeviceImpl.getInstance().setHandler(this.getHandler());
        sipProfile =DeviceImpl.getInstance().getSipProfile();
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
                intent.putExtra("friList", DeviceImpl.getInstance().getSipProfile().getFriendList());
                startActivity(intent);
                break;
            case R.id.btnSend://发送消息的按钮
                //todo
                //修改buildMessage(),应该是取到好友列表
                //最后无论是私聊还是群聊都是通过服务器转发
                Name = (EditText) findViewById(R.id.Name);
                Input = (EditText) findViewById(R.id.Input);
                if(!Input.getText().toString().equals("")){
                    System.out.println(Input.getText().toString());
                    String receiver = Name.getText().toString();//接收方的名字
                    String message = Input.getText().toString();//要发送的信息
                    DeviceImpl.getInstance().SendMessage(receiver, message);
                    Input.setText("");
                }
                break;
        }
    }

    public boolean handleMessage(Message msg){
        return true;
    }

    public Handler getHandler(){
        return handler;
    }

    protected class MessageAdapter extends ArrayAdapter<String> {
        public MessageAdapter(Context context,
                              List<String> items){
            super(context, android.R.layout.simple_list_item_1, items);
        }
    }
}

package com.ui.xt;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import bupt.jsip_demo.R;
import jsip_ua.SipProfile;

public class friendList extends AppCompatActivity implements OnClickListener {//这里有一个refresh按钮
    //中间是一个ListView

    private static final String TAG = "friendList";

    private SipProfile sipProfile;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnRefresh://这个人点击刷新好友列表
                //to do
                //refresh
                break;
        }
    }
}

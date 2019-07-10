package jsip_msg_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ui.xt.chatRoomActivity;

import bupt.jsip_demo.R;
import jsip_ua.SipProfile;
import jsip_ua.impl.DeviceImpl;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnClickListener,
		OnSharedPreferenceChangeListener {

	SharedPreferences prefs;
	EditText editName;
	EditText editPort;
	EditText chatRoomAddress;//聊天室的地址
	SipProfile sipProfile;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sipProfile = new SipProfile();//SipProfile一开始在这里
        HashMap<String, String> customHeaders = new HashMap<>();//这里用作数据字典
        customHeaders.put("customHeader1","customValue1");
        customHeaders.put("customHeader2","customValue2");

        DeviceImpl.getInstance().Initialize(getApplicationContext(), sipProfile,customHeaders);
        //相当于一个中间层

		Button btnEnter = (Button) findViewById(R.id.btnEnter);
		btnEnter.setOnClickListener(this);//这个按钮是点击的时候注册的

		editName = (EditText) findViewById(R.id.editName);//用户自己的用户名，在xml里面设定了一下
		//是Caroline
		editPort = (EditText) findViewById(R.id.editPort);//客户端用户自己的端口号
		//在xml里面设定了一下
		chatRoomAddress = (EditText) findViewById(R.id.chatRoomAddress);
		//聊天室的ip地址和端口号

		prefs = PreferenceManager.getDefaultSharedPreferences(this);//存数据的

		prefs.registerOnSharedPreferenceChangeListener(this);
		initializeSipFromPreferences();//用prefs来初始化sip

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {//OptionsMenu

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {//这个是在onCreateOptionsMenu调用之后
		//马上调用的
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {//这个是点击Main界面的enter会发生的事情
		switch (v.getId()) {
		case (R.id.btnEnter):
			String toChatRoom = chatRoomAddress.getText().toString();
			String remoteIP;
			String remotePort;
			if(toChatRoom.split(":").length>2){
				remotePort = toChatRoom.split(":")[2];//getPort
				remoteIP = toChatRoom.split(":")[1];
				remoteIP = remoteIP.substring(remoteIP.indexOf("@") + 1);

			}else{
				remoteIP = "192.168.43.196";
				remotePort = "5060";
			}

			//操纵对应的xml文件
			//用SharedPreference来保存key-value键值对
			//下面这一行是让editor处于可编辑状态
			SharedPreferences.Editor editor = prefs.edit();
			//以下用putString存放键值对
			editor.putString("remoteIP", remoteIP);
			editor.putString("remotePort", remotePort);
			editor.putString("localUser", editName.getText().toString());
			editor.putString("localPort", editPort.getText().toString());

			Intent intent = new Intent(this, chatRoomActivity.class);//这里是在构造一个指定目标组件的intent,我觉得应该是点击之后跳转到这个界面里面来
			//把这个mainActivity界面里面得数据传过去
			//存到extra里面去
			intent.putExtra("sip", toChatRoom);//聊天室的地址(sip格式的包括用户名,IP,端口号等)
			intent.putExtra("remoteIP", remoteIP);
			intent.putExtra("remotePort", remotePort);
			intent.putExtra("localUser", editName.getText().toString());
			intent.putExtra("localPort", editPort.getText().toString());

			startActivity(intent);

			break;
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("remoteIP")) {
			sipProfile.setRemoteIp((prefs.getString("remoteIP", "")));
		} else if (key.equals("remotePort")) {
			sipProfile.setRemotePort(Integer.parseInt(prefs.getString(
					"remotePort", "5060")));
		}  else if (key.equals("localUser")) {
			sipProfile.setSipUserName(prefs.getString("localUser",
					"Caroline"));
		}
	}

	@SuppressWarnings("static-access")
	private void initializeSipFromPreferences() {
		sipProfile.setRemoteIp((prefs.getString("remoteIP", "")));
		sipProfile.setRemotePort(Integer.parseInt(prefs.getString(
				"remotePort", "5060")));
		sipProfile.setSipUserName(prefs.getString("localUser", "Caroline"));
	}

}

package com.example.trantrunghieu.chatchit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trantrunghieu.chatchit.adapter.AdapterFriend;
import com.example.trantrunghieu.chatchit.models.Friend;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class Home  extends AppCompatActivity{
    AdapterFriend adapter;
    ArrayList<Friend> listData;
    ListView lvData;



    private void initView() {
        listData = new ArrayList<Friend>();

        lvData = (ListView) findViewById(R.id.LvFriend);
        adapter = new AdapterFriend(
                getApplicationContext(),
                R.layout.itemfriend,listData
        );
        lvData.setAdapter(adapter);

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent guidulieu = new Intent(Home.this,Chat.class);
                guidulieu.putExtra("username",username);
                guidulieu.putExtra("friend",listData.get(i).getUsername());

                startActivity(guidulieu);

            }
        });
    }

    private  String username;
    private Socket mSocket;
    {

        try {
            mSocket = IO.socket("http://192.168.1.11:3000");
        } catch (URISyntaxException e) {}
    }
    private Emitter.Listener onTakeListFriend = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray danhsach;
                    listData.clear();
                    try {

                        danhsach = data.getJSONArray("danhsach");
                        int len = danhsach.length();
                        for(int i=0;i<len;i++){
                            JSONObject persion = danhsach.getJSONObject(i);
                            Friend friend = new Friend(persion.getString("Username").toString(),
                                    persion.getString("LastName").toString().concat(" ").concat(persion.getString("FirstName").toString()),
                                    persion.getString("Image").toString());
                            listData.add(friend);

                            adapter.notifyDataSetChanged();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_home);
        Intent intent = this.getIntent();
        username= intent.getStringExtra("username");
        initView();
        if(!mSocket.connected()) mSocket.connect();
        String Info = "{\"username\":\""+username+"\"}";
        mSocket.emit("client_xin_thong_tin_friendlist",Info);
        mSocket.on("server_return_friendlist",onTakeListFriend);



    }
}

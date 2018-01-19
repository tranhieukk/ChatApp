package com.uiresource.cookit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.uiresource.cookit.Estring.CreateJson;
import com.uiresource.cookit.Estring.Encrypt;
import com.uiresource.cookit.modal.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class Chat extends AppCompatActivity {

    String username;
    String friend;
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Handler handler= null;
    Boolean running;
    int lastID=0;
    private Socket mSocket;
    {

        try {
            mSocket = IO.socket("http://192.168.1.14:3000");
        } catch (URISyntaxException e) {}
    }
    void initView(){
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        Intent intent = this.getIntent();
        username= intent.getStringExtra("username");
        friend= intent.getStringExtra("friend");
        if(!mSocket.connected()) mSocket.connect();
        String Info = CreateJson.getInstance().InfoChat11(username,friend);
        mSocket.emit("client_xin_thong_tin_tin_nhan_voi_friend",Info);

        //---

        handler = new Handler();
        runTime();


        ///--

        messageArea.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER){
                    String messageText = messageArea.getText().toString();

                    if(!messageText.equals("") && !messageText.equals("\n") ){
                        try {
                            messageText = Encrypt.getInstance().TexttoBase64(messageText);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String jsMessage = CreateJson.getInstance().Message(username,friend,messageText);

                        mSocket.emit("client_gui_tin_nhan_cho_friend",jsMessage);

                    }
                    messageArea.getText().clear();
                }

                return false;
            }
        });


        //-

        mSocket.on("server_return_listmesage",onTakeListMessage);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    try {
                        messageText = Encrypt.getInstance().TexttoBase64(messageText);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String jsMessage = CreateJson.getInstance().Message(username,friend,messageText);
                    mSocket.emit("client_gui_tin_nhan_cho_friend",jsMessage);

                }
                messageArea.getText().clear();
            }
        });

    }

    private void runTime() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(running) {
                    mSocket.emit("client_xin_thong_tin_tin_nhanmoi_voi_friend", CreateJson.getInstance().InfoChat11(username, friend,lastID));
                }
                handler.postDelayed(this,1000);
            }
        });
    }
    @Override
    protected void onPause(){
        super.onPause();
        running = false;

    }
    @Override
    protected void onResume(){
        super.onResume();
        running = true;

    }
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        running= false;
        handler.removeCallbacksAndMessages(null);
    }
    public void addMessageBox(Message message){
        TextView textView = new TextView(Chat.this);
        textView.setText(message.getContent());

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(message.getIn()) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        else{

            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }

        textView.setLayoutParams(lp2);
        layout.addView(textView);

        scrollView.fullScroll(View.FOCUS_DOWN);
    }
    private Emitter.Listener onTakeListMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray danhsach;

                    try {
                        danhsach = data.getJSONArray("listmesage");
                        int len = danhsach.length();
                        for(int i=0;i<len;i++){
                            JSONObject listmsg = danhsach.getJSONObject(i);
                            Boolean come = true;
                            String friend="";
                            if(listmsg.getString("from").equals(username)) come = false;
                            if(come){friend = listmsg.getString("from");
                                int ID =Integer.parseInt(listmsg.getString("ID"));
                                mSocket.emit("client_gui_da_nhan_tin_nhan_tu_friend",CreateJson.getInstance().InfoChat11("user","friend",ID));
                            }else {friend = listmsg.getString("to");}
                            String content = Encrypt.getInstance().Base64toText(listmsg.getString("content"));
                            Message msg = new Message(Integer.parseInt(listmsg.getString("ID")),content ,come,friend);
                            if(msg.getID()>lastID){
                                addMessageBox(msg);
                                lastID=msg.getID();
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

}

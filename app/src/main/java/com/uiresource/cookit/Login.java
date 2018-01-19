package com.uiresource.cookit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.uiresource.cookit.Estring.CreateJson;
import com.uiresource.cookit.Estring.Encrypt;
import com.uiresource.cookit.Estring.Formater;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Login  extends AppCompatActivity {
    TextView registerUser;
    EditText username, password;
    Button loginButton;
    ProgressDialog pd;
    String user, pass;
    String url = "http://192.168.1.14:3000";

    private static Socket mSocket;
    {
        try {
            mSocket = IO.socket(url);
        } catch (URISyntaxException e) {}
    }
    private Emitter.Listener onNewMessageKQ = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noiDung;
                    pd.dismiss();
                    try {
                        noiDung = data.getString("noidung");
                        if(noiDung == "true"){
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            Intent guidulieu = new Intent(Login.this,Main.class);
                            guidulieu.putExtra("username",user);
                            startActivity(guidulieu);
                        }else
                        {
                            Toast.makeText(Login.this, "Sai mật khẩu hoặc tài khoản", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        return;
                    }


                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(!mSocket.connected()) mSocket.connect();
        registerUser = (TextView)findViewById(R.id.register);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);





        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else{
                    String url = "http://192.168.1.9:3000";
                    pd = new ProgressDialog(Login.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String username = Formater.getInstance().chuanHoaUsername(user );
                    String password = Encrypt.getInstance().md5(pass);


                    mSocket.emit("client-gui-username", CreateJson.getInstance().Information(username,password));
                    mSocket.on("ketquaDN", onNewMessageKQ);


                }

            }
        });
    }
}

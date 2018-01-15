package com.example.trantrunghieu.chatchit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trantrunghieu.chatchit.Estring.CreateJson;
import com.example.trantrunghieu.chatchit.Estring.Encrypt;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Register extends AppCompatActivity {
    EditText username, password;
    Button registerButton;
    String user, pass;
    TextView login;
    ProgressDialog pd ;
    private Socket mSocket;
    {

        try {
            mSocket = IO.socket("http://192.168.1.11:3000");
        } catch (URISyntaxException e) {}
    }
    private Emitter.Listener KetQuaDangKy = new Emitter.Listener() {
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
                            Toast.makeText(Register.this, "Đăng kýthành công", Toast.LENGTH_SHORT).show();

                            Intent guidulieu = new Intent(Register.this,Home.class);
                            guidulieu.putExtra("username",user);
                            startActivity(guidulieu);
                        }else
                        {
                            Toast.makeText(Register.this, "Tài khoản đã được sử dụng. \n Vui lòng chọn tài khoản khác.", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_register);
        if(!mSocket.connected()) mSocket.connect();
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        registerButton = (Button)findViewById(R.id.registerButton);
        login = (TextView)findViewById(R.id.login);
        mSocket.on("ketquaDK",KetQuaDangKy);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("Không được để trống.");
                }
                else if(pass.equals("")){
                    password.setError("Không được để trống.");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("Chỉ nhập chữ và số.");
                }
                else if(user.length()<3){
                    username.setError("Tài khoản phải lớn hơn 3 ký tự");
                }
                else if(pass.length()<5){
                    password.setError("Mật khẩu phải lớn hơn 5 ký tự");
                }
                else {
                    pd = new ProgressDialog(Register.this);

                    String Info = CreateJson.getInstance().Information(user, Encrypt.getInstance().md5(pass));

                    mSocket.emit("client_Dang_ky_thanhvien",Info);

                    pd.setMessage("Đang thực hiện...");
                    pd.show();

                }


            }
        });
    }
}
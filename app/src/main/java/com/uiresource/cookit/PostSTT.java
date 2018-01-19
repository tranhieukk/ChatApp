package com.uiresource.cookit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.uiresource.cookit.Estring.CreateJson;
import com.uiresource.cookit.Estring.Encrypt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class PostSTT extends AppCompatActivity {
    private  final  int REQUET_TAKE_PHOTO =123;
    private  final  int REQUET_CHOOSE_PHOTO =321;
    private static Bitmap imageChoose =null;
    private  static String userName="";
    ProgressDialog pd;


    boolean haveIMG = false;
    ImageButton ibGui ;
    ImageButton ibChup;
    ImageButton ibChon ;
    ImageView imgShow;
    EditText edtPost;
    private Socket mSocket;
    {

        try {
            mSocket = IO.socket("http://192.168.1.14:3000");
        } catch (URISyntaxException e) {}
    }

    private Emitter.Listener GuiHinh = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    senImage();
                }
            });
        }
    };

    private Emitter.Listener KetQua = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noiDung;
                    try {
                        noiDung = data.getString("ketqua");
                        if(noiDung=="1")noiDung ="Đăng thành công"; else noiDung = "Không thành công";
                        Toast.makeText(PostSTT.this,noiDung,Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    clear();
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_stt);
        getUserInfo();
        addControls();
        initSocket();
        addEvents();
        Toast.makeText(PostSTT.this,userName,Toast.LENGTH_LONG).show();
    }

    private void getUserInfo() {
        Intent intent = this.getIntent();
        userName= intent.getStringExtra("username");
    }
    private void clear(){
        edtPost.setText("");
        imgShow.setImageResource(0);
        imageChoose=null;
        haveIMG=false;
        pd.dismiss();
    }

    private void initSocket() {

        if(!mSocket.connected())mSocket.connect();
        mSocket.on("Server_lay_img",GuiHinh);
        mSocket.on("Server_bao_ketQua",KetQua);

    }

    private void addControls() {
         ibGui = (ImageButton) findViewById(R.id.ibpost);
         ibChup = (ImageButton) findViewById(R.id.ibaddimg);
         ibChon = (ImageButton) findViewById(R.id.ibchup);
        imgShow =(ImageView) findViewById(R.id.imgShow);
        edtPost = (EditText) findViewById(R.id.edtPost);
    }
    private void addEvents() {
        ibGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(edtPost.getText().length()>0 || haveIMG){
                senPost();}else {

                   Toast.makeText(PostSTT.this,"Hãy viết gì đó!",Toast.LENGTH_LONG).show();
               }
            }
        });

        ibChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        ibChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosepicture();

            }
        });
    }
    private  void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUET_TAKE_PHOTO);
    }
    private  void choosepicture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUET_CHOOSE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUET_CHOOSE_PHOTO && resultCode == RESULT_OK){
            try {Uri imageUri = null;
                imageUri=data.getData();
                InputStream is = getContentResolver().openInputStream(imageUri);
               Bitmap bm = BitmapFactory.decodeStream(is);
                imgShow.setImageBitmap(bm);
                imageChoose = bm;
                haveIMG=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else if(requestCode == REQUET_TAKE_PHOTO && resultCode==RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgShow.setImageBitmap(bitmap);
            imageChoose = bitmap;
            haveIMG=true;
        }
    }

    private void senPost(){
        pd = new ProgressDialog(PostSTT.this);
        pd.setMessage("Đang tải lên...");
        pd.show();
        try {

            String i="0";
            if(haveIMG) i="1";
            String Post = Encrypt.getInstance().TexttoBase64(edtPost.getText().toString());
            mSocket.emit("Client_gui_stt",CreateJson.getInstance().Post(userName,Post,i));


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void senImage() {
        if(imageChoose!=null) {
            byte[] bytearr = GetArrayBytefromBitmap(imageChoose);
            mSocket.emit("Client_gui_hinh", bytearr);
        }
    }
     public  byte [] GetArrayBytefromBitmap(Bitmap bmp){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
         byte []bytearray =stream.toByteArray();
         return  bytearray;
    }
}

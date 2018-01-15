package com.example.trantrunghieu.chatchit.Estring;

/**
 * Created by Tran Trung Hieu on 1/14/18.
 */

public class CreateJson {

    public CreateJson() {
    }
    private  static  CreateJson instance;
    private static void setInstance(CreateJson instance) {
        CreateJson.instance = instance;
    }
    public static  CreateJson getInstance(){
        if(instance == null)
            instance = new CreateJson();
        return  instance;
    }
    private String repl(String s){
        return s.replace("\"","\\\"");
    }
    public String Information(String a, String b){
        a=repl(a);
        b=repl(b);
       return "{\"username\":\""+a+"\",\"password\":\""+b+"\"}";
    }
    public String InfoChat11(String a, String b){
        a=repl(a);
        b=repl(b);
        return "{\"username\":\""+a+"\",\"friend\":\""+b+"\"}";
    }
    public String Message(String username, String friend,String content){
        content=repl(content);
        return "{\"username\":\""+username+"\",\"friend\":\""+friend+"\",\"content\":\"" + content + "\"}";
    }
}

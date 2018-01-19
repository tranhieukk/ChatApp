package com.uiresource.cookit.Estring;


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
    public String Information(String username, String password){
        username=repl(username);
        password=repl(password);
       return "{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
    }
    public String InfoChat11(String username, String friend){
        username=repl(username);
        friend=repl(friend);
        return "{\"username\":\""+username+"\",\"friend\":\""+friend+"\"}";
    }
    public String InfoChat11(String username, String friend,int id){
        username=repl(username);
        friend=repl(friend);
        return "{\"username\":\""+username+"\",\"friend\":\""+friend+"\",\"ID\":\"" +id + "\"}";
    }
    public String Message(String username, String friend,String content){

        return "{\"username\":\""+username+"\",\"friend\":\""+friend+"\",\"content\":\"" + content + "\"}";
    }
    public  String Post(String username,String Post,String haveIMG){
        return  "{\"username\":\"" + username + "\",\"post\":\""+Post+"\",\"pic\":\""+haveIMG+"\"}";
    }
}

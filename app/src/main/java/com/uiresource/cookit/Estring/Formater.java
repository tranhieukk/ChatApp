package com.uiresource.cookit.Estring;

/**
 * Created by Tran Trung Hieu on 1/14/18.
 */

public class Formater {
    public Formater() {
    }
    private  static  Formater instance;
    private static void setInstance(Formater instance) {
        Formater.instance = instance;
    }
    public static  Formater getInstance(){
        if(instance == null)
            instance = new Formater();
        return  instance;
    }
    public String chuanHoa(String str) {
        str = str.trim();
        str=str.replace("\n", "");
        str = str.replaceAll("\\s+", " ");
        str = str.replace("'","");
        str = str.replace("1","");
        str = str.replace(" delete ","");
        str = str.replace("*","");
        str = str.replace(" drop ","");
        str = str.replace(" and ","");
        str = str.replace(" or ","");
        str = str.replace("\"","");
        return str;
    }
    public String chuanHoaUsername(String str) {
        str = str.trim();
        str = str.replace("'","");
        str = str.replace("1","");
        str = str.replace(" delete ","");
        str = str.replace("*","");
        str = str.replace(" drop ","");
        str = str.replace(" and ","");
        str = str.replace(" or ","");
        str = str.replace("\"","");
        str = str.replaceAll("\\s+", " ");
        return str;
    }

    public String chuanHoaDanhTuRieng(String str) {
        str = chuanHoa(str);
        String temp[] = str.split(" ");
        str = ""; // ? ^-^
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) // ? ^-^
                str += " ";
        }
        return str;
    }
}

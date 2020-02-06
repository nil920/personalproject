package controller;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class ReceiveInfo {
    public  String notice = "";
    public  String card = "";
    public  String reply ="";
    public  int needed;

    @SerializedName("game-id")
    public  int gameid;

    public String drew = "";
    public  String token;
    public LinkedList<LinkedList<String>> hands;
    public int timeout;
    public String rainbow;
    public int position;
    public Boolean replaced = null;


    //info
    public int rank ;
    public int player ;
    public String suit = "";
    public LinkedList<Boolean> info;
}

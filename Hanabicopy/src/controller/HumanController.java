package controller;

import controller.gson.CreateMessage;
import controller.gson.JoinMessage;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Waitingroom;
import view.WaitingRoom;

import java.io.IOException;

public class HumanController {

    private static Waitingroom waitingroomModel;
    private static WaitingRoom waitingroomView;
    ConnectToServer connectToServer;
    public HumanController() {
    }

    public static void create(String nsid, int players, int timeout, String rainbow) {
//        create json format to send to server
        CreateMessage create = new CreateMessage("create", nsid, players, rainbow, timeout,true);
        Gson createGson = new Gson();
        String json = createGson.toJson(create);
        try {
//            send data to server
            ConnectToServer.sendMessageToServer(json);
        }
        catch (IOException e){
        }
    }

    public static void join(String nsid, int gameID, String secretToken) {
//        create json format to send to server
        JoinMessage join = new JoinMessage("join", nsid, gameID, secretToken);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES);

        Gson joinGson = new Gson();
        String json = joinGson.toJson(join);
        try {
//            send data to server
            ConnectToServer.sendMessageToServer(json);
        }
        catch (IOException e){

        }
    }



}


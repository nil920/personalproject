package controller;

import controller.gson.GivenInfoSuit;
import controller.gson.PlayDiscardMessage;
import startup.GUIHanabiSystem;
import com.google.gson.Gson;

import java.io.IOException;

public class MovesController
{
    public MovesController()
    { }

    public static void play(int cardIndex)
    {
        GUIHanabiSystem.gameModel.writeToCSV("play",cardIndex,"",0);
        controller.gson.PlayDiscardMessage playCard = new PlayDiscardMessage("play",cardIndex);
        Gson playGson = new Gson();
        String msg = playGson.toJson(playCard);
        GUIHanabiSystem.cancelTimer =true;
        try {
//            send data to server
            ConnectToServer.sendMessageToServer(msg);
        }
        catch (IOException e){
            System.out.println("error, help");
        }
    }

    public static void discard(int cardIndex)
    {
        GUIHanabiSystem.gameModel.writeToCSV("discard",cardIndex,"",0);
        PlayDiscardMessage discardCard = new PlayDiscardMessage("discard",cardIndex);
        Gson discardGson = new Gson();
        String msg = discardGson.toJson(discardCard);
        GUIHanabiSystem.cancelTimer =true;
        try {
//            send data to server
            ConnectToServer.sendMessageToServer(msg);
        }
        catch (IOException e){
            System.out.println("error, help");
        }
    }

    public static void giveClueSuit(int indexOfPlayer, String suit)
    {
        GUIHanabiSystem.gameModel.writeToCSV("info",0,suit,0);
        controller.gson.GivenInfoSuit givenInfoSuit = new GivenInfoSuit("inform",indexOfPlayer,suit);
        Gson clueSuitGson = new Gson();
        String msg = clueSuitGson.toJson(givenInfoSuit);
        GUIHanabiSystem.cancelTimer =true;
        try {
//            send data to server
            ConnectToServer.sendMessageToServer(msg);
        }
        catch (IOException e){
            System.out.println("error, help");
        }
    }

    public static void giveClueRank(int indexOfPlayer, int rank)
    {
        GUIHanabiSystem.gameModel.writeToCSV("info",0,"",rank);
        controller.gson.GivenInfoInt givenInfoSuit = new controller.gson.GivenInfoInt("inform",indexOfPlayer,rank);
        Gson clueRankGson = new Gson();
        String msg = clueRankGson.toJson(givenInfoSuit);
        GUIHanabiSystem.cancelTimer =true;
        try {
            ConnectToServer.sendMessageToServer(msg);
        }
        catch (IOException e){
            System.out.println("error, help");
        }
    }

}

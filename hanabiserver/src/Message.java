import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Message {
    public static JsonObject invalid(){
        JsonObject jo=new JsonObject();
        jo.addProperty("reply","invalid");
        return jo;
    }

    public static JsonObject repley(String message){
        JsonObject jo = new JsonObject();
        jo.addProperty("reply",message);
        return jo;
    }


    public static JsonObject create(int gameid, String token,boolean exist){
        JsonObject jo = new JsonObject();
        if (exist){
            jo.addProperty("reply","extant");
        }
        else {
            jo.addProperty("reply","created");
        }

        jo.addProperty("game-id",gameid);
        jo.addProperty("token",token);

        return jo;
    }


    public static JsonObject join(int needed, int timeout,String rainbow){
        JsonObject jo = new JsonObject();

        jo.addProperty("reply","joined");
        jo.addProperty("needed",needed);
        jo.addProperty("timeout",timeout);
        jo.addProperty("rainbow",rainbow);

        return jo;
    }

    public static JsonObject playerjoined(int needed){
        JsonObject jo = new JsonObject();
        jo.addProperty("notice","player joined");
        jo.addProperty("needed",needed);
        return jo;
    }

    public static JsonObject gamestart(ArrayList<ArrayList> hands){
        JsonObject jo = new JsonObject();
        jo.addProperty("notice","game starts");
        JsonArray jsonArray = new Gson().toJsonTree(hands).getAsJsonArray();
        jo.add("hands",jsonArray);
        return jo;
    }

    public static JsonObject yourturn(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("reply","your move");
        return jsonObject;
    }

    public static JsonObject played(int position, String card){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("notice","played");
        jsonObject.addProperty("position",position);
        jsonObject.addProperty("card",card);
        return jsonObject;
    }

    public static JsonObject builtorburn(boolean build,String card,Boolean replace, int position){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("reply",build ? "built": "burned");
        jsonObject.addProperty("card",card);
        jsonObject.addProperty("replaced",replace);
        jsonObject.addProperty("position",position);
        return jsonObject;
    }

    public static JsonObject accepted( Boolean replaced,String card){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("reply","accepted");
        jsonObject.addProperty("replaced",replaced);
        jsonObject.addProperty("card",card);
        return jsonObject;
    }


    public static JsonObject discarded(int position, String drew){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("reply","discarded");
        jsonObject.addProperty("position",position);
        jsonObject.addProperty("card",drew);
        return jsonObject;
    }

    public static JsonObject informother(int player,Boolean isrank, int rank, String suit){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("notice","inform");
        jsonObject.addProperty("player",player);
        if (isrank){
            jsonObject.addProperty("rank",rank);
        }
        else {
            jsonObject.addProperty("suit",suit);
        }
        return jsonObject;
    }

    public static JsonObject inforplayer(Boolean isrank, ArrayList<Boolean> cards,int rank, String suit){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("reply","inform");
        if (isrank){
            jsonObject.addProperty("rank",rank);
        }
        else {
            jsonObject.addProperty("suit",suit);
        }
        JsonArray jsonArray = new Gson().toJsonTree(cards).getAsJsonArray();
        jsonObject.add("info",jsonArray);
        return jsonObject;
    }



}

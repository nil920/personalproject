import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;


public class Server {
    // all the games
    public static HashMap <String,Game> games = new HashMap<>();

    // add game to hashmap
    public static synchronized void addgame(String nsid,Game game){
        games.put(nsid,game);
    }

    // find game by nsid
    public static Game findgamebyid(String nsid){
        return games.get(nsid);
    }

    // if the searched game in the hashmap/
    public static boolean containgame(String nsid){
        return games.containsKey(nsid);
    }

    //get the id of game
    public static int getgameid(String nsid) throws RuntimeException{
        Object[] keyset = games.keySet().toArray();
        for (int i =0;i< keyset.length;i++){
            if (keyset[i] == nsid){
                return i+1;
            }
        }
        return Integer.MAX_VALUE;
    }

    //check if the given gameid is invalid.
    public static boolean validgameid(int gameid){
        return gameid <= games.size() && gameid >0;
    }

    // find game by index
    public static Game findbyindex(int gameid){
        Object[] keyset = games.keySet().toArray();
        String key = (String) keyset[gameid-1];
        return games.get(key);
    }

    // find next empty game id
    public static int newgameid(){
        return games.size()+1;
    }


    public static void main(String[] args) throws IOException{

        // server
        ServerSocket s;
        s = new ServerSocket(Config.port);

        while(true) {
            // so server will contine for any fail
            while(true) {
                try {
                    Socket c = s.accept();
                    Service svc = new Service(c);
                    // mult thread server.
                    Thread t = new Thread(svc);
                    System.out.println("--- servicing " + c);
                    t.start();
                } catch (IOException e) {
                    System.err.println("!!! server failed:" + e);
                }
            }
        }
    }

}


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;

public class Service extends Thread{

    // current player
    private Socket sock;
    private Game game;
    public static String state = "wait";



    Service(Socket s) {
        this.sock = s;
    }

    public void run() {
        // get input message
        try {
            Reader in = new InputStreamReader(this.sock.getInputStream());
            char[] cb = new char[1024];
            int rc = in.read(cb, 0, 1024);
            String msg = String.copyValueOf(cb, 0, rc);
            System.out.println(msg);
            JsonObject jsonObject = new JsonParser().parse(msg).getAsJsonObject();

            // if is a cmd message.
            // then it will be create or join.
            if (state.equals("wait")) {
                String cmd = jsonObject.get("cmd").getAsString();
                // create.
                // if game not created, then we create a new game.
                // else we back to the created game
                if (cmd.equals("create")) {
                    if (!Server.containgame(jsonObject.get("nsid").getAsString())) {
                        int players = jsonObject.get("players").getAsInt();
                        int timeout = jsonObject.get("timeout").getAsInt();
                        String rainbow = jsonObject.get("rainbow").getAsString();

                        String token = Token.generatetoken();
                        game = new Game(Server.newgameid(),players, timeout, rainbow, sock, token);

                        Server.addgame(jsonObject.get("nsid").getAsString(), game);


                        JsonObject created = Message.create(game.getGameid(), token, false);
                        writeback(created);

                        sleep(500);

                        JsonObject joined = Message.join(game.getPlayers(),game.getTimeout(),game.getRainbow());
                        writeback(joined);
                    }
                    else {
                        game =Server.findgamebyid(jsonObject.get("nsid").getAsString());
                        JsonObject created = Message.create(game.getGameid(),game.getToken(), true);
                        writeback(created);

                        JsonObject joined = Message.join(game.getPlayers()-1,game.getTimeout(),game.getRainbow());
                        writeback(joined);
                    }
                }

                // join. need a game id and a token
                if (cmd.equals("join")) {
                    int gameid = jsonObject.get("game-id").getAsInt();
                    if (Server.validgameid(gameid)
                            && jsonObject.get("token").getAsString().equals(Server.findbyindex(gameid).getToken())
                    ) {
                        game = Server.findbyindex(jsonObject.get("game-id").getAsInt());
                        if (game.getPlayers() == 0){
                            JsonObject full = Message.repley("game full");
                            writeback(full);
                        }
                        else {
                            JsonObject joined = Message.join(game.getPlayers(),game.getTimeout(),game.getRainbow());
                            writeback(joined);
                            game.addplayer(sock);
                        }
                    }
                    else {
                        JsonObject invalid = Message.invalid();
                        writeback(invalid);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writeback(JsonObject jsonObject) throws IOException{
        PrintWriter printWriter = new PrintWriter(this.sock.getOutputStream(),true);
        printWriter.println(jsonObject.toString());
    }

    protected synchronized static void changestate(String state1){
        state = state1;
    }
}

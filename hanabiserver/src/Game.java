import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Game {
    // initnal card without fireworks
    private static String[] basiccards = new String[]{"r1","r2","r3","r4","r5",
            "g1","g2","g3","g4","g5",
            "b1","b2","b3","b4","b5",
            "y1","y2","y3","y4","y5",
            "w1","w2","w3","w4","w5"
    };

    //fireworkcards
    private static String[] rainbowcard = new String[]{"m1","m2","m3","m4","m5"};

    // number of players
    private static int players;
    // time
    private static int timeout;
    // init rainbow message
    private static String rainbow;
    // table
    private static ArrayList<Player> seats;
    // game token
    private static String token;
    //game id
    private static int gameid;
    // advance mode
    private static boolean wild = false;
    private static boolean rainbows = false;
    // currentplayer(turn)
    private static int currentplayer;

    // hand of each player
    private static ArrayList<ArrayList> hands;
    // remain cards
    private static ArrayList<String> cards;

    // build fireworks
    private static ArrayList<String> buildfirework;

    private static int lastturn;

    // if 3 token burnout, game end
    private static int blacktoken;

    private static boolean gameend;


    // shuffle seats. swith position around
    public static void shuffleseats(){
        Random r = new Random();
        for (int i = seats.size() -1;i>0;i--){
            int toshuffle = r.nextInt(i);
            Player temp = seats.get(i);
            seats.set(i,seats.get(toshuffle));
            seats.set(toshuffle,temp);
        }
        for (int i =0; i< seats.size();i++){
            seats.get(i).setSeat(i);
        }
    }

    // for reduece duplicate code
    private static void inithandshelper(boolean rainbows){
        for (String i : basiccards){
            if (Integer.parseInt(String.valueOf(i.charAt(1) ))== 1){
                for (int v =0;v<3;v++){
                    cards.add(i);
                }
            }
            if (Integer.parseInt(String.valueOf(i.charAt(1) ) )== 5){
                cards.add(i);
            }
            else {
                for (int v =0;v<2;v++){
                    cards.add(i);
                }
            }
        }
        if (rainbows){
            for (String i : rainbowcard){
                if (Integer.parseInt(String.valueOf(i.charAt(1) ) )== 1){
                    for (int v =0;v<3;v++){
                        cards.add(i);
                    }
                }
                if (Integer.parseInt(String.valueOf(i.charAt(1) ) )== 5){
                    cards.add(i);
                }
                else {
                    for (int v =0;v<2;v++){
                        cards.add(i);
                    }
                }
            }
        }
    }




    //initnal cards and deal hands
    private static void inithands(){
            inithandshelper(rainbows);
        // shuffle 6 times
        for (int i =0; i< 3; i++){
            shufflecards();
            Collections.shuffle(cards);
        }

        // deal init cards.
        dealinithand();
    }


    //give message
    private static ArrayList<ArrayList>  giveplayerinithand(int playerseat){
        ArrayList<ArrayList> result = new ArrayList<>();
        for (ArrayList i: hands){
            result.add(i);
        }
        result.set(playerseat,new ArrayList());
        return result;
    }

    // shuffle cards. same with collection.shuffle
    private static void shufflecards(){

        Random r = new Random();
        for (int i =cards.size()-1; i>0;i--){
            int j = r.nextInt(i);
            String temp = cards.get(i);
            cards.set(i,cards.get(j));
            cards.set(j,temp);
        }
    }

    // give cards to each player
    private synchronized static void dealinithand(){
        hands = new ArrayList<>();
        if (players < 4){
            for (int i = 0;i< players ; i++){
                ArrayList<String> hand = new ArrayList<>();
                for (int j = 0; j< 5;j++){
                    hand.add(cards.remove(0));
                }
                hands.add(hand);
            }
        }
        else {
            for (int i = 0;i< players ; i++){
                ArrayList<String> hand = new ArrayList<>();
                for (int j = 0; j< 4;j++){
                    hand.add(cards.remove(0));
                }
                hands.add(hand);
            }
        }
    }


    public int getGameid() {
        return gameid;
    }

    // constructer.
    public Game(int gameid, int players, int timeout, String rainbow, Socket player, String token) {
        this.gameid = gameid;
        this.players = players;
        this.timeout = timeout;
        this.rainbow = rainbow;
        this.token = token;
        if (!rainbow.equals("none")){
            rainbows = true;
        }
        if (rainbow == "wild"){
            wild = true;
        }

        cards = new ArrayList<>();
        seats = new ArrayList<>();
        Player player1 = new Player(player);
        seats.add(player1);

        buildfirework = new ArrayList<>();

        lastturn =0;
        blacktoken = 3;
        gameend = false;

    }


    // add player to the table
    public synchronized static void addplayer(Socket player) throws IOException {
        // send each person on the table a message
        for (Player i : seats){
            JsonObject jsonObject = Message.playerjoined(getPlayers() -1);
            PrintWriter printWriter = new PrintWriter(i.getSocket().getOutputStream(),true);
            printWriter.println(jsonObject.toString());
        }
        // create new player
        Player j = new Player(player);
        seats.add(j);
        if (getPlayers() == 0){
            shuffleseats();
            inithands();
            for (Player i : seats){
                JsonObject jsonObject = Message.gamestart(giveplayerinithand(i.getSeat()));
                PrintWriter printWriter = new PrintWriter(i.getSocket().getOutputStream(),true);
                printWriter.println(jsonObject.toString());
            }
            gamestart();
        }
    }

    // infor first player his turn
    protected static void inforplayer(int toinfo) throws IOException {
        JsonObject jsonObject = Message.yourturn();
        writetoclient(toinfo,jsonObject);
    }



    public static int getCurrentplayer() {
        return currentplayer;
    }

    public static void increaseCurrentplayer(){
        if (currentplayer == players-1){
            currentplayer = 0;
        }
        else {
            currentplayer++;
        }
    }


    // return true for card go to firework pile, false otherwise.
    protected static boolean buildordiscard(String card){
        Integer value = Integer.parseInt(String.valueOf(card.charAt(1)));
        Character color  =card.charAt(0);
        System.out.println(value);
        boolean containcolor = false;
        int counter = 0;
        for (String i: buildfirework){
            if (i.charAt(0) == color){
                containcolor = true;
            }
            // not go into here
            int card_rank = Character.getNumericValue(i.charAt(1));
            if (containcolor && card_rank +1 == value){
                System.out.println(Integer.parseInt(String.valueOf(i.charAt(1))));
                buildfirework.set(counter,card);
                return true;
            }
            counter ++;
            containcolor = false;
        }
        if (!containcolor && value == 1){
            buildfirework.add(card);
            return true;
        }
        return false;
    }


    private static boolean gameend(){
        if (lastturn >= players || gameend){
            return true;
        }
        return false;
    }


    private static void gamestart(){

        while (!gameend()) {
            try{
                Thread.sleep(50);
            }
            catch (InterruptedException e){
            }
            for (int i = 0; i < players; i++) {
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e){
                }

                try {
                    inforplayer(i);

                    Reader in = new InputStreamReader(seats.get(i).getSocket().getInputStream());
                    char[] cb = new char[1024];
                    int rc = in.read(cb, 0, 1024);
                    String msg = String.copyValueOf(cb, 0, rc);
                    JsonObject jsonObject = new JsonParser().parse(msg).getAsJsonObject();

                    try {
                        String action = jsonObject.get("action").getAsString();
                        System.out.println(action);
                        if (action.equals("play")) {
                            int position = jsonObject.get("position").getAsInt() - 1;
                            String card = (String) hands.get(i).get(position);
                            boolean buildordiscard = buildordiscard(card);
                            JsonObject jo = Message.builtorburn(buildordiscard, card, havereplace(), position + 1);
                            writetoclient(i, jo);
                            if (!buildordiscard) {
                                blacktoken--;
                            }

                            if (havereplace()) {
                                String newcard = cards.remove(0);
                                jo = Message.played(position + 1, newcard);
                                writetolientexcepti(i, jo);
                                hands.get(i).set(position, newcard);
                            }
                            if (!havereplace()) {
                                jo = Message.played(position + 1, "none");
                                writetolientexcepti(i, jo);
                            }

                            if (blacktoken == 0) {
                                jo = Message.repley("game ends");
                                writetolientexcepti(Integer.MAX_VALUE, jo);
                                gameend = true;
                            }
                        }

                        if (action.equals("discard")) {
                            int position = jsonObject.get("position").getAsInt() - 1;
                            String card = (String) hands.get(i).get(position);
                            JsonObject jo = Message.accepted(havereplace(), card);
                            writetoclient(i, jo);

                            if (havereplace()) {
                                String newcard = cards.remove(0);
                                jo = Message.discarded(position + 1, newcard);
                                writetolientexcepti(i, jo);
                                hands.get(i).set(position, newcard);
                            }
                            if (!havereplace()) {
                                jo = Message.discarded(position + 1, "none");
                                writetolientexcepti(i, jo);
                            }
                        }

                        if (action.equals("inform")) {
                            int player = jsonObject.get("player").getAsInt() - 1;
                            if (jsonObject.get("rank") == null) {
                                char suit = jsonObject.get("suit").getAsCharacter();
                                JsonObject jo = Message.inforplayer(false, stringhintcard(suit, player), 0, String.valueOf(suit));
                                writetoclient(player, jo);

                                jo = Message.informother(player + 1, false, 0, String.valueOf(suit));
                                writetolientexcepti(player, jo);
                            } else {
                                int rank = jsonObject.get("rank").getAsInt();
                                JsonObject jo = Message.inforplayer(true, inthintcard(rank, player), rank, "");
                                writetoclient(player, jo);

                                jo = Message.informother(player + 1, true, rank, "");
                                writetolientexcepti(player, jo);
                            }
                        }
                    } catch (NullPointerException e) {
                        System.out.println("waht???");
                    }


                } catch (IOException e) {
                    System.out.println("Exit");
                }
            }
        }
    }


    private static void writetoclient(int towriteback, JsonObject jsonObject) throws IOException{
        PrintWriter printWriter = new PrintWriter(seats.get(towriteback).getSocket().getOutputStream(),true);
        printWriter.println(jsonObject.toString());
    }


    private static void writetolientexcepti(int i, JsonObject jsonObject) throws IOException{
        for (int j = 0; j< players;j++){
            if (j != i){
                writetoclient(j,jsonObject);
            }
        }
    }

    private static ArrayList<Boolean> inthintcard(int rank, int player){
        ArrayList<Boolean> result = new ArrayList<>();
        for (int i =0; i< hands.get(player).size();i++){
            String card = (String) hands.get(player).get(i);
            if (Integer.parseInt(String.valueOf(card.charAt(1)))==rank){
                result.add(true);
            }
            else {
                result.add(false);
            }
        }
        return result;
    }

    private static ArrayList<Boolean> stringhintcard(char suit, int player){
        ArrayList<Boolean> result = new ArrayList<>();
        for (int i =0; i< hands.get(player).size();i++){
            String card = (String) hands.get(player).get(i);
            if (card.charAt(0)==suit){
                result.add(true);
            }
            else {
                result.add(false);
            }
        }
        return result;
    }


    private static boolean havereplace(){
        return cards.size() != 0;
    }


    public static int getPlayers() {
        return players - seats.size();
    }


    public static int getTimeout() {
        return timeout;
    }


    public static String getRainbow() {
        return rainbow;
    }


    public static ArrayList<Player> getSeats() {
        return seats;
    }


    public static String getToken() {
        return token;
    }
}

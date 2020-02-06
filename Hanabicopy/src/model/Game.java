package model;


import view.Subscriber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Game {
    //File
    private File file = new File("/output.csv");

    //Token
    private int inforTokenCountor;
    private int blackFuseCounter;

    //rainbow
    private boolean rainbow;
    private boolean wild;

    // Card count
    private int deckCount;


    // linkedlist
    private LinkedList <LinkedList<Card>> discardedcard;
    private LinkedList<Action> history;

    // point
    private int points;

    // timer
    private int timer;

    // Current
    private int currentPlayer;

    //hand
    private LinkedList<LinkedList<Card>> hands;

    // firework
    private LinkedList<LinkedList<Card>> firework;

    // ????
    private boolean lastTurn;


    // number of player
    private int playernumber;

    private boolean youTurn;

    private int you;

    private ArrayList<Subscriber> point_listener;
    private ArrayList<Subscriber> card_change_listener;
    private ArrayList<Subscriber> fuse_token_listener;
    private ArrayList<Subscriber> info_token_listener;


    // init
    public Game(LinkedList<LinkedList<String>> hand,int timer, boolean rainbow, boolean wild) {
        this.inforTokenCountor = 8;
        this.blackFuseCounter= 3;
        this.playernumber=hand.size();
        this.hands=Card.stringtohand(hand);
        this.points=0;
        this.rainbow = rainbow;
        this.wild = wild;

        int handcount = hands.size() * hands.get(0).size();

        if(rainbow)
        {
            this.deckCount = 60 - handcount;
        }else {
            this.deckCount = 50 - handcount;
        }


        this.discardedcard=new LinkedList<>();
        this.history=new LinkedList<>();

        this.timer = timer;

        this.currentPlayer=0;
        this.lastTurn=false;

        firework = new LinkedList<>();

        if(rainbow && !wild)
        {
            for(int i = 0; i < 6; i++)
            {
                firework.add(new LinkedList<Card>());
                discardedcard.add(new LinkedList<>());
            }
        }else
        {
            for(int i = 0; i < 5; i++)
            {
                firework.add(new LinkedList<Card>());
                discardedcard.add(new LinkedList<>());
            }
        }

        for (int i =0 ; i<hands.size();i++){
            if (hands.get(i).get(0).getCardColor() == 'q')
            {
                you = i;
            }
        }

    }

    public void addHistory(Action action) {
        this.history.add(action);
    }


    public int getBlackFuseCounter() {
        return blackFuseCounter;
    }

    public int getDeckCount() {
        return deckCount;
    }

    public LinkedList<LinkedList<Card>> getDiscardedcard() {
        return discardedcard;
    }

    public LinkedList<Action> getHistory()
    {
        return history;
    }

    public int getPoints()
    {
        int points = 0;

        for(int i = 0 ; i < firework.size(); i++)
        {
            points+= firework.get(i).size();
        }

        return points;
    }

    public void setInforTokenCountor(int inforTokenCountor)
    {
        this.inforTokenCountor = inforTokenCountor;
    }

    public void setBlackFuseCounter()
    {
        this.blackFuseCounter--;
    }

    public void setDeckCount()
    {
        this.deckCount--;
    }

    public void addToDiscardedcard(Card card) {
        char colour = card.cardColor;
        boolean found = false;

        //find the firework with the same colour
        for(int i = 0; i < this.discardedcard.size(); i++)
        {
            if((discardedcard.get(i).size() != 0) && discardedcard.get(i).get(0).cardColor == colour)
            {
                discardedcard.get(i).add(card);
                found = true;
            }
        }

        //if the firework of that colour doesnt exist yet
        if(found == false)
        {
            for(int i = 0; i < this.discardedcard.size(); i++)
            {
                if(discardedcard.get(i).size() == 0)
                {
                    discardedcard.get(i).add(card);
                    return;
                }
            }
        }
    }

    public void increaseinfotoken(){
        if (inforTokenCountor == 8){
            inforTokenCountor = 8;
        }
        else {
            inforTokenCountor++;
        }
    }


    public void increaseCurrentPlayer() {
        if (this.currentPlayer ==( hands.size() -1)){
            currentPlayer = 0;
        }
        else{
            this.currentPlayer ++;
        }
    }


    public void setHands(LinkedList<LinkedList<Card>> hands) {
        this.hands = hands;
    }

    public void changeCard(Card card, int pos)
    {
        for (int i = 0; i < hands.get(currentPlayer).size(); i++) {
            if (hands.get(currentPlayer).get(i).getCardIndex() == pos) {
                hands.get(currentPlayer).set(i,card);
            }
        }
    }

    public void addToFirework(Card card)
    {
        char colour = card.cardColor;
        boolean found = false;

        //find the firework with the same colour
        for(int i = 0; i < this.firework.size(); i++) {
            if((firework.get(i).size() != 0) && firework.get(i).get(0).cardColor == colour) {
                firework.get(i).add(card);
                found = true;
            }
        }

        //if the firework of that colour doesnt exist yet
        if(found == false) {
            for(int i = 0; i < this.firework.size(); i++) {
                if(firework.get(i).size() == 0) {
                    firework.get(i).add(card);
                    return;
                }
            }
        }
    }

    public void setYouTurn(boolean youTurn) {
        this.youTurn = youTurn;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public LinkedList<LinkedList<Card>> getFirework() {
        return firework;
    }


    public int getInforTokenCountor() {
        return inforTokenCountor;
    }

    public LinkedList<LinkedList<Card>> getHands() {
        return hands;
    }

    public int getTimer() {
        return timer;
    }

    public int getYou() {
        return you;
    }

    public boolean getLastTurn()
    {
        return lastTurn;
    }

    public void inthinttohand(int hint,LinkedList<Boolean> listhint){
        for (int i =0; i<listhint.size();i++){
            if (listhint.get(i) == true)
            {
                hands.get(getYou()).get(i).setCardRank(hint);
                hands.get(getYou()).get(i).setRankKnown();
            }
        }
    }

    public void colorhinttohand(char color,LinkedList<Boolean> listhint){
        for (int i =0; i<listhint.size();i++){
            if (listhint.get(i) == true) {
                hands.get(getYou()).get(i).setCardColor(color);
                hands.get(getYou()).get(i).setRankKnown();
            }
        }
    }

    public void inthinttoother(int playerposition, int hint){
        for (int i =0 ; i<hands.get(playerposition -1).size();i++){
            if (hands.get(playerposition -1).get(i).getCardRank() == hint){
                hands.get(playerposition -1).get(i).setRankKnown();
            }
        }
    }


    public void colorhinttoother(int playernumber,char suit){
        for (int i =0 ; i<hands.get(playernumber -1).size();i++){
            if (hands.get(playernumber -1).get(i).getCardColor() == suit){
                hands.get(playernumber -1).get(i).setColorKnown();
            }
        }
    }

    public void fireworkordiscard(Card card){
        for (int i = 0; i < firework.size();i++){
            if (firework.get(i).size() == 0){
                if (card.getCardRank() == 1){
                    addToFirework(card);
                    break;
                }
                else {
                    addToDiscardedcard(card);
                    blackFuseCounter--;
                    break;
                }
            }
            if ((firework.get(i).size() !=0) && firework.get(i).get(0).getCardColor() == card.getCardColor()){
                if (card.getCardRank() == firework.get(i).getLast().getCardRank()+1 ){
                    addToFirework(card);
                    break;
                }
                else {
                    addToDiscardedcard(card);
                    blackFuseCounter--;
                    break;
                }
            }
        }
    }

    public void writetocsv(String action,int position, String suit, int rank){
        try {
            FileWriter output = new FileWriter(file,true);
            ArrayList<String> result = new ArrayList<>();

            for (Card i: hands.get(currentPlayer)){
                result.add(i.toString());
            }

            for (int i=0;i< hands.size();i++){
                if (i != currentPlayer){
                    for (Card j: hands.get(i)){
                        result.add(j.toString());
                    }
                }
            }

            for (int i=0;i< hands.size();i++){
                if (i != currentPlayer){
                    for (Card j: hands.get(i)){
                        result.add(String.valueOf(j.getRankKnown()));
                        result.add(String.valueOf(j.getColorKnown()));
                    }
                }
            }



            int startpoint = 0;
            for (LinkedList<Card> i : firework){
                if (i.size() != 0){
                    result.add(i.getLast().toString());
                }
            }

            for (int i =0 ; i<firework.size();i++){
                if (firework.get(i).size() != 0){
                    startpoint = i;
                }
            }

            for (int i = startpoint; i< firework.size();i++){
                result.add("nocard");
            }
            result.add(String.valueOf(inforTokenCountor));

            result.add(action);
            result.add(String.valueOf(position));
            result.add(suit);
            result.add(String.valueOf(rank));

            String collect = result.stream().collect(Collectors.joining(",")) + "\n";
            output.write(collect);
            output.close();

        }
        catch (IOException e){

        }


    }
}

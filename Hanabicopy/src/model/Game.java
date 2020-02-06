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
    private int inforTokenCounter;
    private int blackFuseCounter;
    //rainbow
    private boolean rainbow;
    // linkedlist
    private LinkedList <LinkedList<Card>> discardedcard;
    // timer
    private int timer;
    // Current
    private int currentPlayer;
    //hand
    private LinkedList<LinkedList<Card>> hands;
    // firework
    private LinkedList<LinkedList<Card>> firework;
    // if this is last turn
    private boolean lastTurn;
    // your seat number
    private int you;
    // your turn?
    private  boolean youTurn;
    // listeners
    private ArrayList<Subscriber> point_listener;
    private ArrayList<Subscriber> card_change_listener;
    private ArrayList<Subscriber> fuse_token_listener;
    private ArrayList<Subscriber> info_token_listener;


    // init
    public Game(LinkedList<LinkedList<String>> hand,int timer, boolean rainbow) {
        // info token, fuse token
        this.inforTokenCounter = 8;
        this.blackFuseCounter= 3;
        // hands init
        this.hands=Card.stringtohand(hand);
        // if rainbow cards
        this.rainbow = rainbow;
        // init linked list of discard and history
        this.discardedcard=new LinkedList<>();
        this.firework = new LinkedList<>();
        this.timer = timer;
        // start from player 0
        this.currentPlayer=0;
        this.lastTurn=false;


        if(rainbow)
        {
            for(int i = 0; i < 6; i++)
            {
                firework.add(new LinkedList<>());
                discardedcard.add(new LinkedList<>());
            }
        }
        else {
            for(int i = 0; i < 5; i++)
            {
                firework.add(new LinkedList<>());
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


    public int getBlackFuseCounter() {
        return blackFuseCounter;
    }


    public LinkedList<LinkedList<Card>> getDiscardedCard() {
        return discardedcard;
    }

    // add listener ----------------------------------
    public int getPoints()
    {
        int points = 0;

        for(int i = 0 ; i < firework.size(); i++)
        {
            points+= firework.get(i).size();
        }

        return points;
    }


    // add listener ------------------------------------
    public void minusBlackFuseCounter()
    {
        this.blackFuseCounter--;
    }

    public void minusInfoCounter()
    {
        this.inforTokenCounter--;
    }


    // add the parameter to discard;
    // add listener ----------------------------------
    public void addToDiscard(Card card) {
        char colour = card.getCardColor();
        boolean found = false;

        //find the firework with the same colour
        for(int i = 0; i < this.discardedcard.size(); i++)
        {
            if((discardedcard.get(i).size() != 0) && discardedcard.get(i).get(0).getCardColor() == colour)
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


    // add listener ----------------------------------
    // add card to firework
    public void addToFirework(Card card)
    {
        char colour = card.getCardColor();
        boolean found = false;

        //find the firework with the same colour
        for(int i = 0; i < this.firework.size(); i++) {
            if((firework.get(i).size() != 0) && firework.get(i).get(0).getCardColor() == colour) {
                firework.get(i).add(card);
                found = true;
            }
        }

        //if the firework of that colour doesnt exist yet
        if(!found) {
            for (LinkedList<Card> cards : this.firework) {
                if (cards.size() == 0) {
                    cards.add(card);
                    return;
                }
            }
        }
    }


    // add one info token
    // add listener ----------------------------------
    public void increaseinfotoken(){
        if (inforTokenCounter == 8){
            inforTokenCounter = 8;
        }
        else {
            inforTokenCounter++;
        }
    }


    // add listener ----------------------------------
    public void increaseCurrentPlayer() {
        if (this.currentPlayer ==( hands.size() -1)){
            currentPlayer = 0;
        }
        else{
            this.currentPlayer ++;
        }
    }

    // add listener ----------------------------------
    // probably not set, but remove some card from hands
    public void setHands(LinkedList<LinkedList<Card>> hands) {
        this.hands = hands;
    }

    // add listener ----------------------------------
    public void changeCard(Card card, int pos)
    {
        for (int i = 0; i < hands.get(currentPlayer).size(); i++) {
            if (hands.get(currentPlayer).get(i).getCardIndex() == pos) {
                hands.get(currentPlayer).set(i,card);
            }
        }
    }

    // add listener ----------------------------------
    public void setYouTurn(boolean youTurn) {
        this.youTurn = youTurn;
    }


    public int getCurrentPlayer() {
        return currentPlayer;
    }


    public LinkedList<LinkedList<Card>> getFirework() {
        return firework;
    }


    public int getInfoToken() {
        return inforTokenCounter;
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


    public void intHintToHand(int hint, LinkedList<Boolean> listhint){
        for (int i =0; i<listhint.size();i++){
            if (listhint.get(i))
            {
                hands.get(getYou()).get(i).setCardRank(hint);
                hands.get(getYou()).get(i).setRankKnown();
            }
        }
    }


    public void colorHintToHand(char color, LinkedList<Boolean> listhint){
        for (int i =0; i<listhint.size();i++){
            if (listhint.get(i)) {
                hands.get(getYou()).get(i).setCardColor(color);
                hands.get(getYou()).get(i).setRankKnown();
            }
        }
    }


    public void intHintToOther(int playerposition, int hint){
        for (int i =0 ; i<hands.get(playerposition -1).size();i++){
            if (hands.get(playerposition -1).get(i).getCardRank() == hint){
                hands.get(playerposition -1).get(i).setRankKnown();
            }
        }
    }


    public void colorHintToOther(int player_number, char suit){
        for (int i =0 ; i<hands.get(player_number -1).size();i++){
            if (hands.get(player_number -1).get(i).getCardColor() == suit){
                hands.get(player_number -1).get(i).setColorKnown();
            }
        }
    }


    // judge if the current card go to firework or discard;
    public void fireworkOrDiscard(Card card){
        for (int i = 0; i < firework.size();i++){
            if (firework.get(i).size() == 0){
                if (card.getCardRank() == 1){
                    addToFirework(card);
                    break;
                }
                else {
                    addToDiscard(card);
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
                    addToDiscard(card);
                    blackFuseCounter--;
                    break;
                }
            }
        }
    }

    // for data collecting
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
            result.add(String.valueOf(inforTokenCounter));
            result.add(action);
            result.add(String.valueOf(position));
            result.add(suit);
            result.add(String.valueOf(rank));
            String collect = result.stream().collect(Collectors.joining(",")) + "\n";
            output.write(collect);
            output.close();
        }
        catch (IOException e){
            System.out.println("data collecting error");
        }
    }

    public void notifyPoint_listener() {
        for (Subscriber i: point_listener){
            i.notifythis();
        }
    }

    public void addPoint_listener(Subscriber subscriber) {
        this.point_listener.add(subscriber);
    }

    public void notifyCard_change_listener() {
        for (Subscriber i: card_change_listener){
            i.notifythis();
        }
    }

    public void addCard_change_listener(Subscriber subscriber) {
        this.card_change_listener.add(subscriber);
    }


    public void notifyFuse_token_listener() {
        for (Subscriber i: fuse_token_listener){
            i.notifythis();
        }
    }

    public void addFuse_token_listener(Subscriber subscriber) {
        this.fuse_token_listener.add(subscriber);
    }

    public void notifyInfo_token_listener() {
        for (Subscriber i: info_token_listener){
            i.notifythis();
        }
    }

    public void addInfo_token_listener(Subscriber subscriber) {
        this.info_token_listener.add(subscriber);
    }


}

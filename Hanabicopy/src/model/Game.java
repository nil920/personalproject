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
    // points
    private int points;
    // listeners
    private ArrayList<Subscriber> subscribers;


    // for change hand;
    private int changed_player;
    private int changed_int;



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

        points = 0;

        subscribers = new ArrayList<>();

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
    public void addPoints()
    {
        points++;
        notifyPoint_listener();
    }

    public int getPoints(){
        return points;
    }




    // add listener ------------------------------------
    public void minusBlackFuseCounter()
    {
        this.blackFuseCounter--;
        notifyFuse_token_listener();
    }

    public void minusInfoCounter()
    {
        this.inforTokenCounter--;
        notifyInfo_token_listener();
    }


    public void removeCurrentPlayerHand(int position){
        hands.get(currentPlayer).remove(position-1);
        for (int i =position-1; i< hands.get(currentPlayer).size();i++){
            hands.get(currentPlayer).get(i).setCardIndex(i+1);
        }
        notifyCardRemove();
    }

    public Card getCardAtPosition(int positon){
        return hands.get(currentPlayer).get(positon-1);
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
        if(!found)
        {
            for(int i = 0; i < this.discardedcard.size(); i++)
            {
                if(discardedcard.get(i).size() == 0)
                {
                    discardedcard.get(i).add(card);
                    notifyDiscard_change();
                    return;
                }
            }
        }
        notifyDiscard_change();
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
                    notifyFirework_change();
                    return;
                }
            }
        }
        notifyFirework_change();
        addPoints();
    }


    // add one info token
    // add listener ----------------------------------
    public void increaseinfotoken(){
        if (inforTokenCounter == 8){
            inforTokenCounter = 8;
        }
        else {
            inforTokenCounter++;
            notifyInfo_token_listener();
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
        notifyCurrent_Player_change();
    }


    // add listener ----------------------------------
    public void changeCard(Card card, int pos)
    {

        LinkedList <Card> current_player_hand = hands.get(currentPlayer);
        for (int i = 0; i < current_player_hand.size(); i++) {
            if (current_player_hand.get(i).getCardIndex() == pos) {
                hands.get(currentPlayer).set(i,card);
            }
        }
        notifyHand_change();
    }

    // add listener ----------------------------------
    public void setYouTurn(boolean youTurn) {
        this.youTurn = youTurn;
        if (youTurn){
            notifyYou_Turn();
        }
        if (!youTurn){
            disableActionListener();
        }
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
            if (listhint.get(i) == Boolean.TRUE)
            {
                hands.get(you).get(i).setCardRank(hint);
                hands.get(you).get(i).setRankKnown();
            }
        }
        notifyHint();
    }


    public void colorHintToHand(char color, LinkedList<Boolean> listhint){
        for (int i =0; i<listhint.size();i++){
            if (listhint.get(i) == Boolean.TRUE) {
                hands.get(you).get(i).setCardColor(color);
                hands.get(you).get(i).setColorKnown();
            }
        }
        notifyHint();
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
                    minusBlackFuseCounter();
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
                    minusBlackFuseCounter();
                    break;
                }
            }
        }
    }

    // for data collecting
    public void writeToCSV(String action, int position, String suit, int rank){
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

    private void notifyPoint_listener() {
        for (Subscriber i: subscribers){
            i.notifyPointChange();
        }
    }

    public void addListener(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    private void notifyFuse_token_listener() {
        for (Subscriber i: subscribers){
            i.notifyFuseTokenChange();
        }
    }

    private void notifyInfo_token_listener() {
        for (Subscriber i: subscribers){
            i.notifyInfoTokenChange();
        }
    }

    private void notifyHand_change(){
        for (Subscriber i : subscribers){
            i.notifyHandChange();
        }
    }

    private void notifyFirework_change(){
        for (Subscriber i: subscribers){
            i.notifyFireworkChange();
        }
    }

    private void notifyDiscard_change(){
        for (Subscriber i: subscribers){
            i.notifyDiscardChange();
        }
    }

    private void notifyCurrent_Player_change(){
        for (Subscriber i: subscribers){
            i.notifyCurrentPlayerChange();
        }
    }

    private void notifyYou_Turn(){
        for (Subscriber i: subscribers){
            i.addActionListener();
        }
    }

    private void disableActionListener(){
        for (Subscriber i:subscribers){
            i.disableActionListeners();
        }
    }


    private void notifyHint(){
        for (Subscriber i:subscribers){
            i.notifyHint();
        }
    }

    private void notifyCardRemove(){
        for (Subscriber i: subscribers){
            i.notifyCurrentPlayerRemove();
        }
    }


}

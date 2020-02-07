package startup;

import controller.ComputerController;
import controller.ConnectToServer;
import controller.ReceiveInfo;
import com.google.gson.Gson;
import model.Action;
import model.Card;
import model.Game;
import model.Waitingroom;
import view.*;
import view.listeners.MainListener;

import java.io.IOException;
import java.util.LinkedList;

/**
 * A class that starts up the Hanabi System GUI
 */
public class GUIHanabiSystem {
    private static Waitingroom waitingRoomModel;
    private static int gameid= 0;
    private static String token = "";
    public static Game gameModel;
    private static int timer;
    private static String rainbow;
    private static WaitingRoom waitingroomView;
    public static int cardindex;
    public static boolean canceltimer;
    public static GamePagePanel Hanabi_client;
    public static boolean AI = false;

    private static CoordinateSystem coordinateSystem;


    /**
     * Start up the Hanabi client application.
     */
    public static void main(String[] args) {

        //Startup the game with the main menu window
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);

//        connect to the server
        try {
            ConnectToServer.connect();
        }
        catch (IOException e){
            Message.infoBox("Error: Server Connection", "We can't connect to server right now. Please try again later.");
        }

//        keep checking for server response
        while (true){
            try {
                String jsonResponse = ConnectToServer.getMessageFromServer(); // json response from the server
                System.out.println(jsonResponse);
                Gson gson = new Gson();
                ReceiveInfo response = gson.fromJson(jsonResponse, ReceiveInfo.class); // convert json response

                if (response.reply.equals("invalid")) {
                    Message.infoBox("Error: Invalid", "Invalid action. Please try again.");
                }

                // game created, expect reply = join
                if (response.reply.equals("created")) {
                    gameid = response.gameid;
                    token = response.token;
                }

                // --------------------------waiting room-----------------------------------------------------
                // join info, initilize a waiting room
                if (response.reply.equals("joined")) {
                    if(token.equals("") && gameid == 0) {

                        waitingRoomModel = new Waitingroom(JoinGame.idField, JoinGame.secretTokenField, 300, response.needed);
                    }else{
                        waitingRoomModel = new Waitingroom(gameid, token, 300, response.needed);
                    }
                    waitingroomView = new WaitingRoom();
                    waitingroomView.init(waitingRoomModel);
                    waitingroomView.setVisible(true);
                    timer = response.timeout;
                    rainbow = response.rainbow;
                }

                // player left waiting room
                if (response.notice.equals("player left")) {
                    waitingRoomModel.setPlayersNeeded(response.needed);
                    waitingroomView.update(waitingRoomModel);
                }
                // player joined
                if (response.notice.equals("player joined")) {
                    waitingRoomModel.setPlayersNeeded(response.needed);
                    waitingroomView.update(waitingRoomModel);
                }


                // -------------------game stage--------------------------------------
                //game start
                if (response.notice.equals("game starts")) {
                    waitingroomView.dispose();
                    Hanabi_client = new GamePagePanel();
                    Hanabi_client.setGame(gameModel);
                    Hanabi_client.display();
                    if (rainbow.equals("none")) {
                        gameModel = new Game(response.hands, timer, false);
                    }
                    else {
                        gameModel = new Game(response.hands, timer, true);
                    }
                    // game view initialize
                    coordinateSystem = new CoordinateSystem();
                    coordinateSystem.setGame(gameModel);
                    coordinateSystem.init();
                    gameModel.addListener(coordinateSystem);

                }


                // your turn.
                if (response.reply.equals("your move")) {
                    // start timer
                    Hanabi_client.timer = new Timer();
                    Hanabi_client.AIA.setVisible(true);
                    // enable all acton listener
                    gameModel.setYouTurn(true);
                    canceltimer = false;
                    // enable ai assistant
                    Hanabi_client.aiButtonAddListener();
                    // if ai, go to ai package
                    if (!AI) {
                        while (!canceltimer) {
                            Hanabi_client.updateCounter(gameModel.getTimer());
                        }
                    }
                    else {
                        ComputerController computerController = new ComputerController(gameModel);
                        computerController.actionPerformed();
                        response.reply = "";
                    }
                }


                // discard card. to other player
                if (response.reply.equals("discarded")) {
                    if (!String.valueOf(response.card).equals("none")) {
                        // if the current player get a replaced card
                        CoordinateSystem.logWindow.append("\n" + Action.discardaction(gameModel, response.position-1));
                        // add the target card to discard.
                        gameModel.addToDiscard(gameModel.getHands().get(gameModel.getCurrentPlayer()).get(response.position-1));
                        // convert string to a card object
                        Card card = Card.stringtocard(response.card, response.position);
                        // replace this card
                        gameModel.changeCard(card, response.position);
                    }
                    else {
                        // remove the discarded card of current player's hand
                        gameModel.removeCurrentPlayerHand(response.position);
                    }
                    gameModel.increaseCurrentPlayer();
                    gameModel.increaseinfotoken();
                }


                if (response.reply.equals("accepted")) {
                    if (Boolean.FALSE.equals(response.replaced)){
                        gameModel.removeCurrentPlayerHand(cardindex);
                    }
                    if (Boolean.TRUE.equals(response.replaced)) {
                        Card card = new Card(cardindex, 'q', 0);
                        Card old_card = Card.stringtocard(response.card,0);
                        gameModel.addToDiscard(old_card);
                        gameModel.changeCard(card, cardindex);
                    }
                    gameModel.increaseinfotoken();
                    gameModel.setYouTurn(false);
                    gameModel.increaseCurrentPlayer();
                    Hanabi_client.aiButtonRemoveListener();
                }


                // played card, to other;
                if (response.notice.equals("played")) {
                    if (!response.card.equals("none")) {
                        // add log --
                        CoordinateSystem.logWindow.append("\n" + Action.playaction(gameModel, response.position-1));
                        gameModel.fireworkOrDiscard(gameModel.getCardAtPosition(response.position));
                        Card card = Card.stringtocard(response.card, response.position);
                        gameModel.changeCard(card, response.position);
                    }
                    else {
                        gameModel.removeCurrentPlayerHand(response.position);
                    }
                    gameModel.increaseCurrentPlayer();
                }


                // played card, to you
                if (response.reply.equals("built")) {
                    if (Boolean.TRUE.equals(response.replaced)) {
                        Card card = new Card(response.position, 'q', 0);
                        gameModel.changeCard(card, response.position);

                        gameModel.addToFirework(Card.stringtocard(response.card,0));
                    }
                    else {
                        gameModel.removeCurrentPlayerHand(response.position);
                        gameModel.addToFirework(Card.stringtocard(response.card,0));
                    }
                    gameModel.setYouTurn(false);
                    gameModel.increaseCurrentPlayer();
                    Hanabi_client.aiButtonRemoveListener();
                }


                // burned card, to you
                if (response.reply.equals("burned")) {
                    if (Boolean.TRUE.equals(response.replaced)) {
                        Card card = new Card(cardindex, 'q', 0);
                        gameModel.changeCard(card, cardindex);

                        gameModel.addToDiscard(Card.stringtocard(response.card,0));
                    }
                    else {
                        gameModel.removeCurrentPlayerHand(response.position);

                        gameModel.addToDiscard(Card.stringtocard(response.card,0));
                    }
                    gameModel.setYouTurn(false);
                    gameModel.minusBlackFuseCounter();
                    gameModel.increaseCurrentPlayer();
                    Hanabi_client.aiButtonRemoveListener();
                }


                if (response.reply.equals("inform")) {
                    if (response.info != null && response.rank!=0  && response.player==0){
                        gameModel.intHintToHand(response.rank,response.info);
                        CoordinateSystem.logWindow.append("\n" + Action.youinformaction(gameModel, response.rank));
                    }
                    if (response.info != null && !response.suit.equals("")  && response.player==0 ){
                        gameModel.colorHintToHand(response.suit.charAt(0),response.info);
                        CoordinateSystem.logWindow.append("\n" + Action.youinformaction(gameModel, response.suit));
                    }
                    gameModel.setYouTurn(false);
                    gameModel.minusInfoCounter();
                    gameModel.increaseCurrentPlayer();
                    Hanabi_client.aiButtonRemoveListener();
                }


                if (response.notice.equals("inform")){
                    if (response.player!=0 && response.rank !=0 && response.info == null){
                        gameModel.intHintToOther(response.player,response.rank);
                        CoordinateSystem.logWindow.append("\n" + Action.informaction(gameModel,response.player, response.rank));
                    }
                    if (response.player!=0 && !response.suit.equals("")   && response.info ==null){
                        gameModel.colorHintToOther(response.player,response.suit.charAt(0));
                        CoordinateSystem.logWindow.append("\n" + Action.informaction(gameModel,response.player, response.suit));
                    }
                    gameModel.minusInfoCounter();
                    gameModel.increaseCurrentPlayer();
                }


                if (response.reply.equals("game cancelled")){
                    System.exit(0);
                }


                if (response.reply.equals("game ends")){

                    System.exit(0);
                }
            }
            catch (IOException e){
                System.out.println("Error, check your connection");
                break;
            }
        }
    }
}


package view;

import model.Card;
import model.Game;
import view.listeners.MainListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import static startup.GUIHanabiSystem.Hanabi_client;

public class CoordinateSystem implements Subscriber{
    private static JLabel[][] lblCard = new JLabel[6][6];
    private static JLabel[] lblPlayer=new JLabel[5];
    private static JLabel boardPlayedCard,boardDiscards,infoCounter,fuseCounter,currentPlayerB,totalpoint;
    private static JLabel CurrentPlayer;
    private static JLabel info,fuse,points;
    public static JScrollPane jScrollPane;
    private static JLabel[][] lbldiscard = new JLabel[6][10];
    private static JLabel[] lblfirework=new JLabel[6];
    public static JLabel pdcard;
    public static JTextArea logWindow;
    private static Game game;


    public CoordinateSystem() {
        /*
          the things not needed to change when model change, icon and backgrounds;
         */
        // played cards, add background
        boardPlayedCard = new JLabel();
        ImageIcon img = new javax.swing.ImageIcon(CoordinateSystem.class.getResource("/FireworkBack.png"));
        Image image = img.getImage();
        Image newing = image.getScaledInstance(420,120, Image.SCALE_SMOOTH);
        boardPlayedCard= new JLabel( new ImageIcon (newing));
        boardPlayedCard.setBounds(90,60,420,120);
        Hanabi_client.add(boardPlayedCard,JLayeredPane.PALETTE_LAYER);


        // discard card; add background
        boardDiscards = new JLabel();
        ImageIcon img1 = new ImageIcon(CoordinateSystem.class.getResource("/DiscardBack.png"));
        Image image1 = img1.getImage();
        Image newing1 = image1.getScaledInstance(420,500, Image.SCALE_SMOOTH);
        boardDiscards= new JLabel( new ImageIcon (newing1));
        boardDiscards.setBounds(90,200,420,500);
        Hanabi_client.add(boardDiscards,JLayeredPane.PALETTE_LAYER);


        // info counter, add a icon
        infoCounter=new JLabel();
        ImageIcon img2 = new ImageIcon(CoordinateSystem.class.getResource("/time.png" ));
        Image image2 = img2.getImage();
        Image newing2 = image2.getScaledInstance(50,50, Image.SCALE_SMOOTH);
        infoCounter= new JLabel( new ImageIcon (newing2));
        infoCounter.setBounds(550,170,50,50);
        Hanabi_client.add(infoCounter,JLayeredPane.MODAL_LAYER);


        // fuse counter icon
        fuseCounter=new JLabel();
        ImageIcon imgfuse = new ImageIcon(CoordinateSystem.class.getResource("/boom.png") );
        Image imgefuse = imgfuse.getImage();
        Image newfuse = imgefuse.getScaledInstance(50,50, Image.SCALE_SMOOTH);
        fuseCounter= new JLabel( new ImageIcon (newfuse));
        fuseCounter.setBounds(550,220,50,50);
        Hanabi_client.add(fuseCounter,JLayeredPane.MODAL_LAYER);


        // current player text:
        currentPlayerB=new JLabel("Current Player");
        currentPlayerB.setFont((new Font("Time",Font.BOLD,18)));
        currentPlayerB.setForeground(Color.white);
        currentPlayerB.setBounds(550,210,150,150);
        Hanabi_client.add(currentPlayerB,JLayeredPane.MODAL_LAYER);


        // total point text
        totalpoint=new JLabel("Total points:");
        totalpoint.setFont((new Font("Time",Font.BOLD,18)));
        totalpoint.setForeground(Color.white);
        totalpoint.setBounds(550,50,150,50);
        Hanabi_client.add(totalpoint,JLayeredPane.MODAL_LAYER);


        // log window init
        logWindow = new JTextArea("Welcome Hanabi!");
        logWindow.setFont((new Font("Time", Font.BOLD, 14)));
        logWindow.setForeground(Color.black);
        jScrollPane = new JScrollPane(logWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setBounds(550,360,210,350);
        jScrollPane.getViewport().getView().setEnabled(false);
        jScrollPane.getViewport().getView().setForeground(Color.black);
        Hanabi_client.add(jScrollPane,JLayeredPane.MODAL_LAYER);
    }

    public void setGame(Game game) {
        CoordinateSystem.game = game;
    }

    // using game model to initialize
    public void init(){
        // init hands of players
        inithands();

        // player label for selection. only need to do once.
        for (int i =0; i< game.getHands().size();i++){
            lblPlayer[i] = new JLabel();
            ImageIcon img = new ImageIcon(CoordinateSystem.class.getResource("/player"+String.valueOf(i+1)+".png" ));
            Image image = img.getImage();
            Image newing = image.getScaledInstance(120,50, Image.SCALE_SMOOTH);
            lblPlayer[i]= new JLabel( new ImageIcon (newing));
            lblPlayer[i].setBounds(780,50+i*120,120,50);
            Hanabi_client.add(lblPlayer[i],JLayeredPane.MODAL_LAYER);
        }


        //current player.
        int i = game.getCurrentPlayer();
        ImageIcon img = new ImageIcon(CoordinateSystem.class.getResource("/player"+String.valueOf(i+1)+".png" ));
        Image image = img.getImage();
        Image newing = image.getScaledInstance(150,80, Image.SCALE_SMOOTH);
        CurrentPlayer= new JLabel( new ImageIcon (newing));
        CurrentPlayer.setBounds(550,290+i,150,80);
        Hanabi_client.add(CurrentPlayer,JLayeredPane.MODAL_LAYER);


        //game info counter.
        int v =game.getInfoToken();
        info = new JLabel(String.valueOf(v));
        info.setFont((new Font("Time",Font.BOLD,25)));
        info.setForeground(Color.white);
        info.setBounds(650,160,70,70);
        Hanabi_client.add(info,JLayeredPane.MODAL_LAYER);


        // game fuse counter.
        int m=game.getBlackFuseCounter();
        fuse=new JLabel(String.valueOf(m));
        fuse.setFont((new Font("Time",Font.BOLD,25)));
        fuse.setForeground(Color.white);
        fuse.setBounds(650,210,70,70);
        Hanabi_client.add(fuse,JLayeredPane.MODAL_LAYER);


        // game points.
        int t = game.getPoints();
        points=new JLabel(String.valueOf(t));
        points.setFont((new Font("Time",Font.BOLD,26)));
        points.setForeground(Color.white);
        points.setBounds(550,80,70,70);
        Hanabi_client.add(points,JLayeredPane.MODAL_LAYER);
    }


    private void inithands(){
        LinkedList<LinkedList<Card>> hand = game.getHands();
        for (int i =0; i< hand.size(); i++){
            for (int j= 0 ; j < hand.get(i).size();j++){
                lblCard[i][j] = new JLabel();
                ImageIcon img = new ImageIcon(CoordinateSystem.class.getResource ( "/"+hand.get(i).get(j).getCardColor() + hand.get(i).get(j).getCardRank() +".jpg" ));
                Image image = img.getImage();
                Image newing = image.getScaledInstance(60,97, Image.SCALE_SMOOTH);
                lblCard[i][j] = new JLabel( new ImageIcon (newing));
                lblCard[i][j].setBounds(900+j*70,50+i*120,60,97);
                Hanabi_client.add(lblCard[i][j],JLayeredPane.MODAL_LAYER);
            }
        }
    }


    private void discardAddToPanel(){
        LinkedList<LinkedList<Card>> discarded  =game.getDiscardedCard();
        for (int i =0; i< discarded.size(); i++){
            for (int j= 0 ; j < discarded.get(i).size();j++){
                if (lbldiscard[i][j] == null){
                    ImageIcon discard = new ImageIcon(CoordinateSystem.class.getResource( "/"+discarded.get(i).get(j).getCardColor() + discarded.get(i).get(j).getCardRank() +".jpg" ));
                    Image discardImage = discard.getImage();
                    Image discard_resized = discardImage.getScaledInstance(50,83, Image.SCALE_SMOOTH);
                    lbldiscard[i][j] = new JLabel( new ImageIcon (discard_resized));
                    lbldiscard[i][j].setBounds(90+j*20,200+i*83,50,83);
                    Hanabi_client.add(lbldiscard[i][j],JLayeredPane.MODAL_LAYER);
                }
            }
        }
    }


    private void fireworkAddToPanel(){
        for (int i =0; i< game.getFirework().size(); i++){
            if (game.getFirework().get(i).size() ==0){
                break;
            }
            ImageIcon fire = new ImageIcon(CoordinateSystem.class.getResource( "/"+game.getFirework().get(i).getLast().getCardColor()
                    + game.getFirework().get(i).getLast().getCardRank() +".jpg" ));
            Image temp = fire.getImage();
            Image firework_resized = temp.getScaledInstance(60,97, Image.SCALE_SMOOTH);
            if (lblfirework[i] == null){
                lblfirework[i] = new JLabel( new ImageIcon (firework_resized));
                lblfirework[i].setBounds(90+70*i,70,60,97);
                Hanabi_client.add(lblfirework[i],JLayeredPane.MODAL_LAYER);
                Hanabi_client.repaint();
            }
            else {
                lblfirework[i].setIcon(new ImageIcon(firework_resized));
            }
        }
    }


    public void addActionListeners() {
        LinkedList<Card> your_hand = game.getHands().get(game.getYou());
        for (int i = 0; i < your_hand.size(); i++) {
            lblCard[game.getYou()][i].addMouseListener(MainListener.createcardListener(your_hand.get(i)));
        }
        for (int i = 0; i < game.getHands().size(); i++) {
            if (i != game.getYou()) {
                lblPlayer[i].addMouseListener(MainListener.createPlayerListener(i));
            }
        }
    }


    public void disableActionListener(){
        LinkedList<Card> your_hand = game.getHands().get(game.getYou());
        for (int i = 0; i < your_hand.size(); i++) {
            for (MouseListener j : lblCard[game.getYou()][i].getMouseListeners()) {
                    lblCard[game.getYou()][i].removeMouseListener(j);
            }
        }
        for (int i = 0; i < game.getHands().size(); i++) {
            if (i != game.getYou()) {
                for (MouseListener j : lblPlayer[i].getMouseListeners()){
                    lblPlayer[i].removeMouseListener(j);
                }
            }
        }
    }


    private void ChangeIcon(int player){
        int counter = 0;
        for (Card i : game.getHands().get(player)){
            ImageIcon img = new ImageIcon(CoordinateSystem.class.getResource ( "/"+i.getCardColor() +i.getCardRank() +".jpg" ));
            Image image = img.getImage();
            Image newing = image.getScaledInstance(60,97, Image.SCALE_SMOOTH);
            lblCard[player][counter].setIcon(new ImageIcon(newing));
            counter++;
        }
    }

    private void cardRemove(int player){
        Hanabi_client.remove(lblCard[player][game.getHands().get(player).size()-1]);
        ChangeIcon(player);
    }



    @Override
    public void notifyPointChange() {
        points.setText(String.valueOf(game.getPoints()));
    }

    @Override
    public void notifyHandChange() {
        ChangeIcon(game.getCurrentPlayer());
    }

    @Override
    public void notifyInfoTokenChange() {
        info.setText(String.valueOf(game.getInfoToken()));
    }

    @Override
    public void notifyFuseTokenChange() {
        fuse.setText(String.valueOf(game.getBlackFuseCounter()));
    }

    @Override
    public void notifyCurrentPlayerChange() {
        int i = game.getCurrentPlayer();
        ImageIcon img = new ImageIcon(CoordinateSystem.class.getResource("/player"+String.valueOf(i+1)+".png" ));
        Image image = img.getImage();
        Image newing = image.getScaledInstance(150,80, Image.SCALE_SMOOTH);
        CurrentPlayer.setIcon(new ImageIcon(newing));
    }

    @Override
    public void notifyFireworkChange() {
        fireworkAddToPanel();
        ChangeIcon(game.getCurrentPlayer());
    }

    @Override
    public void notifyCurrentPlayerRemove() {
        cardRemove(game.getCurrentPlayer());
    }

    @Override
    public void notifyDiscardChange() {
        discardAddToPanel();
        ChangeIcon(game.getCurrentPlayer());
    }

    @Override
    public void addActionListener() {
        addActionListeners();
    }

    @Override
    public void disableActionListeners() {
        disableActionListener();
    }

    @Override
    public void notifyHint() {
        ChangeIcon(game.getYou());
    }
}

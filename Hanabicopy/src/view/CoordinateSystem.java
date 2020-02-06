package view;

import model.Card;
import model.Game;
import view.listeners.MainListener;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static startup.GUIHanabiSystem.Hanabiclient;

public class CoordinateSystem {
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


    public static void initboard()
    {
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
        Hanabiclient.panel.add(boardPlayedCard,JLayeredPane.PALETTE_LAYER);


        // discard card; add background
        boardDiscards = new JLabel();
        ImageIcon img1 = new ImageIcon(CoordinateSystem.class.getResource("/DiscardBack.png"));
        Image image1 = img1.getImage();
        Image newing1 = image1.getScaledInstance(420,500, Image.SCALE_SMOOTH);
        boardDiscards= new JLabel( new ImageIcon (newing1));
        boardDiscards.setBounds(90,200,420,500);
        Hanabiclient.panel.add(boardDiscards,JLayeredPane.PALETTE_LAYER);


        // info counter, add a icon
        infoCounter=new JLabel();
        ImageIcon img2 = new ImageIcon(CoordinateSystem.class.getResource("/time.png" ));
        Image image2 = img2.getImage();
        Image newing2 = image2.getScaledInstance(50,50, Image.SCALE_SMOOTH);
        infoCounter= new JLabel( new ImageIcon (newing2));
        infoCounter.setBounds(550,170,50,50);
        Hanabiclient.panel.add(infoCounter,JLayeredPane.MODAL_LAYER);


        // fuse counter icon
        fuseCounter=new JLabel();
        ImageIcon imgfuse = new ImageIcon(CoordinateSystem.class.getResource("/boom.png") );
        Image imgefuse = imgfuse.getImage();
        Image newfuse = imgefuse.getScaledInstance(50,50, Image.SCALE_SMOOTH);
        fuseCounter= new JLabel( new ImageIcon (newfuse));
        fuseCounter.setBounds(550,220,50,50);
        Hanabiclient.panel.add(fuseCounter,JLayeredPane.MODAL_LAYER);


        // current player text:
        currentPlayerB=new JLabel("Current Player");
        currentPlayerB.setFont((new Font("Time",Font.BOLD,18)));
        currentPlayerB.setForeground(Color.white);
        currentPlayerB.setBounds(550,210,150,150);
        Hanabiclient.panel.add(currentPlayerB,JLayeredPane.MODAL_LAYER);


        // total point text
        totalpoint=new JLabel("Total points:");
        totalpoint.setFont((new Font("Time",Font.BOLD,18)));
        totalpoint.setForeground(Color.white);
        totalpoint.setBounds(550,50,150,50);
        Hanabiclient.panel.add(totalpoint,JLayeredPane.MODAL_LAYER);


        // log window init
        logWindow = new JTextArea("Welcome Hanabi!");
        logWindow.setFont((new Font("Time", Font.BOLD, 14)));
        logWindow.setForeground(Color.black);
        jScrollPane = new JScrollPane(logWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setBounds(550,360,210,350);
        jScrollPane.getViewport().getView().setEnabled(false);
        jScrollPane.getViewport().getView().setForeground(Color.black);
        Hanabiclient.panel.add(jScrollPane,JLayeredPane.MODAL_LAYER);
    }


    // using game model to initialize
    public static void init(Game game){
        // init hands of players
        inithands(game);

        // player label for selection. only need to do once.
        for (int i =0; i< game.getHands().size();i++){
            lblPlayer[i] = new JLabel();
            ImageIcon img = new ImageIcon(CoordinateSystem.class.getResource("/player"+String.valueOf(i+1)+".png" ));
            Image image = img.getImage();
            Image newing = image.getScaledInstance(120,50, Image.SCALE_SMOOTH);
            lblPlayer[i]= new JLabel( new ImageIcon (newing));
            lblPlayer[i].setBounds(780,50+i*120,120,50);
            Hanabiclient.panel.add(lblPlayer[i],JLayeredPane.MODAL_LAYER);
        }


        //current player.
        int i = game.getCurrentPlayer();
        ImageIcon img = new ImageIcon(CoordinateSystem.class.getResource("/player"+String.valueOf(i+1)+".png" ));
        Image image = img.getImage();
        Image newing = image.getScaledInstance(150,80, Image.SCALE_SMOOTH);
        CurrentPlayer= new JLabel( new ImageIcon (newing));
        CurrentPlayer.setBounds(550,290+i,150,80);
        Hanabiclient.panel.add(CurrentPlayer,JLayeredPane.MODAL_LAYER);


        //game info counter.
        int v =game.getInfoToken();
        info = new JLabel(String.valueOf(v));
        info.setFont((new Font("Time",Font.BOLD,25)));
        info.setForeground(Color.white);
        info.setBounds(650,160,70,70);
        Hanabiclient.panel.add(info,JLayeredPane.MODAL_LAYER);


        // game fuse counter.
        int m=game.getBlackFuseCounter();
        fuse=new JLabel(String.valueOf(m));
        fuse.setFont((new Font("Time",Font.BOLD,25)));
        fuse.setForeground(Color.white);
        fuse.setBounds(650,210,70,70);
        Hanabiclient.panel.add(fuse,JLayeredPane.MODAL_LAYER);


        // game points.
        int t = game.getPoints();
        points=new JLabel(String.valueOf(t));
        points.setFont((new Font("Time",Font.BOLD,26)));
        points.setForeground(Color.white);
        points.setBounds(550,80,70,70);
        Hanabiclient.panel.add(points,JLayeredPane.MODAL_LAYER);


        LinkedList<LinkedList<Card>> discard = game.getDiscardedCard();
        discardaddtopanel(lbldiscard,discard);

        LinkedList<LinkedList<Card>> firework = game.getFirework();
        fireworkaddtopanel(lblfirework,firework);
    }


    private static void inithands(Game game){
        LinkedList<LinkedList<Card>> hand = game.getHands();
        for (int i =0; i< hand.size(); i++){
            for (int j= 0 ; j < hand.get(i).size();j++){
                lblCard[i][j] = new JLabel();
                ImageIcon img = new ImageIcon(CoordinateSystem.class.getResource ( "/"+hand.get(i).get(j).getCardColor() + hand.get(i).get(j).getCardRank() +".jpg" ));
                Image image = img.getImage();
                Image newing = image.getScaledInstance(60,97, Image.SCALE_SMOOTH);
                lblCard[i][j] = new JLabel( new ImageIcon (newing));
                lblCard[i][j].setBounds(900+j*70,50+i*120,60,97);
                Hanabiclient.panel.add(lblCard[i][j],JLayeredPane.MODAL_LAYER);
            }
        }
    }


    private static void discardaddtopanel(JLabel[][] tobeadd, LinkedList<LinkedList<Card>> hand){
        for (int i =0; i< hand.size(); i++){
            for (int j= 0 ; j < hand.get(i).size();j++){
                ImageIcon discardi = new ImageIcon(CoordinateSystem.class.getResource( "/"+hand.get(i).get(j).getCardColor() + hand.get(i).get(j).getCardRank() +".jpg" ));
                Image discardImage = discardi.getImage();
                Image newdiscard = discardImage.getScaledInstance(50,83, Image.SCALE_SMOOTH);
                tobeadd[i][j] = new JLabel( new ImageIcon (newdiscard));
                tobeadd[i][j].setBounds(90+j*20,200+i*83,50,83);
                Hanabiclient.panel.add(tobeadd[i][j],JLayeredPane.MODAL_LAYER);
            }
        }
    }


    private static void fireworkaddtopanel(JLabel[] tobeadd, LinkedList<LinkedList<Card>> hand){
        for (int i =0; i< hand.size(); i++){
            if (hand.get(i).size() ==0){
                break;
            }
            ImageIcon fire = new ImageIcon(CoordinateSystem.class.getResource( "/"+hand.get(i).getLast().getCardColor() + hand.get(i).getLast().getCardRank() +".jpg" ));
            Image fireim = fire.getImage();
            Image newfire = fireim.getScaledInstance(60,97, Image.SCALE_SMOOTH);
            tobeadd[i] = new JLabel( new ImageIcon (newfire));
            tobeadd[i].setBounds(90+70*i,70,60,97);
            Hanabiclient.panel.add(tobeadd[i],JLayeredPane.MODAL_LAYER);
        }
    }


    public static void clearpanel(int x,int y,int z){
        for (int i =0 ; i< x ;i++){
            for (int j =0; j<y;j++){
                Hanabiclient.panel.remove(lblCard[i][j]);
            }
        }
        for (int i=0;i<x;i++)
        {
        Hanabiclient.panel.remove(lblPlayer[i]);
        }

        for (int i=0;i<z;i++){
            if (lblfirework[i]==null){
                break;
            }
            Hanabiclient.panel.remove(lblfirework[i]);
        }
        for (int i =0;i<6;i++){
            if (lbldiscard[i]==null){
                break;
            }
            for (int j =0;j<10;j++){
                if (lbldiscard[i][j] == null)
                {
                    break;
                }
                else
                    {
                    Hanabiclient.panel.remove(lbldiscard[i][j]);
                    }
            }
        }
        Hanabiclient.panel.remove(CurrentPlayer);
        Hanabiclient.panel.remove(info);
        Hanabiclient.panel.remove(fuse);
        Hanabiclient.panel.remove(points);
        pdcard = new JLabel("");
    }


    public static void update(Game game){
        int x=game.getHands().size();
        int y = game.getHands().get(0).size();
        int z = game.getFirework().size();
        clearpanel(x,y,6);
        init(game);
    }


    public static void addActionListners(Game game) {
        for (int i = 0; i < game.getHands().get(0).size(); i++) {
            lblCard[game.getYou()][i].addMouseListener(MainListener.createcardListener(game.getHands().get(0).get(i)));
        }
        for (int i = 0; i < game.getHands().size(); i++) {
            if (i != game.getYou()) {
                lblPlayer[i].addMouseListener(MainListener.createPlayerListener(i));
            }
        }
    }
}

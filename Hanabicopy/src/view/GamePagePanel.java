package view;

import javax.swing.*;
import java.awt.*;

public class GamePagePanel {

    public Timer timer;
    public ImageIcon background=new ImageIcon(GamePagePanel.class.getResource("/background2.png"));
    public JLayeredPane panel = new JLayeredPane();
    public JFrame frame;
    public Menu menu;
    public JButton play,discard,give,AIA;
    public JButton color,rank;
    private JLabel lblBoard;
    private Counter counter;
    public JComboBox colors,ranks;

    public void init(){
        lblBoard  = new JLabel(background);
        lblBoard.setBounds(0,20,1366,768);

        counter = new Counter();
        counter.setBounds(900, 660, 450, 12);

        menu = new Menu();

        panel.add(lblBoard, JLayeredPane.DEFAULT_LAYER);

        frame = new GamePageFrame();
        frame.setLayeredPane(panel);
        frame.setJMenuBar(menu);
        frame.setVisible(true);

        play=new JButton("Play");
        play.setBounds(900,680,100,50);

        color = new JButton("Color");
        color.setBounds(900,680,100,50);

        String[] ccolor = {"r", "g", "y", "b", "w","m"};
        String[] crank = {"1", "2", "3", "4", "5"};

        colors= new JComboBox(ccolor);
        colors.setBounds(900,680,100,50);
        ranks=new JComboBox(crank);
        ranks.setBounds(900,680,100,50);

        discard=new JButton("Discard");
        discard.setBounds(1010,680,100,50);

        rank = new JButton("Rank");
        rank.setBounds(1010,680,100,50);

        give=new JButton("GiveHint");
        give.setBounds(1120,680,100,50);

        AIA=new JButton("AI helper");
        AIA.setBounds(1230,680,100,50);

        ImageIcon img = new ImageIcon(GamePagePanel.class.getResource("/background2.png"));
        Image image = img.getImage();
        Image newing = image.getScaledInstance(1366,768, Image.SCALE_SMOOTH);
        background = new ImageIcon(newing);
    }

    public void display() {
        panel.add(play, JLayeredPane.MODAL_LAYER);
        panel.add(discard, JLayeredPane.MODAL_LAYER);
        panel.add(rank, JLayeredPane.MODAL_LAYER);
        panel.add(color,JLayeredPane.MODAL_LAYER);
        panel.add(give,JLayeredPane.MODAL_LAYER);
        panel.add(AIA,JLayeredPane.MODAL_LAYER);
        panel.add(counter, JLayeredPane.MODAL_LAYER);
        panel.add(colors,JLayeredPane.MODAL_LAYER);
        panel.add(ranks,JLayeredPane.MODAL_LAYER);
        setBoardAppearance(background);
        AIA.setVisible(true);
        play.setVisible(false);
        give.setVisible(false);
        color.setVisible(false);
        rank.setVisible(false);
        discard.setVisible(false);
        colors.setVisible(false);
        ranks.setVisible(false);
    }

    public void updateCounter(int timer1) {
        counter.setSizeOnRemainingSeconds((int) timer.listen(timer1));
        panel.repaint();
    }

    public void setBoardAppearance(ImageIcon path) {
        lblBoard.setIcon(path);
        panel.repaint();
    }

    public void setplaydisButtonDisplay(boolean flag){
        discard.setVisible(flag);
        play.setVisible(flag);
    }

    public void setcardrankbuttonDisplay(boolean flag){
        color.setVisible(flag);
        rank.setVisible(flag);
    }

    public void disableallbutton(){
        color.setVisible(false);
        rank.setVisible(false);
        discard.setVisible(false);
        AIA.setVisible(false);
        play.setVisible(false);
        give.setVisible(false);
        colors.setVisible(false);
        ranks.setVisible(false);
    }
}

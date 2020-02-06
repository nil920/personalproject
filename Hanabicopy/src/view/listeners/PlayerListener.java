package view.listeners;

import view.CoordinateSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class PlayerListener extends MainListener{
    int player;
    boolean isSelected=false;
    boolean blocker= true;
    private JLabel pdcard;

    public PlayerListener(int player){
        this.player = player;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        isSelected = true;
        startup.GUIHanabiSystem.Hanabiclient.setcardrankbuttonDisplay(true);
        startup.GUIHanabiSystem.Hanabiclient.setplaydisButtonDisplay(false);
        startup.GUIHanabiSystem.Hanabiclient.give.setVisible(false);
        startup.GUIHanabiSystem.Hanabiclient.frame.repaint();
        if (blocker) {
            if (startup.GUIHanabiSystem.Hanabiclient.rank.getActionListeners().length == 0 && startup.GUIHanabiSystem.Hanabiclient.color.getActionListeners().length == 0) {
                startup.GUIHanabiSystem.Hanabiclient.rank.addActionListener(new GiveRankListener(player));
                startup.GUIHanabiSystem.Hanabiclient.color.addActionListener(new GiveColorListener(player));
                blocker = false;
            }
            else {
                for (ActionListener g: startup.GUIHanabiSystem.Hanabiclient.rank.getActionListeners()){
                    startup.GUIHanabiSystem.Hanabiclient.rank.removeActionListener(g);
                }
                for (ActionListener g: startup.GUIHanabiSystem.Hanabiclient.rank.getActionListeners()){
                    startup.GUIHanabiSystem.Hanabiclient.rank.removeActionListener(g);
                }
                startup.GUIHanabiSystem.Hanabiclient.color.addActionListener(new GiveColorListener(player));
                startup.GUIHanabiSystem.Hanabiclient.rank.addActionListener(new GiveRankListener(player));
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pdcard=new JLabel("Give information to player: "+ String.valueOf(player+1));
        pdcard.setFont((new Font("Time",Font.BOLD,12)));
        pdcard.setForeground(Color.white);
        pdcard.setBounds(900,680,400,50);
        startup.GUIHanabiSystem.Hanabiclient.panel.add(pdcard,JLayeredPane.MODAL_LAYER);
        startup.GUIHanabiSystem.Hanabiclient.frame.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        startup.GUIHanabiSystem.Hanabiclient.panel.remove(pdcard);
        startup.GUIHanabiSystem.Hanabiclient.frame.repaint();
    }
}

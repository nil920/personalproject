package view.listeners;

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
        startup.GUIHanabiSystem.Hanabi_client.setcardrankbuttonDisplay(true);
        startup.GUIHanabiSystem.Hanabi_client.setplaydisButtonDisplay(false);
        startup.GUIHanabiSystem.Hanabi_client.give.setVisible(false);
        startup.GUIHanabiSystem.Hanabi_client.frame.repaint();
        if (blocker) {
            if (startup.GUIHanabiSystem.Hanabi_client.rank.getActionListeners().length == 0 && startup.GUIHanabiSystem.Hanabi_client.color.getActionListeners().length == 0) {
                startup.GUIHanabiSystem.Hanabi_client.rank.addActionListener(new GiveRankListener(player));
                startup.GUIHanabiSystem.Hanabi_client.color.addActionListener(new GiveColorListener(player));
                blocker = false;
            }
            else {
                for (ActionListener g: startup.GUIHanabiSystem.Hanabi_client.rank.getActionListeners()){
                    startup.GUIHanabiSystem.Hanabi_client.rank.removeActionListener(g);
                }
                for (ActionListener g: startup.GUIHanabiSystem.Hanabi_client.rank.getActionListeners()){
                    startup.GUIHanabiSystem.Hanabi_client.rank.removeActionListener(g);
                }
                startup.GUIHanabiSystem.Hanabi_client.color.addActionListener(new GiveColorListener(player));
                startup.GUIHanabiSystem.Hanabi_client.rank.addActionListener(new GiveRankListener(player));
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pdcard=new JLabel("Give information to player: "+ String.valueOf(player+1));
        pdcard.setFont((new Font("Time",Font.BOLD,12)));
        pdcard.setForeground(Color.white);
        pdcard.setBounds(900,680,400,50);
        startup.GUIHanabiSystem.Hanabi_client.panel.add(pdcard,JLayeredPane.MODAL_LAYER);
        startup.GUIHanabiSystem.Hanabi_client.frame.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        startup.GUIHanabiSystem.Hanabi_client.panel.remove(pdcard);
        startup.GUIHanabiSystem.Hanabi_client.frame.repaint();
    }
}

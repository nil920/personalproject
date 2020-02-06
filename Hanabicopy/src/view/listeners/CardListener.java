package view.listeners;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class CardListener extends MainListener{

    private Card card;
    private JLabel pdcard;
    boolean blocker=true;


    public CardListener(Card card){
        this.card = card;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        startup.GUIHanabiSystem.Hanabiclient.setplaydisButtonDisplay(true);
        startup.GUIHanabiSystem.Hanabiclient.setcardrankbuttonDisplay(false);
        startup.GUIHanabiSystem.Hanabiclient.give.setVisible(false);
        if (blocker)
        {
            if (startup.GUIHanabiSystem.Hanabiclient.play.getActionListeners().length > 0){
                for (ActionListener g: startup.GUIHanabiSystem.Hanabiclient.play.getActionListeners())
                {
                    startup.GUIHanabiSystem.Hanabiclient.play.removeActionListener(g);
                }
                startup.GUIHanabiSystem.Hanabiclient.play.addActionListener(new PlayActionListener(card.getCardIndex()));
            }
            if (startup.GUIHanabiSystem.Hanabiclient.play.getActionListeners().length ==0){
                startup.GUIHanabiSystem.Hanabiclient.play.addActionListener(new PlayActionListener(card.getCardIndex()));
            }
            if (startup.GUIHanabiSystem.Hanabiclient.discard.getActionListeners().length > 0){
                for (ActionListener g: startup.GUIHanabiSystem.Hanabiclient.discard.getActionListeners())
                {
                    startup.GUIHanabiSystem.Hanabiclient.discard.removeActionListener(g);
                }
                startup.GUIHanabiSystem.Hanabiclient.discard.addActionListener(new DiscardListener(card.getCardIndex()));
            }
            if (startup.GUIHanabiSystem.Hanabiclient.discard.getActionListeners().length ==0){
                startup.GUIHanabiSystem.Hanabiclient.discard.addActionListener(new DiscardListener(card.getCardIndex()));
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        pdcard=new JLabel("Play / Discard card number: "+ String.valueOf(card.getCardIndex()));
        pdcard.setFont((new Font("Time",Font.BOLD,12)));
        pdcard.setForeground(Color.white);
        pdcard.setBounds(900,680,400,50);
        startup.GUIHanabiSystem.Hanabiclient.panel.add(pdcard,JLayeredPane.MODAL_LAYER);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        startup.GUIHanabiSystem.Hanabiclient.panel.remove(pdcard);
    }
}

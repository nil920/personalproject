package view.listeners;

import model.Card;
import startup.GUIHanabiSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class CardListener extends MainListener{

    private Card card;



    public CardListener(Card card){
        this.card = card;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        startup.GUIHanabiSystem.Hanabi_client.setplaydisButtonDisplay(true);
        startup.GUIHanabiSystem.Hanabi_client.setcardrankbuttonDisplay(false);
        startup.GUIHanabiSystem.Hanabi_client.give.setVisible(false);


        // if play button has an listener, delete it.
        if (startup.GUIHanabiSystem.Hanabi_client.play.getActionListeners().length > 0){
            for (ActionListener g: startup.GUIHanabiSystem.Hanabi_client.play.getActionListeners())
            {
                startup.GUIHanabiSystem.Hanabi_client.play.removeActionListener(g);
            }
            startup.GUIHanabiSystem.Hanabi_client.play.addActionListener(new PlayActionListener(card.getCardIndex()));
        }
        if (startup.GUIHanabiSystem.Hanabi_client.play.getActionListeners().length ==0){
            startup.GUIHanabiSystem.Hanabi_client.play.addActionListener(new PlayActionListener(card.getCardIndex()));
        }

        // same with discard button
        if (startup.GUIHanabiSystem.Hanabi_client.discard.getActionListeners().length > 0){
            for (ActionListener g: startup.GUIHanabiSystem.Hanabi_client.discard.getActionListeners())
            {
                startup.GUIHanabiSystem.Hanabi_client.discard.removeActionListener(g);
            }
            startup.GUIHanabiSystem.Hanabi_client.discard.addActionListener(new DiscardListener(card.getCardIndex()));
        }
        if (startup.GUIHanabiSystem.Hanabi_client.discard.getActionListeners().length ==0){
            startup.GUIHanabiSystem.Hanabi_client.discard.addActionListener(new DiscardListener(card.getCardIndex()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        GUIHanabiSystem.Hanabi_client.pdcard.setText("Play / Discard card number: "+ card.getCardIndex());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        GUIHanabiSystem.Hanabi_client.pdcard.setText("");
    }
}

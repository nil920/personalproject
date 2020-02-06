package view.listeners;

import model.Card;
import model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class MainListener extends MouseAdapter implements ActionListener {


    public static ExitMainListener ExitListener(){
        return new ExitMainListener();
    }

    public static ThemeMainListener createThemeOptionsListener(ImageIcon img){
        return new ThemeMainListener(img);
    }

    public static PlayActionListener playActionListener(int cardind){
        return new PlayActionListener(cardind);
    }

    public static DiscardListener creatediscardlistener(int cardind){
        return new DiscardListener(cardind);
    }

    public static PlayerListener createPlayerListener(int player){
        return new PlayerListener(player);
    }

    public static CardListener createcardListener(Card card){
        return new CardListener(card);
    }

    public static AIAssistListener createAIAlistener(Game game){
        return new AIAssistListener(game);
    }


    public void actionPerformed(ActionEvent e){
    }
}

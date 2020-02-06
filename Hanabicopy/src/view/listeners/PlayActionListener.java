package view.listeners;

import controller.MovesController;

import java.awt.event.ActionEvent;

public class PlayActionListener extends MainListener {
    private int cardid;
    public PlayActionListener(int cardind){
        this.cardid = cardind;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        startup.GUIHanabiSystem.cardindex = cardid;
        MovesController.play(cardid);
        startup.GUIHanabiSystem.Hanabiclient.disableallbutton();
    }
}
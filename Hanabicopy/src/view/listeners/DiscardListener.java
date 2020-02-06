package view.listeners;

import controller.MovesController;

import java.awt.event.ActionEvent;

public class DiscardListener extends MainListener{
    private int cardid;
    public DiscardListener(int cardind){
        this.cardid = cardind;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MovesController.discard(cardid);
        startup.GUIHanabiSystem.Hanabiclient.disableallbutton();
        startup.GUIHanabiSystem.cardindex=cardid;

    }


}

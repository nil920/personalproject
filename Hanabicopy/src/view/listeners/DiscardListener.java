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
        startup.GUIHanabiSystem.cardIndex =cardid;
        MovesController.discard(cardid);
        startup.GUIHanabiSystem.Hanabi_client.disableAllButton();
    }
}

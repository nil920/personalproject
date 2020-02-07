package view.listeners;

import controller.MovesController;

import java.awt.event.ActionEvent;

public class GiveColorActionListener extends MainListener{
    private int playerid;
    private String cardcolor;

    public GiveColorActionListener(int player){
        this.playerid = player+1;
    }

    //"Red", "Green", "Yellow", "Blue", "White","Rainbow"
    @Override
    public void actionPerformed(ActionEvent e) {
        cardcolor = (String.valueOf( startup.GUIHanabiSystem.Hanabi_client.colors.getSelectedItem()));
        System.out.println(cardcolor);
        MovesController.giveClueSuit(playerid,cardcolor);
        startup.GUIHanabiSystem.Hanabi_client.disableAllButton();
    }
}

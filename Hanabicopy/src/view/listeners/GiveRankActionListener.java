package view.listeners;

import controller.MovesController;

import java.awt.event.ActionEvent;

public class GiveRankActionListener extends MainListener{

    private int playerid;
    private int cardnumber;
    public GiveRankActionListener(int player){
        this.playerid = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cardnumber =Integer.valueOf((String) startup.GUIHanabiSystem.Hanabiclient.ranks.getSelectedItem());
        MovesController.giveClueRank(playerid,cardnumber);
        startup.GUIHanabiSystem.Hanabiclient.disableallbutton();

    }
}

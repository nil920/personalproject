package view.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GiveRankListener extends MainListener {
    private int playerid;

    public GiveRankListener(int player){
        this.playerid = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        startup.GUIHanabiSystem.Hanabi_client.setcardrankbuttonDisplay(false);
        startup.GUIHanabiSystem.Hanabi_client.setplaydisButtonDisplay(false);
        startup.GUIHanabiSystem.Hanabi_client.ranks.setVisible(true);
        startup.GUIHanabiSystem.Hanabi_client.give.setVisible(true);
        if (startup.GUIHanabiSystem.Hanabi_client.give.getActionListeners().length == 0){
            startup.GUIHanabiSystem.Hanabi_client.give.addActionListener(new GiveRankActionListener(playerid));
        }
        else {
            for (ActionListener g: startup.GUIHanabiSystem.Hanabi_client.give.getActionListeners()){
                startup.GUIHanabiSystem.Hanabi_client.give.removeActionListener(g);
            }
            startup.GUIHanabiSystem.Hanabi_client.give.addActionListener(new GiveRankActionListener(playerid));
        }
    }

}

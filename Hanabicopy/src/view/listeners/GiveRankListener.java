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
        startup.GUIHanabiSystem.Hanabiclient.setcardrankbuttonDisplay(false);
        startup.GUIHanabiSystem.Hanabiclient.setplaydisButtonDisplay(false);
        startup.GUIHanabiSystem.Hanabiclient.ranks.setVisible(true);
        startup.GUIHanabiSystem.Hanabiclient.give.setVisible(true);
        if (startup.GUIHanabiSystem.Hanabiclient.give.getActionListeners().length == 0){
            startup.GUIHanabiSystem.Hanabiclient.give.addActionListener(new GiveRankActionListener(playerid));
        }
        else {
            for (ActionListener g: startup.GUIHanabiSystem.Hanabiclient.give.getActionListeners()){
                startup.GUIHanabiSystem.Hanabiclient.give.removeActionListener(g);
            }
            startup.GUIHanabiSystem.Hanabiclient.give.addActionListener(new GiveRankActionListener(playerid));
        }
    }

}

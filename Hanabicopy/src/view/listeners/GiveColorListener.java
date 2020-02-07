package view.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GiveColorListener extends MainListener{
    private int playerid;

    public GiveColorListener(int player){
        this.playerid = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        startup.GUIHanabiSystem.Hanabi_client.setCardRankButtonDisplay(false);
        startup.GUIHanabiSystem.Hanabi_client.setPlayButtonDisplay(false);
        startup.GUIHanabiSystem.Hanabi_client.colors.setVisible(true);
        startup.GUIHanabiSystem.Hanabi_client.give.setVisible(true);
        if (startup.GUIHanabiSystem.Hanabi_client.give.getActionListeners().length == 0){
            startup.GUIHanabiSystem.Hanabi_client.give.addActionListener(new GiveColorActionListener(playerid));
        }
        else {
            for (ActionListener g: startup.GUIHanabiSystem.Hanabi_client.give.getActionListeners()){
                startup.GUIHanabiSystem.Hanabi_client.give.removeActionListener(g);
            }
            startup.GUIHanabiSystem.Hanabi_client.give.addActionListener(new GiveColorActionListener(playerid));
        }
    }
}

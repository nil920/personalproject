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
        startup.GUIHanabiSystem.Hanabiclient.setcardrankbuttonDisplay(false);
        startup.GUIHanabiSystem.Hanabiclient.setplaydisButtonDisplay(false);
        startup.GUIHanabiSystem.Hanabiclient.colors.setVisible(true);
        startup.GUIHanabiSystem.Hanabiclient.give.setVisible(true);
        if (startup.GUIHanabiSystem.Hanabiclient.give.getActionListeners().length == 0){
            startup.GUIHanabiSystem.Hanabiclient.give.addActionListener(new GiveColorActionListener(playerid));
        }
        else {
            for (ActionListener g: startup.GUIHanabiSystem.Hanabiclient.give.getActionListeners()){
                startup.GUIHanabiSystem.Hanabiclient.give.removeActionListener(g);
            }
            startup.GUIHanabiSystem.Hanabiclient.give.addActionListener(new GiveColorActionListener(playerid));
        }
    }
}

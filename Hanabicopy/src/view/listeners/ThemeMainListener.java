package view.listeners;

import view.GamePagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ThemeMainListener extends MainListener {

    ImageIcon backgroundImage;

    ThemeMainListener(ImageIcon backgroundImage) {
            this.backgroundImage = backgroundImage; };

    public void actionPerformed(ActionEvent arg0) {
        GamePagePanel view = startup.GUIHanabiSystem.Hanabi_client;
        view.background = backgroundImage;
        view.setBoardAppearance(backgroundImage); }
}

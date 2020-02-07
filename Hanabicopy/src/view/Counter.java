package view;

import javax.swing.*;

public class Counter extends JLabel{

    public Counter() {
        this.setIcon(new ImageIcon(getClass().getResource("/ProcessTile.PNG")));
    }

    public void setSizeOnRemainingSeconds(int remainingSeconds) {
        this.setSize(3* remainingSeconds , 12);
    }
}

package view;

import javax.swing.*;

public class Message {

    public static void infoBox(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}

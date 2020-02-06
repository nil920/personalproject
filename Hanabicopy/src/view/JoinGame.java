package view;

import controller.HumanController;
import startup.GUIHanabiSystem;

import javax.swing.*;
import java.awt.*;

public class JoinGame extends JFrame {

    final int frameWidth = 700;
    final int frameHeight = 900;
    JPanel panel = new JPanel();
    public static int idField;
    public static String secretTokenField;


    public JoinGame() {

        setTitle("Hanabi");
        setLayout(null);
        setSize(frameWidth, frameHeight);
        setLocation(500, 100);
        setVisible(true);

        setLayout(new BorderLayout(100, 100));
        JLabel author = new JLabel("CMPT370-19 B2");
        author.setFont(new Font("AppleMyungjo", Font.BOLD + Font.ITALIC, 15));
        author.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add("South", author);
        getContentPane().add("East", new JLabel());
        getContentPane().add("West", new JLabel());
        getContentPane().add("Center", panel);
        panel.setLayout(new GridLayout(22,1));

        JLabel header = new JLabel("Join a Game");
        header.setFont(new Font("Helvetica", Font.BOLD, 40));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel(), new JLabel());
        panel.add(new JLabel());
        panel.add(header);

        JLabel nsid = new JLabel("NSID:");
        nsid.setFont((new Font("Time", Font.PLAIN, 25)));
        nsid.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(nsid);

        JTextField nsidField = new JTextField();
        nsidField.setFont((new Font("Time", Font.PLAIN, 25)));
        nsidField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nsidField);

        JLabel gameID = new JLabel("GAME ID");
        gameID.setFont((new Font("Time", Font.PLAIN, 25)));
        gameID.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(gameID);

        JTextField gameIdField = new JTextField();
        gameIdField.setFont((new Font("Time", Font.PLAIN, 25)));
        gameIdField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(gameIdField);

        JLabel secretToken = new JLabel("SECRET TOKEN");
        secretToken.setFont((new Font("Time", Font.PLAIN, 25)));
        secretToken.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(secretToken);

        JTextField secretTokenField = new JTextField();
        secretTokenField.setFont((new Font("Time", Font.PLAIN, 25)));
        secretTokenField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(secretTokenField);


        JCheckBox aiplay = new JCheckBox("AI player");
        aiplay.setHorizontalAlignment(SwingConstants.CENTER);
        aiplay.setFont((new Font("Time", Font.PLAIN, 25)));
        panel.add(new JLabel());
        panel.add(aiplay);

        JButton join = new JButton("JOIN");
        join.setFont(new Font("Copperplate Gothic Light", Font.ITALIC, 18));
        join.setHorizontalAlignment(SwingConstants.CENTER);



//        join action listener
        join.addActionListener(e -> {
            if(nsidField.getText().equals("")  ||
                    gameIdField.getText().equals("") || secretTokenField.getText().equals("")) {
                Message.infoBox("Error: Invalid", "Please fill in all fields.");
            }
            else {
                String nsidToSend = nsidField.getText();
                int gameIdToSend = Integer.parseInt(gameIdField.getText());
                String tokenToSend = secretTokenField.getText();

                idField = gameIdToSend;
                this.secretTokenField = secretTokenField.getText();

                if (aiplay.isSelected()){
                    GUIHanabiSystem.AI=true;
                }
                HumanController.join(nsidToSend, gameIdToSend, tokenToSend);
                dispose();
            }
        });
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(join);

        JButton back = new JButton("BACK");
        back.setFont(new Font("Copperplate Gothic Light", Font.ITALIC, 18));
        back.setHorizontalAlignment(SwingConstants.CENTER);
//        back action listener
        back.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });
        panel.add(new JLabel());
        panel.add(back);

        revalidate();
        repaint();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}

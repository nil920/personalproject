package view;

import model.Waitingroom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WaitingRoom extends JFrame {

    final int frameWidth = 400;
    final int frameHeight = 800;
    JPanel panel = new JPanel();
    private JLabel playerLeftNum;
    private JLabel gameIdLabel;
    private JLabel secretTokenLabel;


    public WaitingRoom() {

        setTitle("Hanabi");
        setLayout(null);
        setSize(frameWidth, frameHeight);
        setLocation(500, 100);
        setVisible(true);

        setLayout(new BorderLayout(10, 70));
        JLabel author = new JLabel("CMPT370-19 B2");
        author.setFont(new Font("AppleMyungjo", Font.BOLD + Font.ITALIC, 15));
        author.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add("South", author);
        getContentPane().add("East", new JLabel());
        getContentPane().add("West", new JLabel());
        getContentPane().add("Center", panel);
        panel.setLayout(new GridLayout(15,1));

        JLabel header = new JLabel("Waiting Room");
        header.setFont(new Font("Helvetica", Font.BOLD, 35));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel(), new JLabel());
        panel.add(new JLabel());
        panel.add(header);

        JLabel gameID = new JLabel("GAME ID");
        gameID.setFont((new Font("Time", Font.PLAIN, 25)));
        gameID.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(gameID);

        gameIdLabel = new JLabel();
        gameIdLabel.setFont((new Font("Time", Font.PLAIN, 25)));
        gameIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(gameIdLabel);

        JLabel secretToken = new JLabel("SECRET TOKEN");
        secretToken.setFont((new Font("Time", Font.PLAIN, 25)));
        secretToken.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(secretToken);

        secretTokenLabel = new JLabel();
        secretTokenLabel.setFont((new Font("Time", Font.PLAIN, 25)));
        secretTokenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(secretTokenLabel);

        JLabel playerLeft = new JLabel("Players Needed");
        playerLeft.setFont((new Font("Time", Font.PLAIN, 25)));
        playerLeft.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(playerLeft);

        playerLeftNum = new JLabel();
        playerLeftNum.setFont((new Font("Time", Font.PLAIN, 25)));
        playerLeftNum.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(playerLeftNum);


        JButton cancel = new JButton("CANCEL");
        cancel.setFont(new Font("QUIT Light", Font.ITALIC, 18));
        cancel.setHorizontalAlignment(SwingConstants.CENTER);
        class cancelListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
        }
        cancel.addActionListener(new cancelListener());
        panel.add(new JLabel());
        panel.add(cancel);

        revalidate();
        repaint();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // Gets called in the controller
    public void init(Waitingroom waitingroom) {
        playerLeftNum.setText(String.valueOf(waitingroom.getPlayersNeeded()));
        gameIdLabel.setText(String.valueOf(waitingroom.getGameid()));
        secretTokenLabel.setText(waitingroom.getSecretToken());
    }

    // Gets called in the controller
    public void update(Waitingroom waitingroom) {
        playerLeftNum.setText(String.valueOf(waitingroom.getPlayersNeeded()));
    }

}


package view;

import controller.*;

import javax.swing.*;
import java.awt.*;

public class CreateGame extends JFrame {

    final int frameWidth = 500;
    final int frameHeight = 700;
    JPanel panel = new JPanel();

    public CreateGame() {

        setTitle("Hanabi");
        setLayout(null);
        setSize(frameWidth, frameHeight);
        setLocation(500, 100);
        setVisible(true);

        setLayout(new BorderLayout(50, 50));
        JLabel author = new JLabel("CMPT370-19 B2");
        author.setFont(new Font("AppleMyungjo", Font.BOLD + Font.ITALIC, 15));
        author.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add("South", author);
        getContentPane().add("East", new JLabel());
        getContentPane().add("West", new JLabel());
        getContentPane().add("Center", panel);
        panel.setLayout(new GridLayout(22,1));

        JLabel header = new JLabel("Create New Game");
        header.setFont(new Font("Helvetica", Font.BOLD, 35));
        header.setHorizontalAlignment(SwingConstants.CENTER);
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



        JLabel totalPlayer = new JLabel("Total Number of Players: ");
        totalPlayer.setFont((new Font("Time", Font.PLAIN, 25)));
        totalPlayer.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(totalPlayer);

        String[] numberPlayer = {"2", "3", "4", "5"};
        JComboBox totalPlayerNum = new JComboBox(numberPlayer);
        totalPlayerNum.setFont((new Font("Time", Font.PLAIN, 25)));
        panel.add(totalPlayerNum);

        JLabel timer = new JLabel("Timer");
        timer.setFont((new Font("Time", Font.PLAIN, 25)));
        timer.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(timer);

        String[] timeOption = { "30","40","50" ,"60"};
        JComboBox timerNum = new JComboBox(timeOption);
        timerNum.setFont((new Font("Time", Font.PLAIN, 25)));
        panel.add(timerNum);

        JLabel advance = new JLabel("ADVANCE MODEL");
        advance.setFont((new Font("Time", Font.PLAIN, 25)));
        advance.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(advance);

        String[] Option = {"none", "firework"};
        JComboBox advanceCombo = new JComboBox(Option);
        advanceCombo.setFont((new Font("Time", Font.PLAIN, 25)));
        panel.add(advanceCombo);

        JButton create = new JButton("CREATE");
        create.setFont(new Font("Copperplate Gothic Light", Font.ITALIC, 18));
        create.setHorizontalAlignment(SwingConstants.CENTER);
//        create action listener
        create.addActionListener(e -> {

            if(nsidField.getText().equals("")) {
                Message.infoBox("Error: Invalid", "Please fill in all fields.");
            }
            else {
                String nsidToSend = nsidField.getText();
                int playersToSend = Integer.parseInt((String) totalPlayerNum.getSelectedItem());
                int timerToSend = Integer.parseInt((String) timerNum.getSelectedItem());
                String rainbowToSend = (String)advanceCombo.getSelectedItem();

                HumanController.create(nsidToSend, playersToSend, timerToSend, rainbowToSend);
                dispose();
            }

        });
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(create);

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


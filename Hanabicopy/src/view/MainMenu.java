package view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    final int frameWidth = 700;
    final int frameHeight = 700;
    JPanel panel = new JPanel();

    public MainMenu() {

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
        panel.setLayout(new GridLayout(9,1));

        JLabel header = new JLabel("Main Menu");
        header.setFont(new Font("Helvetica", Font.BOLD, 50));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(new JLabel());
        panel.add(header);

        JButton create = new JButton("CREATE");
        create.setFont(new Font("Copperplate Gothic Light", Font.ITALIC, 18));
        create.setHorizontalAlignment(SwingConstants.CENTER);
        create.addActionListener(e -> {
            new CreateGame().setVisible(true);
            dispose();
        });
        panel.add(new JLabel());
        panel.add(create);

        JButton join = new JButton("JOIN");
        join.setFont(new Font("Copperplate Gothic Light", Font.ITALIC, 18));
        join.setHorizontalAlignment(SwingConstants.CENTER);
        join.addActionListener(e -> {
            new JoinGame().setVisible(true);
            dispose();
        });
        panel.add(new JLabel());
        panel.add(join);


        JButton quit = new JButton("QUIT");
        quit.setFont(new Font("QUIT Light", Font.ITALIC, 18));
        quit.setHorizontalAlignment(SwingConstants.CENTER);
        quit.addActionListener(e -> {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dispose();
        });
        panel.add(new JLabel());
        panel.add(quit);

        revalidate();
        repaint();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

}

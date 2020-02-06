package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import view.listeners.*;


public class Menu extends JMenuBar{
    private final static ImageIcon imgBoard_Wooden = new ImageIcon(Menu.class.getResource("/background1.png")),
            imgBoard_Wooden1 = new ImageIcon(Menu.class.getResource("/background2.png")),
            imgBoard_Gary = new ImageIcon(Menu.class.getResource("/gary.jpg")),
            imgBoard_Firework = new ImageIcon(Menu.class.getResource("/spakler.jpg"));

    private JMenu mTheme = new JMenu("Theme"),
            mControl = new JMenu("Control");

    private JMenuItem mThemeWooden = new JMenuItem("Wooden Board"),
            mThemeWooden1 = new JMenuItem("Wooden Board1"),
            mThemeGray = new JMenuItem("Gray"),
            mThemefirework = new JMenuItem("Sparkler"),
            mExit = new JMenuItem("Exit", KeyEvent.VK_X);

    private JMenuItem[] themeMenuItemList = {mThemeWooden, mThemeWooden1,mThemeGray,mThemefirework};

    public Menu() {
        arrangeMenuItems();
        setMenuGroundColor();
        addActionListeners();
    }

    private void arrangeMenuItems() {
        mTheme.setMnemonic(KeyEvent.VK_T);
        mControl.setMnemonic(KeyEvent.VK_C);
        for (JMenuItem themeOptions : themeMenuItemList) {
            mTheme.add(themeOptions);
        }
        this.add(mControl);
        this.add(mTheme);
        mControl.add(mExit);
    }

    private void setMenuGroundColor() {
        this.setBackground(Color.black);
        mControl.setForeground(Color.white);
        mTheme.setForeground(Color.white);
    }


    private void addActionListeners() {
        mThemeWooden.addActionListener(MainListener.createThemeOptionsListener(imgBoard_Wooden));
        mThemeWooden1.addActionListener(MainListener.createThemeOptionsListener(imgBoard_Wooden1));
        mThemeGray.addActionListener(MainListener.createThemeOptionsListener(imgBoard_Gary));
        mThemefirework.addActionListener(MainListener.createThemeOptionsListener(imgBoard_Firework));
        mExit.addActionListener(MainListener.ExitListener());
    }


}

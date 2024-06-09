package viev;

import model.Board;
import viev.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameSettings extends JFrame {
    public GameSettings() {
        JLabel title = new JLabel("Enter the size of the board.It must be between 10 and 100.");

        JLabel widthLabel = new JLabel("width");
        JTextField width = new JTextField("10");
        width.setPreferredSize(new Dimension(100, 20));
        JLabel heightLabel = new JLabel("height");
        JTextField height = new JTextField("10");
        JButton start = new JButton("Start Game");


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(widthLabel);
        panel.add(width);
        panel.add(heightLabel);
        panel.add(height);


        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "quit");
        panel.getActionMap().put("quit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartScreen();
                dispose();
            }

        });


        setLayout(new GridLayout(3, 1));


        start.addActionListener(e -> {
            int widthint = 0;
            int heightint = 0;
            try {
                 widthint = Integer.parseInt((width.getText()));
                 heightint = Integer.parseInt((height.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Wrong data type", "error", JOptionPane.ERROR_MESSAGE);
            }
            if (widthint >= 10 && widthint <= 100 && heightint >= 10 && heightint <= 100) {
                new Board(widthint, heightint);
                new GameScreen();
//                Board.ShowBoard();
                this.pack();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "size must be between 10 and 100", "error", JOptionPane.ERROR_MESSAGE);
            }

        });

        add(title);
        add(panel);
        add(start);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PjatkMan");
        ImageIcon icon = new ImageIcon("player.png");
        setIconImage(icon.getImage());
        setVisible(true);
    }
}

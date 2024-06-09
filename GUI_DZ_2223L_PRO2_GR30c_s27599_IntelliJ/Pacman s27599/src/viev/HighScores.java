package viev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import model.*;

public class HighScores extends JFrame {

    public HighScores() {
        JLabel title = new JLabel("High Scores");
        Vector<Player> leaderboard = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("leaderboard.ser"))) {
            leaderboard = (Vector<Player>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(leaderboard);

        JList list = new JList(leaderboard);
        list.setFocusable(false);
        JScrollPane listScrollPane = new JScrollPane(list);



        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setFocusable(true);
                requestFocus();
                setLayout(new BorderLayout());
                add(title,BorderLayout.NORTH);
                add(listScrollPane,BorderLayout.CENTER);
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "quit");
        panel.getActionMap().put("quit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartScreen();
                dispose();
            }

        });



        this.getContentPane().add(panel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setSize(new Dimension(300, 400));
        setTitle("PjatkMan");
        ImageIcon icon = new ImageIcon("player.png");
        setIconImage(icon.getImage());
        setVisible(true);
    }
}

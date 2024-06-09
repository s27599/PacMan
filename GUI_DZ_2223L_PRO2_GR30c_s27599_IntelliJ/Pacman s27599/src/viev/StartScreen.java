package viev;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JFrame {

    public StartScreen() {

        JButton startGame = new JButton("Start Game");
        startGame.setPreferredSize(new Dimension(110, 25));
        JButton highScores = new JButton("High Scores");
        highScores.setPreferredSize(new Dimension(110, 25));
        JButton exit = new JButton("exit");
        exit.setPreferredSize(new Dimension(110, 25));
        JLabel picLabel = null;
        try {
            picLabel = new JLabel(new ImageIcon(ImageIO.read(new File("pjatkman.png"))));
        } catch (IOException e) {
            picLabel = new JLabel("brak obrazu");
        }


        JPanel panel2 = new JPanel();

        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(startGame);
        panel2.add(highScores);
        panel2.add(exit);



        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.add(picLabel, BorderLayout.WEST);


        startGame.addActionListener(e -> {
            new GameSettings();
            this.dispose();
        });

        highScores.addActionListener(e -> {
            new HighScores();
            this.dispose();
        });

        exit.addActionListener(e -> StartScreen.super.dispose());

        setLayout(new FlowLayout());
        add(panel);
        add(panel2);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ImageIcon icon = new ImageIcon("player.png");
        setIconImage(icon.getImage());

        setTitle("PjatkMan");
        setVisible(true);

    }

}

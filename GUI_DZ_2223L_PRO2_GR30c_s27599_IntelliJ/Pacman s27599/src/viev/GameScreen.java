package viev;

import controler.BoardRenderer;
import controler.Enemy;
import model.Board;
import model.BoardModel;
import model.Direction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Vector;

import controler.*;
import model.*;

import static model.Upgrades.*;


public class GameScreen extends JFrame {

    private int cellSize = 0;


    public GameScreen() {
        Integer[] col = new Integer[Board.getWidth()];
        for (int i = 0; i < col.length; i++) {
            col[i] = i;
        }
        MyTimer timer = new MyTimer();

        JTable board = new JTable(new BoardModel(Board.getBoard(), col));
        board.setShowGrid(false);
        board.setFocusable(false);

        AbstractTableModel model = (AbstractTableModel) board.getModel();

        MovingCharacter playerCharacter = new MovingCharacter(1, 1, model);
        Thread playerThread = new Thread(playerCharacter);

        BoardRenderer boardRenderer = new BoardRenderer(playerCharacter.getDirection());
        board.setDefaultRenderer(Object.class, boardRenderer);
        board.setPreferredScrollableViewportSize(board.getPreferredSize());


        //HUD
        JLabel points = new JLabel(String.valueOf(playerCharacter.getPoints()));
        JLabel timerLabel = new JLabel(timer.toString());
        JLabel hearts = new JLabel(String.valueOf(playerCharacter.getHearts()));
        JLabel upgrade = new JLabel(playerCharacter.getUpgrade().name());
//        JLabel test = new JLabel("test");

        Board.putPoints();



        for (int i = 0; i < 4; i++) {
//            System.out.println(i);
            Enemy en = new Enemy(model, playerCharacter);
            Enemies.add(en);
            EnemiesThreads.add(new Thread(en));
        }


        playerThread.start();


        //leaderboard
        Vector<Player> leaderboard = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("leaderboard.ser"))) {
            leaderboard = (Vector<Player>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            leaderboard = new Vector<>();
        }
        Thread upgradeChecker = new Thread(() -> {
            while (!Thread.interrupted()) {
                switch (playerCharacter.getUpgrade()) {
                    case Speed -> {
                        long actTime = timer.toMillis();
                        while (timer.isAlive() && timer.toMillis() < actTime + 5000 && playerCharacter.getUpgrade() != Upgrades.none) {
                            playerCharacter.setSpeed(150);
                        }
                        playerCharacter.setSpeed(250);
                        playerCharacter.setUpgrade(Upgrades.none);
                    }
                    case enemiesStop -> {
                        long actTime = timer.toMillis();
                        while (timer.isAlive() && timer.toMillis() < actTime + 5000 && playerCharacter.getUpgrade() != Upgrades.none) {
                            Enemy.setStop(true);
                        }
                        Enemy.setStop(false);
                        playerCharacter.setUpgrade(Upgrades.none);

                    }
                    case ExtraHeart -> {
                        playerCharacter.setHearts(playerCharacter.getHearts() + 1);
                        playerCharacter.setUpgrade(Upgrades.none);
                    }
                    case slowenemies -> {
                        long actTime = timer.toMillis();
                        while (timer.isAlive() && timer.toMillis() < actTime + 5000 && playerCharacter.getUpgrade() != Upgrades.none) {
                            Enemy.setSpeed(350);
                        }
                        Enemy.setSpeed(220);
                        playerCharacter.setUpgrade(Upgrades.none);

                    }
                    case invincibility -> {
                        long actTime = timer.toMillis();
                        while (timer.isAlive() && timer.toMillis() < actTime + 5000 && playerCharacter.getUpgrade() != Upgrades.none) {
                            playerCharacter.setGodMode(true);
                        }

                        playerCharacter.setGodMode(false);
                        playerCharacter.setUpgrade(Upgrades.none);
                    }
                }
            }
        });
        upgradeChecker.start();

        Vector<Player> finalLeaderboard = leaderboard;
        Thread progressChecker = new Thread(() -> {
            while (!Thread.interrupted()) {
                if (playerCharacter.checkWin()) {
                    playerThread.interrupt();
                    upgradeChecker.interrupt();

                    EnemiesThreads.interrupt();

                    timer.interrupt();
                    String player = JOptionPane.showInputDialog(this, "YOU WIN\n your time was " + timer.toString() + "\nPlease enter your nickname.", "PjatkMan", JOptionPane.QUESTION_MESSAGE);
                    finalLeaderboard.add(new Player(player, timer));
                    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("leaderboard.ser"))) {
                        outputStream.writeObject(finalLeaderboard);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "An error occurred while saving your time.", "model.Leaderboard error", JOptionPane.ERROR_MESSAGE);
                    }
//                    JOptionPane.showMessageDialog(this, "YOU WIN\n your time was " + timer.toString(), "YOU WIN", JOptionPane.INFORMATION_MESSAGE);

                    this.dispose();
                    SwingUtilities.invokeLater(() -> new StartScreen());
                    Thread.currentThread().interrupt();
                }
                if (playerCharacter.checkLoose()) {
                    playerThread.interrupt();
                    upgradeChecker.interrupt();

                    EnemiesThreads.interrupt();

                    timer.interrupt();
                    JOptionPane.showMessageDialog(this, "YOU LOSE\n ", "PjatkMan", JOptionPane.INFORMATION_MESSAGE);

                    this.dispose();
                    SwingUtilities.invokeLater(() -> new StartScreen());
                    Thread.currentThread().interrupt();
                }

            }
        });
        progressChecker.start();


        JPanel HUD = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                add(hearts);
                hearts.setText("Hearts " + String.valueOf(playerCharacter.getHearts()));
                hearts.setFont(new Font(Font.SERIF, Font.BOLD, 24));
                hearts.setForeground(Color.GREEN);

                add(points);
                points.setText("POINTS: " + String.valueOf(playerCharacter.getPoints()));
                points.setFont(new Font(Font.SERIF, Font.BOLD, 24));

                add(timerLabel);
                timerLabel.setText(timer.toString());
                timerLabel.setFont(new Font(Font.SERIF, Font.BOLD, 24));
                timerLabel.setForeground(Color.RED);


                add(upgrade);
                upgrade.setText("Actual upgrade: " + playerCharacter.getUpgrade().name());
                upgrade.setFont(new Font(Font.SERIF, Font.BOLD, 24));
                upgrade.setForeground(Color.MAGENTA);

//                add(test);
//                test.setText(String.valueOf(Board.getMaxPoints()));
                setPreferredSize(new Dimension(getRootPane().getWidth(), 50));

            }
        };


        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                setFocusable(true);
                requestFocus();
                grabFocus();

                int widthCapacity = getRootPane().getWidth() / Board.getWidth();
                int heightCapacity = ((getRootPane().getHeight() - 50) / Board.getHeight());
                cellSize = Math.min(widthCapacity, heightCapacity);

                for (int i = 0; i < board.getColumnCount(); i++) {
                    board.getColumnModel().getColumn(i).setMinWidth(cellSize);
                    board.getColumnModel().getColumn(i).setMaxWidth(cellSize);
                }



                board.setRowHeight(cellSize);
                board.setRowMargin(0);
                board.getColumnModel().setColumnMargin(0);
                add(board);
            }
        };


        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (!timer.isAlive())
                    timer.start();

                EnemiesThreads.start();

                if (keyCode == KeyEvent.VK_P && e.isControlDown() && e.isShiftDown()) {
                    //winning
                    for (int i = 0; i < Board.getBoard().length; i++) {
                        for (int j = 0; j < Board.getBoard()[i].length; j++) {
                            if(Board.getBoard()[i][j]==5){
                                Board.getBoard()[i][j] = 0;
                            }
                        }
                    }
                }
                if (keyCode == KeyEvent.VK_L && e.isControlDown() && e.isShiftDown()) {
                    if (playerCharacter.isGodMode())
                        playerCharacter.setGodMode(false);
                    else
                        playerCharacter.setGodMode(true);
                }

                switch (keyCode) {
                    case KeyEvent.VK_UP -> {
                        if (Board.getBoard()[playerCharacter.getY() - 1][playerCharacter.getX()] != 1) {
                            playerCharacter.changeDirectionUp();
                            boardRenderer.setDirection(Direction.UP);
                        }
                    }
                    case KeyEvent.VK_DOWN -> {
                        if (Board.getBoard()[playerCharacter.getY() + 1][playerCharacter.getX()] != 1) {
                            playerCharacter.changeDirectionDown();
                            boardRenderer.setDirection(Direction.DOWN);
                        }
                    }
                    case KeyEvent.VK_LEFT -> {
                        if (Board.getBoard()[playerCharacter.getY()][playerCharacter.getX() - 1] != 1) {

                            playerCharacter.changeDirectionLeft();
                            boardRenderer.setDirection(Direction.LEFT);
                        }
                    }
                    case KeyEvent.VK_RIGHT -> {
                        if (Board.getBoard()[playerCharacter.getY()][playerCharacter.getX() + 1] != 1) {

                            playerCharacter.changeDirectionRight();
                            boardRenderer.setDirection(Direction.RIGHT);
                        }

                    }
                    // }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "quit");
        panel.getActionMap().put("quit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartScreen();
                playerThread.interrupt();
                timer.interrupt();
                progressChecker.interrupt();
                upgradeChecker.interrupt();
                EnemiesThreads.interrupt();
                dispose();
            }

        });


        setLayout(new BorderLayout());

        add(panel, BorderLayout.CENTER);

        add(HUD, BorderLayout.NORTH);

        setSize(new Dimension(1920, 1018));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setTitle("PjatkMan");

        ImageIcon icon = new ImageIcon("player.png");
        setIconImage(icon.getImage());

        setVisible(true);

    }
}

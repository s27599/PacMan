package controler;

import controler.Movable;
import model.Board;
import model.Direction;

import javax.swing.table.AbstractTableModel;
import java.util.Random;

import model.*;

public class MovingCharacter implements Movable, Runnable {

    private int x;
    private int y;
    private Direction direction;
    private AbstractTableModel model;
    private int state = 2;
    private int points;

    private int hearts;
    private int speed = 250;
    private Upgrades upgrade;
    private boolean godMode;


    public MovingCharacter(int x, int y, AbstractTableModel model) {
        this.x = x;
        this.y = y;
        Board.getBoard()[y][x] = state;
        this.model = model;
        this.points = 0;
        godMode = false;
        upgrade = Upgrades.none;
        hearts = 3;
        direction = Direction.NA;
    }

    @Override
    public void moveup() {
        if (Board.getBoard()[y - 1][x] != 1) {
            Board.getBoard()[y][x] = 0;
            if (Board.getBoard()[y - 1][x] == 4) {
                if (!godMode) {
                    hearts--;
                }
            }

            if (Board.getBoard()[y - 1][x] == 6)
                checkUpgrade();
            if (Board.getBoard()[y - 1][x] == 5)
                points++;
            y -= 1;

            Board.getBoard()[y][x] = state;
            model.fireTableDataChanged();
        }

    }

    @Override
    public void moveDown() {
        if (Board.getBoard()[y + 1][x] != 1) {
            Board.getBoard()[y][x] = 0;
            if (Board.getBoard()[y + 1][x] == 4) {
                if (!godMode) {
                    hearts--;
                }
            }

            if (Board.getBoard()[y + 1][x] == 6)
                checkUpgrade();
            if (Board.getBoard()[y + 1][x] == 5)
                points++;
            y += 1;

            Board.getBoard()[y][x] = state;
            model.fireTableDataChanged();
        }

    }

    @Override
    public void moveLeft() {
        if (Board.getBoard()[y][x - 1] != 1) {
            Board.getBoard()[y][x] = 0;
            if (Board.getBoard()[y][x - 1] == 4) {
                if (!godMode) {
                    hearts--;
                }
            }

            if (Board.getBoard()[y][x - 1] == 6)
                checkUpgrade();
            if (Board.getBoard()[y][x - 1] == 5)
                points++;
            x -= 1;

            Board.getBoard()[y][x] = state;
            model.fireTableDataChanged();
        }

    }

    @Override
    public void moveRight() {
        if (Board.getBoard()[y][x + 1] != 1) {
            Board.getBoard()[y][x] = 0;
            if (Board.getBoard()[y][x + 1] == 4) {
                if (!godMode) {
                    hearts--;
                }
            }
            if (Board.getBoard()[y][x + 1] == 6)
                checkUpgrade();
            if (Board.getBoard()[y][x + 1] == 5)
                points++;
            x += 1;

            Board.getBoard()[y][x] = state;
            model.fireTableDataChanged();
        }

    }


    @Override
    public void changeDirectionUp() {
        if (Board.getBoard()[y - 1][x] != 1) {
            direction = Direction.UP;
            model.fireTableDataChanged();
        }
    }

    @Override
    public void changeDirectionDown() {
        if (Board.getBoard()[y + 1][x] != 1) {
            direction = Direction.DOWN;
            model.fireTableDataChanged();
        }
    }

    @Override
    public void changeDirectionLeft() {
        if (Board.getBoard()[y][x - 1] != 1) {
            direction = Direction.LEFT;
            model.fireTableDataChanged();
        }
    }

    @Override
    public void changeDirectionRight() {
        if (Board.getBoard()[y][x + 1] != 1) {
            direction = Direction.RIGHT;
            model.fireTableDataChanged();
        }

    }

    public boolean checkWin() {
        final boolean[] control = {false};
        Integer[][] tmpBoard = Board.getBoard();

        for (int i = 0; i < tmpBoard.length; i++) {
            for (int j = 0; j < tmpBoard[i].length; j++) {
                if (tmpBoard[i][j] == 5) {
                    control[0] = true;
                }
            }
        }
        return !control[0];
   }

    public boolean checkLoose() {
        return hearts <= 0;
    }

    public void checkUpgrade() {
        Random random = new Random();
        if (upgrade != Upgrades.none) {
            upgrade = Upgrades.none;
        }
        switch (random.nextInt(5)) {
            case 0 -> {
                upgrade = Upgrades.Speed;
            }
            case 1 -> {
                upgrade = Upgrades.slowenemies;
            }
            case 2 -> {
                upgrade = Upgrades.ExtraHeart;
            }
            case 3 -> {
                upgrade = Upgrades.enemiesStop;
            }
            case 4 -> {
                upgrade = Upgrades.invincibility;
            }
        }
    }

    public Upgrades getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Upgrades upgrade) {
        this.upgrade = upgrade;
    }

    Thread animate = new Thread(() -> {
        while (!Thread.interrupted()) {
            state = 2;
            Board.getBoard()[y][x] = 2;
            model.fireTableDataChanged();
            try {
                Thread.sleep(125);
            } catch (InterruptedException e) {

                return;
            }
            state = 3;

            Board.getBoard()[y][x] = 3;
            model.fireTableDataChanged();
            try {
                Thread.sleep(125);
            } catch (InterruptedException e) {
                return;
            }
        }

    });


    @Override
    public void run() {
        animate.start();

        while (!Thread.interrupted()) {
            if (direction != null)
                switch (direction) {
                    case RIGHT -> {
                        moveRight();
                        checkWin();

                    }
                    case LEFT -> {
                        moveLeft();
                        checkWin();

                    }
                    case UP -> {
                        moveup();
                        checkWin();

                    }
                    case DOWN -> {
                        moveDown();
                        checkWin();
                    }

                }
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                animate.interrupt();
                return;
            }


        }

    }


    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public int getHearts() {
        return hearts;
    }


    public boolean isGodMode() {
        return godMode;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

package controler;

import model.Board;
import model.Direction;
import model.Enemies;
import model.EnemiesThreads;

import javax.swing.table.AbstractTableModel;
import java.util.Random;


public class Enemy implements Movable, Runnable {

    private int x;
    private int y;
    private Direction direction;
    private AbstractTableModel model;
    private int state = 4;

    private static boolean stop;
    private MovingCharacter player;

    private int previousState;
    private static int speed;

    public Enemy(AbstractTableModel model, MovingCharacter player) {
        this.x = -1;
        this.y = -1;
        setStartingPoint();
        this.model = model;
        direction = Direction.UP;
        this.player = player;
        stop = false;
        previousState = 5;
        speed = 220;
    }




    private void setStartingPoint() {
        Random random = new Random();
        while (x < 0 && y < 0) {
            int tmpx = random.nextInt(Board.getWidth() - 2) + 1;
            int tmpy = random.nextInt(Board.getHeight() - 2) + 1;
            if (Board.getBoard()[tmpy][tmpx] == 5 || Board.getBoard()[tmpy][tmpx] == 0) {
                this.x = tmpx;
                this.y = tmpy;}
        }
        Board.getBoard()[y][x] = state;
    }

    @Override
    public void moveup() {
        if (Board.getBoard()[y - 1][x] != 1) {

            Board.getBoard()[y][x] = previousState;
            if (Board.getBoard()[y - 1][x] == 2 || Board.getBoard()[y - 1][x] == 3 && !player.isGodMode()) {
                player.setHearts(player.getHearts() - 1);
            }
            y -= 1;
            previousState = Board.getBoard()[y][x];
            if (previousState == 2 || previousState == 3 || previousState == 4) {
                previousState = 0;
            }
            Board.getBoard()[y][x] = state;
            model.fireTableDataChanged();
        }

    }

    @Override
    public void moveDown() {
        if (Board.getBoard()[y + 1][x] != 1) {
            Board.getBoard()[y][x] = previousState;
            if (Board.getBoard()[y + 1][x] == 2 || Board.getBoard()[y + 1][x] == 3 && !player.isGodMode()) {
                player.setHearts(player.getHearts() - 1);


            }
            y += 1;
            previousState = Board.getBoard()[y][x];


            if (previousState == 2 || previousState == 3 ||previousState ==4) {
                previousState = 0;
            }
            Board.getBoard()[y][x] = state;
            model.fireTableDataChanged();
        }

    }

    @Override
    public void moveLeft() {
        if (Board.getBoard()[y][x - 1] != 1) {
            Board.getBoard()[y][x] = previousState;
            if (Board.getBoard()[y][x - 1] == 2 || Board.getBoard()[y][x - 1] == 3 && !player.isGodMode())
                player.setHearts(player.getHearts() - 1);
            x -= 1;
            previousState = Board.getBoard()[y][x];
            if (previousState == 2 || previousState == 3 || previousState == 4) {
                previousState = 0;
            }
            Board.getBoard()[y][x] = state;
            model.fireTableDataChanged();
        }

    }

    @Override
    public void moveRight() {
        if (Board.getBoard()[y][x + 1] != 1) {
            Board.getBoard()[y][x] = previousState;
            if (Board.getBoard()[y][x + 1] == 2 || Board.getBoard()[y][x + 1] == 3 && !player.isGodMode())
                player.setHearts(player.getHearts() - 1);
            x += 1;
            previousState = Board.getBoard()[y][x];

            if (previousState == 2 || previousState == 3 || previousState == 4) {
                previousState = 0;
            }
            Board.getBoard()[y][x] = state;
            model.fireTableDataChanged();
        }

    }

    @Override
    public void changeDirectionUp() {

        if (Board.getBoard()[y - 1][x] != 1) {
            direction = Direction.UP;
        }
    }

    @Override
    public void changeDirectionDown() {
        if (Board.getBoard()[y + 1][x] != 1) {
            direction = Direction.DOWN;
        }
    }

    @Override
    public void changeDirectionLeft() {
        if (Board.getBoard()[y][x - 1] != 1) {
            direction = Direction.LEFT;
        }

    }

    @Override
    public void changeDirectionRight() {
        if (Board.getBoard()[y][x + 1] != 1) {
            direction = Direction.RIGHT;
        }

    }


    private boolean checkCrossing() {
        int check = 0;
        if (Board.getBoard()[y + 1][x] == 1)
            check++;
        if (Board.getBoard()[y - 1][x] == 1)
            check++;
        if (Board.getBoard()[y][x + 1] == 1)
            check++;
        if (Board.getBoard()[y][x - 1] == 1)
            check++;
        if (Board.getBoard()[y + 1][x] == 1 && Board.getBoard()[y][x + 1] == 1 ||
                Board.getBoard()[y - 1][x] == 1 && Board.getBoard()[y][x + 1] == 1 ||
                Board.getBoard()[y - 1][x] == 1 && Board.getBoard()[y][x - 1] == 1 ||
                Board.getBoard()[y + 1][x] == 1 && Board.getBoard()[y][x - 1] == 1) {
            return true;
        }
        return check <= 1;
    }

    private Direction oposite(Direction direction) {
        switch (direction) {
            case LEFT -> {
                return Direction.RIGHT;
            }
            case RIGHT -> {
                return Direction.LEFT;
            }
            case UP -> {
                return Direction.DOWN;
            }
            case DOWN -> {
                return Direction.UP;
            }
            default -> {
                return Direction.NA;
            }
        }
    }

    private void randomTurn() {
        Random random = new Random();
        int way = 0;
        do {
            way = random.nextInt(4);
        } while (way == oposite(direction).getValue());
        switch (way) {
            case 0 -> {
                changeDirectionRight();
            }
            case 1 -> {
                changeDirectionDown();
            }
            case 2 -> {
                changeDirectionLeft();
            }
            case 3 -> {
                changeDirectionUp();
            }

        }
    }

    Thread upgrader = new Thread(() -> {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return;
            }
            Random random = new Random();


            if (random.nextInt(4) == 1) {
                if (previousState == 5) {
                    Board.setMaxPoints(Board.getMaxPoints() - 1);
                }
                Board.getBoard()[y][x] = 6;
                previousState = 6;

            }
        }
    });


    @Override
    public void run() {
        upgrader.start();
        randomTurn();
        while (!Thread.interrupted()) {
            if (checkCrossing()) {
                randomTurn();
            }
            if (!stop) {
                if (direction != null) {
                    switch (direction) {
                        case RIGHT -> {
                            moveRight();
                            model.fireTableDataChanged();
                        }
                        case LEFT -> {
                            moveLeft();
                            model.fireTableDataChanged();
                        }
                        case UP -> {
                            moveup();
                            model.fireTableDataChanged();
                        }
                        case DOWN -> {
                            moveDown();
                            model.fireTableDataChanged();
                        }
                    }
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    upgrader.interrupt();
                    return;
                }
            }
        }
    }


    public static void setSpeed(int speed) {
        Enemy.speed = speed;
    }

    public int getPreviousState() {
        return previousState;
    }

    public static void setStop(boolean stop) {
        Enemy.stop = stop;
    }
}

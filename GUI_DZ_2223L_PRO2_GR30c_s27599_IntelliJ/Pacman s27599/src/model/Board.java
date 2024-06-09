package model;

import java.util.*;


public class Board {

    static private int width;
    static private int height;

    static private Integer[][] board;
    static private int maxPoints;

    public Board(int width, int height) {
        Board.width = width;
        Board.height = height;
        board = new Integer[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(board[i], 0);
        }
        maxPoints = 0;
//        board[1][1] = 1;
//        board[2][2] = 2;
//        board[99][99] = 1;
        makeBoard();

//        board[3][3] = 4;
//        board[9][9] = 4;


    }

    private static void makeBoard() {
        //szerokosc okregi
//        for (int j = 0; j < board.length ; j++) {
//            board[j][0] = 1;//pion
//            board[j][board[0].length-1] = 1;//pion
//
//        }
//        for (int j = 0; j < (board[0].length ); j++) {
//            board[board.length-1][j] = 1;//poziom
//            board[0][j] = 1;//poziom
//        }
//
//            int counter = 0;
//        for (int i = 2; i < board[0].length / 2; i += 2) {
//            counter = 0;
//            for (int j = i; j < board.length - i; j++) {
//                if(counter<(i%2)+1) {
//                    board[j][i] = 1;
//                    board[j][width - 1 - i] = 1;
//                counter++;
//                }
//                else counter = 0;
//                }
//        }
//counter = 0;
//        //dlugosc okregi
//        for (int i = 2; i < board.length / 2; i += 2) {
//            counter = 0;
//            for (int j = i; j < (board[0].length - i); j++) {
//                if(counter<(i%3)+1) {
//                    board[i][j] = 1;
//                    board[height - 1 - i][j] = 1;
//                    counter++;
//                }else
//                    counter=0;
//            }
//
//        }



        //szerokosc okregi

        for (int i = 0; i < board[0].length / 2; i += 2) {

            for (int j = i; j < board.length - i; j++) {
                board[j][i] = 1;
                board[j][width - 1 - i] = 1;
            }
        }

        //dlugosc okregi
        for (int i = 0; i < board.length / 2; i += 2) {
            for (int j = i; j < (board[0].length - i); j++) {
                board[i][j] = 1;
                board[height - 1 - i][j] = 1;
            }

        }
        //pion
        for (int i = 1; i < board.length - 1; i++) {

            board[i][board[0].length / 2] = 0;
            board[i][board[0].length / 3] = 0;
            board[i][(board[0].length / 3) * 2] = 0;

        }

        //poziom
        for (int i = 1; i < board[0].length - 1; i++) {
            board[board.length / 2][i] = 0;
            board[board.length / 3][i] = 0;
            board[(board.length / 3) * 2][i] = 0;
        }

        //pion
//        for (int i = 1; i < board.length - 1; i++) {
//
//            if ((board[i][(board[0].length / 2) - 1] != 1 || board[i][(board[0].length / 2) + 1] != 1)&& !(board[i][(board[0].length / 2) - 1] != 1 && board[i][(board[0].length / 2) + 1] != 1))
//                board[i][board[0].length / 2] = 0;
//            if ((board[i - 1][(board[0].length / 3) - 1] != 1 || board[i - 1][(board[0].length / 3) + 1] != 1)&& !(board[i - 1][(board[0].length / 3) - 1] != 1 && board[i - 1][(board[0].length / 3) + 1] != 1))
//                board[i][board[0].length / 3] = 0;
//            if ((board[i - 1][((board[0].length / 3) * 2)-1] != 1|| board[i - 1][((board[0].length / 3) * 2)+1] != 1) && !(board[i - 1][((board[0].length / 3) * 2)-1] != 1 && board[i - 1][((board[0].length / 3) * 2)+1] != 1))
//                board[i][(board[0].length / 3) * 2] = 0;
//
//        }

//        //poziom
//        for (int i = 1; i < board[0].length - 1; i++) {
//            board[board.length / 2][i] = 0;
//            board[board.length / 3][i] = 0;
//            board[(board.length / 3) * 2][i] = 0;
//        }


    }

    public static void putPoints() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 5;
                    maxPoints++;
                }
            }
        }
    }


    public static int getMaxPoints() {
        return maxPoints;
    }

    public static Integer[][] getBoard() {
        return board;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setMaxPoints(int maxPoints) {
        Board.maxPoints = maxPoints;
    }

    public static void ShowBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

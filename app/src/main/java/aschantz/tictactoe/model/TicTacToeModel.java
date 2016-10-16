package aschantz.tictactoe.model;

import android.util.Log;

/**
 * Created by aschantz on 9/22/16.
 * Contains the logic of the application -- all the data
 * will store an integer array with the fields
 */

public class TicTacToeModel {

    private static TicTacToeModel instance = null;

    private TicTacToeModel(){
    }

    public static TicTacToeModel getInstance() {
        if (instance == null) {
            instance = new TicTacToeModel();
        }
        return instance;
    }

    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;

    private short[][] model = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    private short nextPlayer = CIRCLE;

    public short getFieldContent(int x, int y) {
        return model[x][y];
    }

    public void setFieldContet(int x, int y, short player) {
        model[x][y]=player;
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

    public void changeNextPlayer() {
        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;

        /*if (nextPlayer == CIRCLE) {
            nextPlayer = CROSS;
        } else {
            nextPlayer = CIRCLE;
        }*/
    }

    public void resetModel() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = EMPTY;
            }
        }
        nextPlayer = CIRCLE;
    }

    public int checkWinner() {
        int winner = 0;

        //check horizontal
        for (int i = 0; i != 3; ++i) {
            if(model[i][0] == 1 && model[i][1] == 1 && model[i][2] == 1){
                winner = 1;
            }
            if(model[i][0] == 2 && model[i][1] == 2 && model[i][2] == 2){
                winner = 2;
            }
        }

        //check vertical
        for (int i = 0; i!=3; ++i) {
            if(model[0][i] == 1 && model[1][i] == 1 && model[2][i] == 1){
                winner = 1;
            }
            if(model[0][i] == 2 && model[1][i] == 2 && model[2][i] == 2){
                winner = 2;
            }
        }

        //check diagnol
        if(model[0][0] == 1 && model[1][1] == 1 && model[2][2] == 1){
            winner = 1;
        }
        if(model[0][0] == 2 && model[1][1] == 2 && model[2][2] == 2){
            winner = 2;
        }
        if(model[0][2] == 1 && model[1][1] == 1 && model[2][0] == 1){
            winner = 1;
        }
        if(model[0][2] == 2 && model[1][1] == 2 && model[2][0] == 2){
            winner = 2;
        }

        return winner;
    }



}

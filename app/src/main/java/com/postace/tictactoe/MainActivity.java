package com.postace.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 1;           // 1 = yellow, 2 = red
    boolean gameOver = false;       // game over when full board or some one won
    int[] boardState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8},
                            {0,3,6}, {1,4,7},{2,5,8}, {0,4,7}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // handle event when touch the cell
    public void dropIn(View view) {

        ImageView image = (ImageView) view;
        // get state at each position
        int playerState = Integer.parseInt(image.getTag().toString());
        // player info
        String playerName = null;
        switch (activePlayer) {
            case 1:
                playerName = "Yellow"; break;
            case 2:
                playerName = "Red"; break;
        }

        // image has not set
        if (image.getDrawable() == null && (!gameOver)) {

            boardState[playerState] = activePlayer;
            // set image for each player each turn
            if (activePlayer == 1) {
                image.setImageResource(R.drawable.yellow);
                activePlayer = 2;
            } else if (activePlayer == 2){
                image.setImageResource(R.drawable.red);
                activePlayer = 1;
            }
            // make the image disappear
            image.setAlpha(0f);

            for (int[] winPos : winningPositions) {
                // someone won the game
                if (boardState[winPos[0]] == boardState[winPos[1]] &&
                        boardState[winPos[1]] == boardState[winPos[2]] &&
                        boardState[winPos[2]] != 0) {
                    gameOver = true;
                    String message = "Player " + playerName + " has won!\nCongratulations!";
                    display(message);
                }

                boolean boardFull = true;
                for (int countState: boardState) {
                    if (countState == 0) boardFull = false;
                }
                // if the board already full
                if (boardFull) {
                    gameOver = true;
                    String message = "Board are already full!";
                    display(message);
                }
            }

            image.animate().alpha(1f).rotation(720f).setDuration(300);
        }

    }

    // reset board game
    public void playAgain(View view) {

        // Clear the board game
        GridLayout layout = (GridLayout) findViewById(R.id.layout_board_game);
        for (int i=0; i<layout.getChildCount(); i++) {
            ((ImageView)layout.getChildAt(i)).setImageResource(0);
        }

        // new game so game is not over
        gameOver = false;

        // set default active player
        activePlayer = 1;

        // reset board game
        for (int i=0;i<boardState.length;i++) {
            boardState[i] = 0;
        }

        // disable popup
        LinearLayout layout_popup = (LinearLayout) findViewById(R.id.layout_popup);
        layout_popup.setVisibility(View.INVISIBLE);
    }

    // display message when someone won or game over
    public void display(String message) {
        LinearLayout layout_popup = (LinearLayout) findViewById(R.id.layout_popup);
        layout_popup.setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.popup_message);
        textView.setText(message);
    }

}

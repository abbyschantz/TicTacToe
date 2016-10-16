package aschantz.tictactoe;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import aschantz.tictactoe.view.TicTacToeView;


public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutContent;
    private TicTacToeView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

        gameView = (TicTacToeView) findViewById(R.id.gameView);

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.restartGame();
            }
        });

        //add shimmer animation to text
        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) findViewById(
                R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();

    }

    public void showTooManyCirclesMessage() {
        showSnackbarMessage(getString(R.string.too_many_circles));
    }

    //displays bar at bottom of screen
    private void showSnackbarMessage(String message) {
        Snackbar.make(layoutContent, message, Snackbar.LENGTH_LONG).
                setAction(getString(R.string.action_clear), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //clears all the circles
                        gameView.restartGame();
                    }
                }).show();
    }

    //message that tells the player its his turn
    public void showSimpleSnackbarMessage(String message) {
        Snackbar.make(layoutContent, message, Snackbar.LENGTH_LONG).show();
    }

    //WINNER MESSAGE
    public void showWinnerSnackbarMessage(String message) {
        Snackbar.make(layoutContent, message, Snackbar.LENGTH_LONG).show();
    }
}



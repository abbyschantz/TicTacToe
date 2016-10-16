package aschantz.tictactoe.view;

/**
 * contains the view of the ticTacToe board.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import aschantz.tictactoe.MainActivity;
import aschantz.tictactoe.R;
import aschantz.tictactoe.model.TicTacToeModel;

public class TicTacToeView extends View {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintText;
    private Paint paintCircle;
    private Paint paintCross;
    private Bitmap background;


    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.space);

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLUE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintText = new Paint();
        paintText.setColor(Color.RED);
        paintText.setTextSize(80);

        paintCircle = new Paint();
        paintCircle.setColor(Color.RED);
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setStrokeWidth(5);

        paintCross = new Paint();
        paintCross.setColor(Color.BLUE);
        paintCross.setStyle(Paint.Style.STROKE);
        paintCross.setStrokeWidth(5);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        background = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), false);

        //*****changes the size of the text
        //paintText.setTextSize(getWidth()/2);
        //*****USING BOUNDS
        //paintText.getTextBounds("HELLO", ...);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //black background
        //canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        //image background
        //THIS CODE WOULD REDRAW A NEW BITMAP EVERY TIME YOU LOAD THE SCREEN so move previous line up with variable
        //Bitmap backGround = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.space);
        canvas.drawBitmap(background, 0, 0, null);

        canvas.drawText("HELLO AIT", 20, 200, paintText);

        drawGameField(canvas);
        
        drawPlayers(canvas);
    }

    /**
     * draws the Xs and Os within the square
     */
    private void drawPlayers(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getFieldContent(i, j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 6 - 2;

                    canvas.drawCircle(centerX, centerY, radius, paintCircle);

                    // draw a cross in the field

                } else if (TicTacToeModel.getInstance().getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3, j * getHeight() / 3,
                            (i + 1) * getWidth() / 3,
                            (j + 1) * getHeight() / 3, paintCross);

                    canvas.drawLine((i + 1) * getWidth() / 3, j * getHeight() / 3,
                            i * getWidth() / 3, (j + 1) * getHeight() / 3, paintCross);
                }
            }
        }
    }

    /**
     * draws the game board
     */
    private void drawGameField(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // two horizontal lines
        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 3, getWidth(),
                2 * getHeight() / 3, paintLine);

        // two vertical lines
        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(),
                paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("TAG_DRAW", "x: " + event.getX() + " # y: " + event.getY()); //print out to terminal

            int tX = ((int) event.getX()) / (getWidth() / 3); //makes the coordinate correspond to a box
            int tY = ((int) event.getY()) / (getHeight() / 3);

            //if its empty, draw it
            if (tX<3 && tY<3 && TicTacToeModel.getInstance().
                    getFieldContent(tX,tY) == TicTacToeModel.EMPTY) {

                TicTacToeModel.getInstance().setFieldContet(tX,
                        tY, TicTacToeModel.getInstance().getNextPlayer());
                TicTacToeModel.getInstance().changeNextPlayer();

                if(TicTacToeModel.getInstance().checkWinner() == 0){
                    showNextPlayerMessage();
                } else {
                    showWinnerMessage(TicTacToeModel.getInstance().checkWinner());
                }

//                TicTacToeModel.getInstance().checkWinner();
//                showWinnerMessage(TicTacToeModel.getInstance().checkWinner());
                Log.d("CHECK_WINNER", Integer.toString(TicTacToeModel.getInstance().checkWinner()));

//                showNextPlayerMessage();

                invalidate(); //restarts onDraw
            }
        }

        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    /**
     * restart the game
     */
    public void restartGame() {
        TicTacToeModel.getInstance().resetModel();
        invalidate();
    }

    /**
     * displays a message for the next player at bottom bar of screens
     */
    private void showNextPlayerMessage() {

        String next = (TicTacToeModel.getInstance().getNextPlayer()
                == TicTacToeModel.CIRCLE) ? "O" : "X";

        ((MainActivity)getContext()).showSimpleSnackbarMessage(
                getContext().getString(R.string.text_next_player, next)
        );
    }

    private void showWinnerMessage(int winner) {
        String won = (winner == 1) ? "O" : "X";

        ((MainActivity)getContext()).showWinnerSnackbarMessage(
                getContext().getString(R.string.winner_is, won)
        );

    }

    //CLOCK
    Long start = 0L;
    public void startClock() {
        start = SystemClock.uptimeMillis();
    }

}

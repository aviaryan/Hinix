package in.aviaryan.hinix;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private GameBoard gameBoard;

    private TableLayout tableLayout;
    private Set<String> userWordSet = new HashSet<String>();
    private String currentWord = "";
    private ArrayList<String> coordsPassed = new ArrayList<>();
    private TextView user_current;
    private TextView computer;
    private TextView userScore;
    private String LOG_TAG = "log";

    private int NUM_ROWS=8;
    private  int NUM_COLS=8;
    private int fontSize=18;
    private int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startScreen.progressDialog.dismiss();
        user_current=(TextView) findViewById(R.id.current_word);
        computer=(TextView) findViewById(R.id.max);
        userScore=(TextView) findViewById(R.id.current);

        Intent mIntent = getIntent();
        int Level = mIntent.getIntExtra("Level", 1);
        Log.e("Level", Level + "");
        switch (Level % 4) {
            case 1:
                NUM_COLS =4;
                NUM_ROWS=4;
                break;
            case 2:
                NUM_COLS =5;
                NUM_ROWS=5;
                break;
            case 3:
                NUM_COLS =6;
                NUM_ROWS=6;
                break;
        }
        initBoard();
        Log.e(LOG_TAG, "init done");
        gameBoard.makeBoard(NUM_ROWS,NUM_COLS);
        tableLayout = (TableLayout) findViewById(R.id.grid);
        float tableHeight = tableLayout.getLayoutParams().height;
        float tableWidth = tableLayout.getLayoutParams().width;
        float tableHeightDP = convertPixelsToDp(tableHeight, getApplicationContext());
        float tableWidthDP = convertPixelsToDp(tableWidth, getApplicationContext());
        float tileHeight = tableHeightDP / (NUM_ROWS + 0.5f);
        float tileWidth = tableWidthDP / (NUM_COLS + 1);
        Log.e("height:", tableHeightDP + "");
        Log.e("Wi", tableWidthDP + "");
        Log.e("tileHeight", tileHeight + "");

        if (NUM_ROWS <= 3 && NUM_COLS <= 3)
            fontSize = 24;
        else if (NUM_ROWS <= 6 && NUM_COLS <= 6)
            fontSize = 18;
        else
            fontSize = 16;


        for (int i = 0; i < NUM_ROWS; i++) {
            // Make TR
            final TableRow tr = new TableRow(this);
            tr.setId(100 + i);
            tr.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
            for (int j = 0; j < NUM_COLS; j++) {
                // Make TV to hold the details
                final TextView charTile = new TextView(this);
                charTile.setHeight((int) dpToPixel(tileHeight));
                charTile.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                charTile.setWidth((int) dpToPixel(tileWidth));
                //charTile.setPadding(8,8,8,8);
                charTile.setElevation(10);
                charTile.setBackground(getDrawable(R.drawable.my_border));
                //charTile.setBackgroundColor(Color.GREEN);
                charTile.setId(NUM_ROWS * i + j);
                charTile.setTypeface(null, Typeface.BOLD);
                charTile.setText(gameBoard.chars[i][j]+"");
                charTile.setTextColor(Color.parseColor("#FFFFFF"));
                charTile.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                // text view listeners
                charTile.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            handleTouch((TextView) v);
                            return true;
                        }
                        return false;
                    }
                });

                charTile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // this is needed for onTouch to work
                    }
                });

                tr.addView(charTile);
            }
            // table row ends
            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
        }
        // loop ends
        computer.setText(gameBoard.getComputerScore()+"");
    }

    public int fetchId(int row, int col) {
        return row*NUM_ROWS + col;
    }

    private void handleTouch(TextView tv) {
        int id = tv.getId();
        int x = id / NUM_ROWS;
        int y = id % NUM_ROWS;
        String str = x + " " + y;
        if (coordsPassed.contains(str)){
            // if already touched check and if the position of this tile is the latest in the coordsPassed arraylist
            // if yes the clickBack else just return
            if(coordsPassed.indexOf(str) == coordsPassed.size()-1) {
                clickBack(tv);
            }
        } else {
            // get last tile
            boolean condition;
            if (coordsPassed.size() > 0) {
                int[] ids = coordsFromStr(coordsPassed.get(coordsPassed.size() - 1));
                condition = (Math.abs(ids[0] - x) <= 1) && (Math.abs(ids[1] - y) <= 1);
            } else {
                condition = true;
            }
            if (condition){
                coordsPassed.add(str);
                currentWord += tv.getText();
                tv.setBackground(getDrawable(R.drawable.new_border));
                showCurrentWord(0);
                int wordStatus = checkWord();
                if (wordStatus == 2){
                    (Toast.makeText(this, "Congrats! " + currentWord + " is a valid word", Toast.LENGTH_SHORT)).show();
                    processCorrectWord();
                } else {
                    showCurrentWord(wordStatus);
                }
            }
        }
    }

    private void showCurrentWord(int status){
        int color;
        if (status == 1)
            color = R.color.colorCurrentWordFaded;
        else if (status == 2)
            color = R.color.colorCurrentWordCorrect;
        else
            color = R.color.colorCurrentWordDefault;
        user_current.setTextColor(getResources().getColor(color));
        user_current.setText(currentWord);
    }

    private int [] coordsFromStr(String s){
        int [] arr = new int[2];
        arr[0] = Integer.parseInt(s.split("\\s+")[0]);
        arr[1] = Integer.parseInt(s.split("\\s+")[1]);
        return arr;
    }

    public void clickBack(View view) {
        if (coordsPassed.size() > 0){
            int [] ids = coordsFromStr( coordsPassed.get(coordsPassed.size()-1) );
            coordsPassed.remove(coordsPassed.size()-1);
            currentWord = currentWord.substring(0, currentWord.length()-1);
            TextView tv = (TextView) findViewById(fetchId(ids[0], ids[1]));
            tv.setBackground(getDrawable(R.drawable.my_border));
            showCurrentWord(checkWord());
        }
    }

    public float dpToPixel(float dps){
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        float pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    private void resetAllTiles(){
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                TextView tv = (TextView) findViewById(fetchId(i, j));
                tv.setBackground(getDrawable(R.drawable.my_border));
            }
        }
    }

    private int checkWord(){
        if ("".equals(currentWord))
            return 0;
        if (!userWordSet.contains(currentWord) && gameBoard.isWordOnBoard(currentWord)) {
            return 2;
        } else if (userWordSet.contains(currentWord)){
            return 1;
        } else {
            return 0;
        }
    }

    public void processCorrectWord() {
        // make ui changes
        counter += currentWord.length();
        userScore.setText("" + counter);
        userWordSet.add(currentWord);
        // reset all tiles
        resetAllTiles();
        // reset vars
        showCurrentWord(2);  // display on UI
        currentWord = "";
        coordsPassed.clear();
    }

    private void initBoard(){
        AssetManager assetManager = getAssets();
        try {
            // Thanks to
            // https://github.com/ManiacDC/TypingAid/tree/master/Wordlists
            InputStream inputStream = assetManager.open("wordlist_weighted_norvig_10k.txt");
            gameBoard = new GameBoard(inputStream);
        } catch (IOException e){
            (Toast.makeText(this, "There was a problem loading dictionary", Toast.LENGTH_LONG)).show();
        }
    }

    public void buttonShow(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set title
        alertDialogBuilder.setTitle("List of possible words (" +
                gameBoard.getPossibleWordsCount() + ")\n\n");
        String temp= "";
//        String clr = "#" + getString(0+R.color.colorCurrentWordCorrect).substring(3);
        for(String s : gameBoard.getComputerList(true)){
            if (userWordSet.contains(s))
                temp += "<font color=" + "green" + ">" + s + "</font><br>";
            else
                temp += s + "<br>";
        }

        // set dialog message
        alertDialogBuilder.setMessage(Html.fromHtml(temp));

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}

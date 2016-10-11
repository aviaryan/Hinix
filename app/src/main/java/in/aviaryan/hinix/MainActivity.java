package in.aviaryan.hinix;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private int NUM_ROWS=4;
    private  int NUM_COLS=4;
    private int fontSize=18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = (TableLayout) findViewById(R.id.grid);
       int tableHeight= tableLayout.getLayoutParams().height;
        int tableWidth= tableLayout.getLayoutParams().width;
        int tableHeightDP=(int)convertPixelsToDp(tableHeight,getApplicationContext());
        int tableWidthDP=(int)convertPixelsToDp(tableWidth,getApplicationContext());
        int tileHeight=tableHeightDP/(NUM_ROWS+2);
        int tileWidth =tableWidthDP/(NUM_COLS+2);
        Log.e("height:",tableHeightDP+"");
        Log.e("Wi",tableWidthDP+"");
        if(NUM_ROWS<=3 && NUM_COLS <=3)
            fontSize=24;
        else
        if(NUM_ROWS<=6 && NUM_COLS <=6)
            fontSize=18;
        else
        fontSize=16;


        for (int i = 0; i <NUM_ROWS; i++) {
            // Make TR
            TableRow tr = new TableRow(this);
            tr.setId(100 + i);
            tr.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < NUM_COLS; j++) {
                // Make TV to hold the details
                final TextView charTile = new TextView(this);
                charTile.setHeight(dpToPixel(tileHeight));
                charTile.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
                charTile.setWidth(dpToPixel(tileWidth));
                charTile.setPadding(8,8,8,8);
                charTile.setElevation(10);
                charTile.setBackground(getDrawable(R.drawable.my_border));
                charTile.setId(NUM_ROWS * i + j);
                charTile.setTypeface(null, Typeface.BOLD);
                charTile.setText("A");
                charTile.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tr.addView(charTile);
                charTile.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        charTile.setBackground(getDrawable(R.drawable.new_border));
                        return false;
                    }
                });

            }
            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        }
    }
    public int dpToPixel(int dps){
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

}

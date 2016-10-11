package in.aviaryan.hinix;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private int NUM_ROWS=4;
    private  int NUM_COLS=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = (TableLayout) findViewById(R.id.grid);
        TextView tv = null;
        ViewGroup tvLayParams = null;

        for (int i = 0; i <NUM_ROWS; i++) {
            // Make TR
            TableRow tr = new TableRow(this);
            tr.setId(100 + i);
            tr.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < NUM_COLS; j++) {
                // Make TF to hold the details
                TextView charTile = new TextView(this);
                charTile.setHeight(50);
                charTile.setWidth(50);
                charTile.setPadding(2,2,2,2);
                charTile.setBackgroundColor(Color.GREEN);
                charTile.setId(NUM_ROWS * i + j);
                charTile.setText("A");
                tr.addView(charTile);

            }
            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        }
    }
}

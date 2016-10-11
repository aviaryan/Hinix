package in.aviaryan.hinix;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initBoard(){
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words10000.txt");
            gameBoard = new GameBoard(inputStream);
        } catch (IOException e){
            (Toast.makeText(this, "There was a problem loading dictionary", Toast.LENGTH_LONG)).show();
        }
    }
}

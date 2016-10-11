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
            // Thanks to
            // https://github.com/ManiacDC/TypingAid/tree/master/Wordlists
            InputStream inputStream = assetManager.open("wordlist_weighted_norvig_10k.txt");
            gameBoard = new GameBoard(inputStream);
        } catch (IOException e){
            (Toast.makeText(this, "There was a problem loading dictionary", Toast.LENGTH_LONG)).show();
        }
    }
}

package in.aviaryan.hinix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;


public class GameBoard {
    char[][] chars;
    private HashSet<String> wordList;

    public GameBoard(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null){
            wordList.add(line.trim());
        }
    }

    public void makeBoard(int n, int m){
        chars = new char[n][m];
        // make the puzzle
    }

    boolean isWord(String s){
        // returns true if word is part of a dictionary
        return true;
    }
}

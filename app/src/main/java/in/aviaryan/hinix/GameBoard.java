package in.aviaryan.hinix;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class GameBoard {
    public char[][] chars;
    int rowCount, colCount;
    private HashSet<String> wordSet = new HashSet<>();
    private ArrayList<String> wordList = new ArrayList<>();
    private ArrayList<String> solutionList;
    Random rand = new Random();
    String LOG_TAG = "TestGB";

    public GameBoard(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null){
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
        }
    }

    /*
     * makes the game board of certain size
     */
    public void makeBoard(int n, int m){
        chars = new char[n][m];
        rowCount = n;
        colCount = m;
        int maxWordLen = (n + m), i, j;
        int dictSize = wordList.size();
        // clear the board
        for (i=0; i<n; i++)
            for (j=0; j<m; j++)
                chars[i][j] = '\0';
        // add words
        int tries = 0, pos;
        String word;
        boolean success;

        while (tries < 5){
            pos = rand.nextInt(dictSize);
            word = wordList.get(pos);
            success = false;
            // if in-elligible word, throw it
            if (word.length() == 1 || word.length() > maxWordLen)
                continue;
            // find place for word
            for (i=0; i<n; i++) {
                for (j=0; j<m; j++){
                    if (chars[i][j] == '\0' || chars[i][j] == word.charAt(0)){
                        HashSet<String> visited = new HashSet<>();
                        ArrayList<String> order = new ArrayList<>();
                        order.add(i + " " + j);
                        visited.add(i + " " + j);
//                        solutionList = new ArrayList<>();
                        success = fitWord(word.substring(1), i, j, visited, order);
                        if (success){
                            tries = 0;
                            int count = 0;
                            // add word to board
                            if (solutionList.size() != word.length()){
                                Log.v(LOG_TAG, word + " " + solutionList.toString());
                            }
                            for (String s: solutionList){
                                int x = Integer.parseInt(s.split("\\s+")[0]);
                                int y = Integer.parseInt(s.split("\\s+")[1]);
                                chars[x][y] = word.charAt(count);
                                count++;
                            }
                            break;
                        }
                    }
                }
                if (success)
                    break;
            }
            if (!success)
                tries++;
        }
        // fill remaining by anything
        for (i=0; i<n; i++) {
            for (j = 0; j < m; j++) {
                if (chars[i][j] == '\0')
                    chars[i][j] = (char) (97 + rand.nextInt(26));
            }
        }
    }

    /*
     * Try word fill
     */
    public boolean fitWord(String s, int x, int y, HashSet<String> visited, ArrayList<String> order){
        int i, j;
        // base case
        if (s.length() == 0) {
            solutionList = order;
            return true;
        }
        boolean success = false;
        for (i=x-1; i<=x+1 && i<rowCount; i++){
            for (j=y-1; j<=y+1 && j<colCount; j++){
                if (!possibleXY(i, j))
                    continue;
                if (visited.contains(i + " " + j))
                    continue;
                if (chars[i][j] == '\0' || chars[i][j] == s.charAt(0)){
                    HashSet<String> newVisited = new HashSet<String>(visited);
                    ArrayList<String> newOrder = new ArrayList<String>(order);
                    newVisited.add(i + " " + j);
                    newOrder.add(i + " " + j);
                    success = fitWord(s.substring(1), i, j, newVisited, newOrder);
                    if (success) {
//                        solutionList = newOrder;
                        break;
                    }
                }
            }
            if (success)
                break;
        }
        return success;
    }

    /*
     * Possible x y
     */
    public boolean possibleXY(int x, int y){
        return (x>=0) && (y>=0) && (x<rowCount) && (y<colCount);
    }

    /*
     * finds all the words in the grid
     */
    public void findWords(){

    }

    /*
     * true if string is a word
     */
    boolean isWord(String s){
        return wordSet.contains(s);
    }
}

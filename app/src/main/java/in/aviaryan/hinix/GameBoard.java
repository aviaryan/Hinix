package in.aviaryan.hinix;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


public class GameBoard {
    public char[][] chars;
    private int rowCount;
    private int colCount;
    private HashSet<String> wordSet = new HashSet<>();
    private ArrayList<String> wordList = new ArrayList<>();
    private ArrayList<String> solutionList;

    private ArrayList<String> computerList = new ArrayList<>();
    private HashSet<String> computerSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> computerSteps = new HashMap<>();

    private Random rand = new Random();
    private String LOG_TAG = "TestGB";

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
        HashSet<String> addedWords = new HashSet<>();
        ArrayList<String> addedWordsList = new ArrayList<>();
        rowCount = n;
        colCount = m;
        int maxWordLen = Math.max(n, m) + 1;
        int i;
        int j;
        int dictSize = wordList.size();
        // clear the board
        for (i=0; i<n; i++)
            for (j=0; j<m; j++)
                chars[i][j] = '\0';
        // add words
        int tries = 0;
        int pos = 0;
        String word;
        boolean success = false;
        int shortRun = 0;
        String badWords = "zyxq";

        while (tries < 100){
            pos = rand.nextInt(dictSize);
            word = wordList.get(pos);
            if (addedWords.contains(word)){
                continue;
            }
            success = false;
            shortRun = 0;
            // if in-elligible word, throw it
            if (word.length() < 4 || word.length() > maxWordLen)
                continue;
            for (i=0; i<badWords.length(); i++){
                if (word.contains(badWords.charAt(i) + "")){
                    break;
                }
            }
            if (i != badWords.length()) // premature exit
                continue;
            // find place for word
            for (i=0; i<n; i++) {
                for (j=0; j<m; j++){
                    if (shortRun == 0) {
                        int posArr[] = findSameCharGrid(word.charAt(0));
                        if (posArr[0] != -1) {
                            i = posArr[0]; j = posArr[1]; shortRun = 1;
                        } else {
                            shortRun = 2;
                        }
                    }
                    if (chars[i][j] == '\0' || chars[i][j] == word.charAt(0)){
                        HashSet<String> visited = new HashSet<>();
                        ArrayList<String> order = new ArrayList<>();
                        order.add(i + " " + j);
                        visited.add(i + " " + j);
                        success = fitWord(word.substring(1), i, j, visited, order);
                        if (success){
                            tries = 0;
                            int count = 0;
                            addedWords.add(word);
                            addedWordsList.add(word);
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
                    if (shortRun == 1){
                        shortRun = 2; i=0; j=-1;
                    }
                }
                if (success)
                    break;
            }
            if (!success)
                tries++;
        }
        // end things
        // fill remaining by anything
        for (i=0; i<n; i++) {
            for (j = 0; j < m; j++) {
                if (chars[i][j] == '\0')
                    chars[i][j] = (char) (97 + rand.nextInt(26));
            }
        }

        Log.v(LOG_TAG, "words added " + addedWords.size());
        Log.v(LOG_TAG, "words added " + addedWordsList.toString());
    }

    /*
     * find char 'c' in the grid
     */
    private int[] findSameCharGrid(char c){
        int arr[] = {-1, -1};
        for (int i=0; i<rowCount; i++){
            for (int j=0; j<colCount; j++){
                if (chars[i][j] == c) {
                    arr[0] = i;
                    arr[1] = j;
                }
            }
        }
        return arr;
    }

    /*
     * find char 'c' in neighbour
     */
    private int[] findSameCharNeighbour(char c, int x, int y, HashSet<String> visited){
        int arr[] = {-1, -1};
        for (int i=x-1; i<=x+1 && i<rowCount; i++) {
            for (int j = y - 1; j <= y + 1 && j < colCount; j++) {
                if (!possibleXY(i, j))
                    continue;
                if (visited.contains(i + " " + j))
                    continue;
                if (chars[i][j] == c){
                    arr[0] = i;
                    arr[1] = j;
                }
            }
        }
        return arr;
    }

    /*
     * Try word filling in the board
     */
    public boolean fitWord(String s, int x, int y, HashSet<String> visited, ArrayList<String> order){
        int i, j;
        // base case
        if (s.length() == 0) {
            solutionList = order;
            return true;
        }
        boolean success = false;
        int shortRun = 0;
        for (i=x-1; i<=x+1 && i<rowCount; i++){
            for (j=y-1; j<=y+1 && j<colCount; j++){
                if (shortRun == 0){
                    int posArr[] = findSameCharNeighbour(s.charAt(0), x, y, visited);
                    if (posArr[0] != -1) {
                        i = posArr[0]; j = posArr[1]; shortRun = 1;
                    } else {
                        shortRun = 2;
                    }
                }
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
                        break;
                    }
                }
                if (shortRun == 1){
                    shortRun = 2; i=0; j=-1;
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

    public void findWords2(){
        computerList.clear();
        computerSet.clear();
        computerSteps.clear();
        boolean ans;
        ArrayList<String> as = wordList;
        Collections.reverse(as);
        for (String word: as) {
            if (word.length() < 4 || computerSet.contains(word))
                continue;
            ans = false;
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    if (chars[i][j] == word.charAt(0))
                        ans = findWords2Util(i, j, 1, word, new HashSet<String>(), new ArrayList<String>());
                    if (ans) break;
                }
                if (ans) break;
            }
        }
    }

    private boolean findWords2Util(int x, int y, int pos, String str, HashSet<String> visited, ArrayList<String> order){
        order.add(x + " " + y);
        visited.add(x + " " + y);
        if (str.length() == pos){
            computerList.add(str);
            computerSet.add(str);
            computerSteps.put(str, order);
            return true;
        }
        // half word
        if (pos >= 4 && isWord(str.substring(0, pos)) && !computerSet.contains(str.substring(0, pos))){
            computerList.add(str.substring(0, pos));
            computerSet.add(str.substring(0, pos));
            computerSteps.put(str.substring(0, pos), order);
        }
        // main solve
        int i, j;
        boolean ans = false;
        for (i=x-1; i<=x+1 && i<rowCount; i++) {
            for (j = y - 1; j <= y + 1 && j < colCount; j++) {
                if (!possibleXY(i, j))
                    continue;
                if (visited.contains(i + " " + j) || chars[i][j] != str.charAt(pos))
                    continue;
                ans = findWords2Util(i, j, pos+1, str, new HashSet<String>(visited), new ArrayList<String>(order));
                if (ans) break;
            }
            if (ans) break;
        }
        return ans;
    }

    public int getComputerScore(){
        findWords2();
        int score = 0;
        for (String s: computerList){
            score += s.length();
        }
        return score;
    }

    /*
     * true if string can be formed on board
     */
    public boolean isWordOnBoard(String word){
        return computerSet.contains(word);
    }

    /*
     * true if string is a word from dictionary
     */
    public boolean isWord(String s){
        return wordSet.contains(s);
    }

    public ArrayList<String> getComputerList(boolean sorted) {
        ArrayList<String> resultList = computerList;
        if (sorted) {
            Collections.sort(resultList);
        }
        return resultList;
    }

    public int getPossibleWordsCount() {
        return computerList.size();
    }
}

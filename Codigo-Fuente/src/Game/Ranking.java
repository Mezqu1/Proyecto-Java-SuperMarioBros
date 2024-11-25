package Game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking {

protected List<Player> top5 = new ArrayList<>(5);
    protected static final int MAX_QUANTITY = 5;
    
    public Ranking() {
        loadTop5FromFile();
    }
    
    private void sortTop5Array() {
    	Comparator<Player> scoreDescendingComparator = Comparator.comparingInt(Player::getScore).reversed();
    	top5.sort(scoreDescendingComparator);
    }
    
    private void loadTop5FromFile() {
        top5.clear();
        try {
        	BufferedReader reader = new BufferedReader(new FileReader("Ranking.txt"));
            String currentLine = reader.readLine();
            while (currentLine != null) {
                String[] data = currentLine.split(" - ");
                top5.add(new Player(data[0], Integer.parseInt(data[1])));
                currentLine = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        sortTop5Array();
    }
    
    private void updateRankingFile() {
    	try (FileWriter fileWriter = new FileWriter("Ranking.txt")) {
             for (Player player : top5) {
                 fileWriter.write(player.getName() + " - " + player.getScore() + "\n");
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    
    public void addPlayerToTop5(String playerName, int score) {
        top5.add(new Player(playerName, score));
        sortTop5Array();
        int arraySize = top5.size();
        if (arraySize > MAX_QUANTITY) {
            top5 = top5.subList(0, MAX_QUANTITY);
        }
        updateRankingFile();
    }
    
    public List<Player> getTop5(){
    	return top5;
    }
    
    public boolean belongsToTop5(int score) {
        return top5.size() < MAX_QUANTITY || score > top5.get(MAX_QUANTITY - 1).getScore();
    }
    
}
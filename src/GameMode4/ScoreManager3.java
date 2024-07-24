package GameMode4;

import GameMode4.Board3;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager3 {

    private static final String SCORE_FILE = "data/highscoresGameMode3.txt";
    
    public static void saveScore(int score) {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            writer.write(Integer.toString(score));
            writer.newLine();
        } catch (IOException e) {
        }
    }

    public static List<Integer> getScores() {
        List<Integer> scores = new ArrayList<>();
        File file = new File(SCORE_FILE);
        if (!file.exists()) {
            return scores; // Trả về danh sách rỗng nếu tệp không tồn tại
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Integer.valueOf(line));
            }
        } catch (IOException e) {
        }

        Collections.sort(scores, Collections.reverseOrder()); // Sắp xếp điểm từ cao đến thấp
        return scores;
    }
}
    
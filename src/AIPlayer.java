import java.util.*;

public class AIPlayer {
    public static int[] findBestMove(char[][] board) {
        List<int[]> available = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    available.add(new int[]{i, j});
                }
            }
        }

        if (available.isEmpty()) return null;

        // Movimiento aleatorio entre los disponibles
        Random r = new Random();
        return available.get(r.nextInt(available.size()));
    }
}

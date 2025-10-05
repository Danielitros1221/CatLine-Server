import com.google.gson.Gson;
import io.netty.channel.Channel;

public class GameSession {
    private final Channel playerChannel;
    private final GameLogic logic = new GameLogic();
    private final Gson gson = new Gson();

    public GameSession(Channel channel) {
        this.playerChannel = channel;
    }

    public void processMove(int row, int col) {
        if (logic.isGameOver()) return;

        if (!logic.playerMove(row, col)) {
            sendMessage("Movimiento inválido");
            return;
        }

        if (!logic.isGameOver()) {
            logic.aiMove();
        }

        GameState state = new GameState(logic.getBoard(), logic.getWinner(), logic.isGameOver());
        playerChannel.writeAndFlush(gson.toJson(state) + "\n");
    }

    private void sendMessage(String text) {
        playerChannel.writeAndFlush("{\"msg\":\"" + text + "\"}\n");
    }
}

class GameState {
    char[][] board;
    char winner;
    boolean finished;

    GameState(char[][] board, char winner, boolean finished) {
        this.board = board;
        this.winner = winner;
        this.finished = finished;
    }
}

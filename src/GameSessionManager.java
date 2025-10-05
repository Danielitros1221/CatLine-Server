import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class GameSessionManager {
    private final Map<ChannelId, GameSession> sessions = new ConcurrentHashMap<>();

    public void createSession(Channel channel) {
        sessions.put(channel.id(), new GameSession(channel));
    }

    public GameSession getSession(Channel channel) {
        return sessions.get(channel.id());
    }

    public void removeSession(Channel channel) {
        sessions.remove(channel.id());
    }
}

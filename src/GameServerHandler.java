import com.google.gson.Gson;
import io.netty.channel.*;

public class GameServerHandler extends SimpleChannelInboundHandler<String> {

    private final GameSessionManager sessionManager;
    private final Gson gson = new Gson();

    public GameServerHandler(GameSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        sessionManager.createSession(ctx.channel());
        System.out.println("Cliente conectado: " + ctx.channel().id());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        GameSession session = sessionManager.getSession(ctx.channel());
        if (session == null) return;

        try {
            MoveMessage move = gson.fromJson(msg, MoveMessage.class);
            session.processMove(move.row, move.col);
        } catch (Exception e) {
            System.err.println("Error procesando mensaje: " + e.getMessage());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        sessionManager.removeSession(ctx.channel());
        System.out.println("Cliente desconectado: " + ctx.channel().id());
    }
}

class MoveMessage {
    int row;
    int col;
}

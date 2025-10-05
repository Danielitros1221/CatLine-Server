public class GameLogic {
    private char[][] board = new char[3][3];
    private char currentPlayer = 'X'; // Jugador humano
    private boolean gameOver = false;
    private char winner = ' ';

    public GameLogic() {
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            Arrays.fill(board[i], ' ');
        }
        currentPlayer = 'X';
        gameOver = false;
        winner = ' ';
    }

    public boolean playerMove(int row, int col) {
        if (gameOver || !isValidMove(row, col)) return false;
        board[row][col] = 'X';
        checkWinner();
        return true;
    }

    public boolean aiMove() {
        if (gameOver) return false;
        int[] move = AIPlayer.findBestMove(board);
        if (move != null) {
            board[move[0]][move[1]] = 'O';
            checkWinner();
            return true;
        }
        return false;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    private void checkWinner() {
        // Comprobar filas, columnas y diagonales
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                setWinner(board[i][0]);
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                setWinner(board[0][i]);
        }
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            setWinner(board[0][0]);
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            setWinner(board[0][2]);

        // Empate
        if (!gameOver && isFull()) {
            gameOver = true;
            winner = 'E'; // Empate
        }
    }

    private void setWinner(char symbol) {
        gameOver = true;
        winner = symbol;
    }

    private boolean isFull() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == ' ') return false;
        return true;
    }

    public char[][] getBoard() { return board; }
    public boolean isGameOver() { return gameOver; }
    public char getWinner() { return winner; }
}

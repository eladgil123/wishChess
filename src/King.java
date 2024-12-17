public class King extends Piece{
    public King(boolean isWhite){
        super(isWhite);
        v=isWhite ? 1000:-1000;;
    }
    public King copy(){
        King p=new King(isWhite);
        return p;
    }
    @Override
    public void reCheckMoves(Piece[][] board, int row, int column,int wkl,int bkl,int lastMove) {
        this.moves = new boolean[8][8];
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++) {
                if (row + i >= 8 || row + i < 0 || column + j >= 8 || column + j < 0) {
                    continue;
                }
                if (board[row + i][column + j] != null) {
                    if (this.isWhite != board[row + i][column + j].isWhite())
                        this.moves[row + i][column + j] = true;
                } else
                    this.moves[row + i][column + j] = true;
            }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (this.moves[i][j]) {
                    Piece temp = board[i][j];
                    board[i][j] = board[row][column];
                    board[row][column] = null;
                    if (this.isWhite) {
                        if (Utils.canBeTaken(board, i, j, true))
                            this.moves[i][j] = false;
                    } else if (Utils.canBeTaken(board, i, j, false)) {
                        this.moves[i][j] = false;
                    }
                    board[row][column] = board[i][j];
                    board[i][j] = temp;
                }
        if (this.isWhite()) {
            if (board[0][4] instanceof King&&!this.didMove && board[0][5] == null && board[0][6] == null && board[0][7] != null && !board[0][7].isDidMove()) {
                this.moves[0][6] = true;
                for (int i = 4; i < 7; i++)
                    if (Utils.canBeTaken(board, 0, i, true))
                        this.moves[0][6] = false;
            }
            if (board[0][4] instanceof King&&!this.didMove && board[0][3] == null && board[0][2] == null && board[0][1] == null && board[0][0] != null && !board[0][0].isDidMove()) {
                this.moves[0][2] = true;
                for (int i = 1; i < 5; i++)
                    if (Utils.canBeTaken(board, 0, i, true))
                        this.moves[0][2] = false;

            }
        } else if (board[7][4] instanceof King&&!this.didMove && board[7][5] == null && board[7][6] == null && board[7][7] != null && !board[7][7].isDidMove()) {
            this.moves[7][6] = true;
            for (int i = 4; i < 7; i++)
                if (Utils.canBeTaken(board, 7, i, false))
                    this.moves[7][6] = false;
        }
        if (board[7][4] instanceof King&&!this.didMove && board[7][3] == null && board[7][2] == null && board[7][1] == null && board[7][0] != null && !board[7][0].isDidMove()) {
            this.moves[7][2] = true;
            for (int i = 1; i < 5; i++)
                if (Utils.canBeTaken(board, 7, i, false))
                    this.moves[7][2] = false;

        }
    }
    public String toString(){

        if(this.isWhite)
            return "Kw";
        return "Kb";
    }
}



public class Board {
    Piece[][] board;
    int wkl, bkl, lastMove;

    public Board() {
        this.board = new Piece[8][8];
        board[0][0] = new Rook(true);
        board[0][1] = new Knight(true);
        board[0][2] = new Bishop(true);
        board[0][3] = new Queen(true);
        board[0][4] = new King(true);
        board[0][5] = new Bishop(true);
        board[0][6] = new Knight(true);
        board[0][7] = new Rook(true);
        for (int i = 0; i < 8; i++)
            board[1][i] = new Pawn(true);
        board[7][0] = new Rook(false);
        board[7][1] = new Knight(false);
        board[7][2] = new Bishop(false);
        board[7][3] = new Queen(false);
        board[7][4] = new King(false);
        board[7][5] = new Bishop(false);
        board[7][6] = new Knight(false);
        board[7][7] = new Rook(false);
        for (int i = 0; i < 8; i++)
            board[6][i] = new Pawn(false);
        this.wkl = 4;
        this.bkl = 74;
        this.lastMove = -1;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (this.board[i][j] != null)
                    this.board[i][j].reCheckMoves(this.board, i, j, this.wkl, this.bkl, this.lastMove);
    }

    public boolean move(int rs, int cs, int rt, int ct) {
        if (this.board[rs][cs] == null)
            return false;
        this.board[rs][cs].reCheckMoves(board, rs, cs, this.wkl, this.bkl, this.lastMove);
        boolean[][] m = this.board[rs][cs].getMoves();
        if (this.board[rs][cs] instanceof King) {
            if (ct == cs + 2 || ct == cs - 2) {
                if (rt == 0 && ct == 6) {
                    if (m[rt][ct]) {
                        this.board[0][4].setDidMove(true);
                        this.board[0][6] = this.board[0][4];
                        this.board[0][4] = null;
                        this.board[0][5] = this.board[0][7];
                        this.board[0][7] = null;
                        this.wkl = 6;
                    } else
                        return false;
                } else if (rt == 0 && ct == 2) {
                    if (m[rt][ct]) {
                        this.board[0][4].setDidMove(true);
                        this.board[0][2] = this.board[0][4];
                        this.board[0][4] = null;
                        this.board[0][3] = this.board[0][0];
                        this.board[0][0] = null;
                        this.wkl = 2;
                    } else
                        return false;
                } else if (rt == 7 && ct == 6) {
                    if (m[rt][ct]) {
                        this.board[7][4].setDidMove(true);
                        this.board[7][6] = this.board[7][4];
                        this.board[7][4] = null;
                        this.board[7][5] = this.board[7][7];
                        this.board[7][7] = null;
                        this.bkl = 76;
                    } else
                        return false;
                } else if (rt == 7 && ct == 2) {
                    if (m[rt][ct]) {
                        this.board[7][4].setDidMove(true);
                        this.board[7][2] = this.board[7][4];
                        this.board[7][4] = null;
                        this.board[7][3] = this.board[7][0];
                        this.board[7][0] = null;
                        this.bkl = 72;
                    } else
                        return false;
                } else
                    return false;
            } else {
                if (this.board[rs][cs].isWhite()) {
                    if (m[rt][ct]) {
                        this.board[rs][cs].setDidMove(true);
                        this.board[rt][ct] = this.board[rs][cs];
                        this.board[rs][cs] = null;
                        this.wkl = rt * 10 + ct;
                    } else
                        return false;
                } else {
                    if (m[rt][ct]) {
                        this.board[rs][cs].setDidMove(true);
                        this.board[rt][ct] = this.board[rs][cs];
                        this.board[rs][cs] = null;
                        this.bkl = rt * 10 + ct;
                    } else
                        return false;
                }
            }
        } else if (board[rs][cs] instanceof Pawn) {
            if (ct != cs && board[rt][ct] == null) {
                if (m[rt][ct]) {
                    this.board[rt][ct] = this.board[rs][cs];
                    this.board[rs][cs] = null;
                    this.board[rs][ct] = null;
                } else
                    return false;
            } else if (m[rt][ct]) {
                this.board[rs][cs].setDidMove(true);
                this.board[rt][ct] = this.board[rs][cs];
                this.board[rs][cs] = null;
            } else
                return false;
        } else {
            if (m[rt][ct]) {
                this.board[rs][cs].setDidMove(true);
                this.board[rt][ct] = this.board[rs][cs];
                this.board[rs][cs] = null;
            } else
                return false;
        }
        this.lastMove = rs * 1000 + cs * 100 + rt * 10 + ct;
        return true;
    }

    public boolean move(int rs, int cs, int rt, int ct, int prom) {

        if (this.board[rs][cs].isWhite()) {
            switch (prom) {
                case 1:
                    this.board[rt][ct] = new Queen(true);
                    break;
                case 2:
                    this.board[rt][ct] = new Rook(true);
                    break;
                case 3:
                    this.board[rt][ct] = new Knight(true);
                    break;
                case 4:
                    this.board[rt][ct] = new Bishop(true);
                    break;
            }
        } else {
            switch (prom) {
                case 1:
                    this.board[rt][ct] = new Queen(false);
                    break;
                case 2:
                    this.board[rt][ct] = new Rook(false);
                    break;
                case 3:
                    this.board[rt][ct] = new Knight(false);
                    break;
                case 4:
                    this.board[rt][ct] = new Bishop(false);
                    break;
            }
        }
            this.board[rs][cs] = null;
            this.lastMove = rs * 1000 + cs * 100 + rt * 10 + ct;
            return true;

    }
}


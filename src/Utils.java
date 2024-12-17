public class Utils {
    public static int WCanBeTaken(Piece[][] board,int row,int column,boolean isWhite){
        int r,c=column,counter=0;

        int[] ps= new int[32];
        for(int i=row+1;i<8;i++) {
            r = i;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Rook) {
                    ps[counter++]= r * 10 + c;
                    break;
                }
                else
                    break;
            }
        }
        for(int i=row-1;i>=0;i--) {
            r = i;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Rook) {
                    ps[counter++]= r * 10 + c;
                    break;
                }
                else
                    break;
            }
        }
        r=row;
        for(int i=column+1;i<8;i++) {
            c = i;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Rook) {
                    ps[counter++]= r * 10 + c;
                    break;
                }
                else
                    break;
            }
        }
        for(int i=column-1;i>=0;i--) {
            c = i;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Rook) {
                    ps[counter++]= r * 10 + c;
                    break;
                }
                else
                    break;
            }
        }
        for(int i=row+1,j=column+1;i<8&&j<8;j++,i++){
            r=i;
            c=j;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Bishop) {
                    ps[counter++]= r * 10 + c;
                    break;
                }
                else
                    break;
            }
        }
        for(int i=row+1,j=column-1;i<8&&j>=0;j--,i++){
            r=i;
            c=j;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Bishop) {
                    ps[counter++]= r * 10 + c;
                    break;
                }
                else
                    break;
            }
        }
        for(int i=row-1,j=column-1;i>=0&&j>=0;j--,i--){
            r=i;
            c=j;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Bishop) {
                    ps[counter++]= r * 10 + c;
                    break;
                }
                else
                    break;
            }
        }
        for(int i=row-1,j=column+1;i>=0&&j<8;j++,i--){
            r=i;
            c=j;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Bishop) {
                    ps[counter++]= r * 10 + c;
                    break;
                }
                else
                    break;
            }
        }
        for(int i=-1;i<2;i++)
            for(int j=-1;j<2;j++) {
                if (row + i >= 8 || row + i < 0 || column + j >= 8 || column + j < 0)
                    continue;
                if (board[row + i][column + j] != null && board[row + i][column + j].isWhite() != isWhite && board[row + i][column + j] instanceof King) {
                    ps[counter++]= (row+i) * 10 + column+j;
                }
            }
        r=row+2;
        c=column-1;
        if(r<8&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            ps[counter++]= r * 10 + c;
        c=column+1;
        if(r<8&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            ps[counter++]= r * 10 + c;
        r=row+1;
        c=column+2;
        if(r<8&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            ps[counter++]= r * 10 + c;
        r=row-1;
        if(r>=0&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            ps[counter++]= r * 10 + c;
        r=row-2;
        c=column+1;
        if(r>=0&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            ps[counter++]= r * 10 + c;
        c=column-1;
        if(r>=0&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            ps[counter++]= r * 10 + c;
        r=row-1;
        c=column-2;
        if(r>=0&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            ps[counter++]= r * 10 + c;
        r=row+1;
        if(r<8&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            ps[counter++]= r * 10 + c;
        r=row-1;
        if(isWhite)
            r=row+1;
        c=column-1;
        if(r<8&&r>=0&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Pawn)
            ps[counter++]= r * 10 + c;
        c=column+1;
        if(r<8&&r>=0&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Pawn)
            ps[counter++]= r * 10 + c;
        int min=0;
        for(int i=1;i<counter;i++){
            if(Math.abs(board[ps[i]/10][ps[i]%10].v)<Math.abs(board[ps[min]/10][ps[min]%10].v))
                min=i;
        }
        if(counter==0)
            return -1;
        return ps[min];
    }
    public static boolean canBeTaken(Piece[][] board,int row,int column,boolean isWhite){
        int r,c=column;
        for(int i=row+1;i<8;i++) {
            r = i;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Rook)
                    return true;
                else
                    break;
            }
        }
        for(int i=row-1;i>=0;i--) {
            r = i;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Rook)
                    return true;
                else
                    break;
            }
        }
        r=row;
        for(int i=column+1;i<8;i++) {
            c = i;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Rook)
                    return true;
                else
                    break;
            }
        }
        for(int i=column-1;i>=0;i--) {
            c = i;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Rook)
                    return true;
                else
                    break;
            }
        }
        for(int i=row+1,j=column+1;i<8&&j<8;j++,i++){
            r=i;
            c=j;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Bishop)
                    return true;
                else
                    break;
            }
        }
        for(int i=row+1,j=column-1;i<8&&j>=0;j--,i++){
            r=i;
            c=j;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Bishop)
                    return true;
                else
                    break;
            }
        }
        for(int i=row-1,j=column-1;i>=0&&j>=0;j--,i--){
            r=i;
            c=j;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Bishop)
                    return true;
                else
                    break;
            }
        }
        for(int i=row-1,j=column+1;i>=0&&j<8;j++,i--){
            r=i;
            c=j;
            if (board[r][c] != null) {
                if (board[r][c].isWhite() == isWhite)
                    break;
                else if (board[r][c] instanceof Queen || board[r][c] instanceof Bishop)
                    return true;
                else
                    break;
            }
        }
        for(int i=-1;i<2;i++)
            for(int j=-1;j<2;j++) {
                if (row + i >= 8 || row + i < 0 || column + j >= 8 || column + j < 0)
                    continue;
                if (board[row + i][column + j] != null && board[row + i][column + j].isWhite() != isWhite && board[row + i][column + j] instanceof King)
                    return true;
            }
        r=row+2;
        c=column-1;
        if(r<8&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            return true;
        c=column+1;
        if(r<8&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            return true;
        r=row+1;
        c=column+2;
        if(r<8&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            return true;
        r=row-1;
        if(r>=0&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            return true;
        r=row-2;
        c=column+1;
        if(r>=0&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            return true;
        c=column-1;
        if(r>=0&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            return true;
        r=row-1;
        c=column-2;
        if(r>=0&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            return true;
        r=row+1;
        if(r<8&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Knight)
            return true;
        r=row-1;
        if(isWhite)
            r=row+1;
        c=column-1;
        if(r<8&&r>=0&&c>=0&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Pawn)
            return true;
        c=column+1;
        if(r<8&&r>=0&&c<8&&board[r][c]!=null&&board[r][c].isWhite()!=isWhite&&board[r][c] instanceof Pawn)
            return true;
        return false;
    }
    public static boolean isThereMove(Board b,boolean isWhite){
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++) {
                if (b.board[i][j] != null && b.board[i][j].isWhite() == isWhite) {
                    b.board[i][j].reCheckMoves(b.board, i, j, b.wkl, b.bkl, b.lastMove);
                    boolean[][] m = b.board[i][j].getMoves();
                    for (int k = 0; k < 8; k++)
                        for (int s = 0; s < 8; s++)
                            if (m[k][s])
                                return true;
                }
            }
        return false;
    }
}

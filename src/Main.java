import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;


public class Main {
    public static void setBoardLabels(JLabel[][] labels,Board board,boolean flip){
        if(flip) {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) {
                    if (board.board[7 - i][j] != null) {
                        URL imageUrl = Main.class.getResource("images/" + board.board[7 - i][j] + ".png");
                        ImageIcon icon = new ImageIcon(imageUrl);
                        Image image = icon.getImage();
                        ImageIcon real = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                        labels[i][j].setIcon(real);
                    } else
                        labels[i][j].setIcon(null);
                }
            return;
        }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (board.board[i][7-j] != null) {
                    URL imageUrl = Main.class.getResource("images/" + board.board[i][7-j] + ".png");
                    ImageIcon icon = new ImageIcon(imageUrl);
                    Image image = icon.getImage();
                    ImageIcon real = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    labels[i][j].setIcon(real);
                } else
                    labels[i][j].setIcon(null);
            }
    }

    public static void main(String[] args) {
        startMenue sm=new startMenue();
        final boolean[] flip = {true};
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.black);
         Board b = new Board();

        JPanel mainP=new JPanel();

        MyLabel[][] bg=new MyLabel[8][8];

        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++){
                bg[i][j]=new MyLabel();
                bg[i][j].setBounds(j*100,i*100,100,100);
                bg[i][j].setBackground((i+j)%2>0 ? Color.lightGray: Color.white);
                bg[i][j].setOpaque(true);
                mainP.add(bg[i][j]);
            }
        setBoardLabels(bg,b, flip[0]);
        mainP.setPreferredSize(new Dimension(800,800));
        mainP.setLayout(null);


        frame.getContentPane().add(mainP);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        URL imageUrl = Main.class.getResource("images/logo.jpeg");
        ImageIcon icon=new ImageIcon(imageUrl);
        frame.setIconImage(icon.getImage());
        frame.setTitle("The Real Chess.com");

        frame.setVisible(true);
        int m=0;
        if(sm.isPB){
            BetterStockFish.botMove(b,true);
            m=1;
            flip[0]=m%2==0;
            setBoardLabels(bg, b, flip[0]);
        }

        int finalM = m;
        MouseListener listener =new MouseListener(){
            int lastClick =-1;
            int movesC= finalM;
            Board board=b;

            @Override
            public void mouseClicked(MouseEvent e) {
                boolean isQ = false;
                int x = e.getX() / 100, y = e.getY() / 100;
                if (flip[0])
                    y = 7 - y;
                else
                    x = 7 - x;

                if (lastClick == -1) {
                    if (board.board[y][x] == null || ((movesC % 2 == 0) != board.board[y][x].isWhite)) {
                        return;
                    }
                    lastClick = (y) * 10 + x;
                    board.board[y][x].reCheckMoves(board.board, y, x, board.wkl, board.bkl, board.lastMove);
                    boolean[][] moves = board.board[y][x].getMoves();
                    if (flip[0]) {
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 8; j++)
                                bg[7 - i][j].setDavid(moves[i][j]);
                    } else {
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 8; j++)
                                bg[i][7 - j].setDavid(moves[i][j]);
                    }
                    return;
                }
                if (board.board[lastClick / 10][lastClick % 10] instanceof Pawn && (y == 0 || y == 7) && board.board[lastClick / 10][lastClick % 10].getMoves()[y][x] && ((movesC % 2 == 0 && board.board[lastClick / 10][lastClick % 10].isWhite()) || (movesC % 2 != 0 && !board.board[lastClick / 10][lastClick % 10].isWhite()))) {
                    Queening q = new Queening(board.board[lastClick / 10][lastClick % 10].isWhite());
                    System.out.println();
                    board.move(lastClick / 10, lastClick % 10, y, x, q.prom);
                    isQ = true;

                }
                if (flip[0]) {
                    for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 8; j++)
                            bg[7 - i][j].setDavid(false);
                } else {
                    for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 8; j++)
                            bg[i][7 - j].setDavid(false);
                }
                if (!isQ) {
                    if (!board.move(lastClick / 10, lastClick % 10, y, x)) {
                        if (board.board[y][x] != null && ((movesC % 2 == 0 && board.board[y][x].isWhite()) || (movesC % 2 != 0 && !board.board[y][x].isWhite()))) {
                            lastClick = (y) * 10 + x;
                            board.board[y][x].reCheckMoves(board.board, y, x, board.wkl, board.bkl, board.lastMove);
                            boolean[][] moves = board.board[y][x].getMoves();
                            if (flip[0]) {
                                for (int i = 0; i < 8; i++)
                                    for (int j = 0; j < 8; j++)
                                        bg[7 - i][j].setDavid(moves[i][j]);
                            } else {
                                for (int i = 0; i < 8; i++)
                                    for (int j = 0; j < 8; j++)
                                        bg[i][7 - j].setDavid(moves[i][j]);
                            }
                            return;
                        }
                        lastClick = -1;
                        return;
                    }
                }
                movesC++;
                if ((movesC % 2 == 0 && !Utils.isThereMove(board, true))) {
                    if (Utils.canBeTaken(board.board, board.wkl / 10, board.wkl % 10, true)) {
                        sm.setLabel("black won");
                    } else
                        sm.setLabel("stalemate");
                    setBoardLabels(bg, board, flip[0]);
                    lastClick = -1;
                    sm.setVisible(true);
                    movesC = 0;
                    flip[0] = movesC % 2 == 0;
                    board = new Board();
                    setBoardLabels(bg, board, flip[0]);
                    if (sm.isPB) {
                        BetterStockFish.botMove(board, movesC % 2 == 0);
                        movesC++;
                        flip[0] = movesC % 2 == 0;
                        setBoardLabels(bg, board, flip[0]);
                    }
                    return;
                } else if ((movesC % 2 != 0 && !Utils.isThereMove(board, false))) {
                    if (Utils.canBeTaken(board.board, board.bkl / 10, board.bkl % 10, false)) {
                        sm.setLabel("white won");
                    } else
                        sm.setLabel("stalemate");
                    setBoardLabels(bg, board, flip[0]);
                    lastClick = -1;
                    System.out.println(movesC);
                    sm.setVisible(true);
                    movesC = 0;
                    flip[0] = movesC % 2 == 0;
                    board = new Board();
                    setBoardLabels(bg, board, flip[0]);
                    if (sm.isPB) {
                        BetterStockFish.botMove(board, movesC % 2 == 0);
                        movesC++;
                        flip[0] = movesC % 2 == 0;
                        setBoardLabels(bg, board, flip[0]);
                    }

                    return;
                }

                System.out.println(board.lastMove);

                flip[0] = movesC % 2 == 0;
                setBoardLabels(bg, board, flip[0]);
                lastClick = -1;
                frame.repaint();
                BetterStockFish.botMove(board, movesC % 2 == 0);
                movesC++;
                flip[0] = movesC % 2 == 0;
                setBoardLabels(bg, board, flip[0]);
                if ((movesC % 2 == 0 && !Utils.isThereMove(board, true))) {
                    if (Utils.canBeTaken(board.board, board.wkl / 10, board.wkl % 10, true)) {
                        sm.setLabel("black won");
                    } else
                        sm.setLabel("stalemate");
                    setBoardLabels(bg, board, flip[0]);
                    lastClick = -1;
                    sm.setVisible(true);
                    movesC = 0;
                    flip[0] = movesC % 2 == 0;
                    board = new Board();
                    setBoardLabels(bg, board, flip[0]);
                    if (sm.isPB) {
                        BetterStockFish.botMove(board, movesC % 2 == 0);
                        movesC++;
                        flip[0] = movesC % 2 == 0;
                        setBoardLabels(bg, board, flip[0]);
                    }
                    return;
                } else if ((movesC % 2 != 0 && !Utils.isThereMove(board, false))) {
                    if (Utils.canBeTaken(board.board, board.bkl / 10, board.bkl % 10, false)) {
                        sm.setLabel("white won");
                    } else
                        sm.setLabel("stalemate");
                    setBoardLabels(bg, board, flip[0]);
                    lastClick = -1;
                    System.out.println(movesC);
                    sm.setVisible(true);
                    movesC = 0;
                    flip[0] = movesC % 2 == 0;
                    board = new Board();
                    setBoardLabels(bg, board, flip[0]);
                    if (sm.isPB) {
                        BetterStockFish.botMove(board, movesC % 2 == 0);
                        movesC++;
                        flip[0] = movesC % 2 == 0;
                        setBoardLabels(bg, board, flip[0]);
                    }

                    return;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {


            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        mainP.addMouseListener(listener);
    }

}
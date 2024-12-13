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
        final boolean[] flip = {true};
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.black);
        Board board = new Board();

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
        setBoardLabels(bg,board, flip[0]);
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

        MouseListener listener =new MouseListener(){
            int lastClick =-1;
            int movesC=0;


            @Override
            public void mouseClicked(MouseEvent e) {
                int x=e.getX()/100,y=e.getY()/100;
                if(flip[0])
                    y=7-y;
                else
                    x=7-x;

                if(lastClick ==-1) {
                    if(board.board[y][x]==null|| ((movesC%2==0)!=board.board[y][x].isWhite)) {
                        return;
                    }
                    lastClick =(y)*10+x;
                    board.board[y][x].reCheckMoves(board.board, y,x,board.wkl,board.bkl,board.lastMove);
                    boolean[][] moves = board.board[y][x].getMoves();
                    if(flip[0]) {
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 8; j++)
                                bg[7 - i ][j].setDavid(moves[i][j]);
                    }else{
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 8; j++)
                                bg[i ][7-j].setDavid(moves[i][j]);
                    }
                    return;
                }
                if(board.board[lastClick/10][lastClick%10] instanceof Pawn&&(y==0||y==7)&&board.board[lastClick/10][lastClick%10].getMoves()[y][x]&&((movesC%2==0&&board.board[lastClick/10][lastClick%10].isWhite())||(movesC%2!=0&&!board.board[lastClick/10][lastClick%10].isWhite()))){
                    Queening q=new Queening(board.board[lastClick/10][lastClick%10].isWhite());
                    System.out.println();board.move(lastClick /10, lastClick %10,y,x,q.prom);
                    movesC++;
                    flip[0] =movesC%2==0;
                    Main.setBoardLabels(bg,board, flip[0]);
                    if(flip[0]) {
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 8; j++)
                                bg[7 - i ][j].setDavid(false);
                    }else{
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 8; j++)
                                bg[i ][7-j].setDavid(false);
                    }

                    System.out.println(board.lastMove);
                    lastClick =-1;

                    return;
                }
                if(flip[0]) {
                    for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 8; j++)
                            bg[7 - i ][j].setDavid(false);
                }else{
                    for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 8; j++)
                            bg[i ][7-j].setDavid(false);
                }
                if(!board.move(lastClick /10, lastClick %10,y,x)) {
                    if (board.board[y][x]!=null&&((movesC%2==0&&board.board[y][x].isWhite())||(movesC%2!=0&&!board.board[y][x].isWhite()))) {
                        lastClick =(y)*10+x;
                        board.board[y][x].reCheckMoves(board.board, y,x,board.wkl,board.bkl,board.lastMove);
                        boolean[][] moves = board.board[y][x].getMoves();
                        if(flip[0]) {
                            for (int i = 0; i < 8; i++)
                                for (int j = 0; j < 8; j++)
                                    bg[7 - i ][j].setDavid(moves[i][j]);
                        }else{
                            for (int i = 0; i < 8; i++)
                                for (int j = 0; j < 8; j++)
                                    bg[i ][7- j].setDavid(moves[i][j]);
                        }
                        return;
                    }
                    lastClick =-1;
                    return;
                }
                movesC++;
                if((movesC%2==0&&!Utils.isThereMove(board,true)))

                System.out.println(board.lastMove);

                flip[0] =movesC%2==0;
                setBoardLabels(bg,board, flip[0]);
                lastClick =-1;

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
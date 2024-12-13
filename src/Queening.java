import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.net.URL;

public class Queening extends JDialog implements ActionListener {
    int prom;
    JRadioButton queen,rook,knight,bishop;
    public Queening(boolean isWhite){
        this.setModal(true);

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(mouseLocation.x, mouseLocation.y-50);


        JPanel sec=new JPanel();
        //sec.setLayout(null);

        queen=new JRadioButton("queen");
        rook=new JRadioButton("rook   ");
        knight=new JRadioButton("knight");
        bishop=new JRadioButton("bishop");
        ButtonGroup group= new ButtonGroup();
        group.add(queen);
        group.add(rook);
        group.add(knight);
        group.add(bishop);
        URL imageUrl = Main.class.getResource("images/Q"+(isWhite ? "w":"b")+".png");
        ImageIcon icon=new ImageIcon(imageUrl);
        Image image = icon.getImage();
        ImageIcon real = new ImageIcon( image.getScaledInstance(30,30, Image.SCALE_SMOOTH));
        queen.setIcon(real);

        imageUrl = Main.class.getResource("images/R"+(isWhite ? "w":"b")+".png");
        icon=new ImageIcon(imageUrl);
        image = icon.getImage();
        real = new ImageIcon( image.getScaledInstance(30,30, Image.SCALE_SMOOTH));
        rook.setIcon(real);

        imageUrl = Main.class.getResource("images/N"+(isWhite ? "w":"b")+".png");
        icon=new ImageIcon(imageUrl);
        image = icon.getImage();
         real = new ImageIcon( image.getScaledInstance(30,30, Image.SCALE_SMOOTH));
        knight.setIcon(real);

        imageUrl = Main.class.getResource("images/B"+(isWhite ? "w":"b")+".png");
        icon=new ImageIcon(imageUrl);
        image = icon.getImage();
        real = new ImageIcon( image.getScaledInstance(30,30, Image.SCALE_SMOOTH));
        bishop.setIcon(real);

        queen.addActionListener(this);
        rook.addActionListener(this);
        bishop.addActionListener(this);
        knight.addActionListener(this);
        sec.add(queen);
        sec.add(rook);
        sec.add(knight);
        sec.add(bishop);
        System.out.println("guihd");
        this.setSize(100,240);
        this.getContentPane().add(sec);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(queen)) {
            this.prom = 1;
        } else if (source.equals(rook)) {
            this.prom = 2;
        } else if (source.equals(knight)) {
            this.prom = 3;
        } else if (source.equals(bishop)) {
            this.prom = 4;
        }
        this.setVisible(false);
    }
}

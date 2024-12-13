import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class startMenue extends JDialog implements ActionListener {
    boolean isPB=false;
    JButton start;
    JLabel l;
    JRadioButton white,black;
    public startMenue(){
        this.setModal(true);

        JPanel p = new JPanel();
         l=new JLabel("choose color");
        p.add(l);
        white = new JRadioButton("white");
        black = new JRadioButton("black");
        ButtonGroup g=new ButtonGroup();
        g.add(white);
        g.add(black);
        start = new JButton("start");
        start.addActionListener(this);

        white.addActionListener(this);
        black.addActionListener(this);
        p.add(white);
        p.add(black);
        p.add(start);

        p.setPreferredSize(new Dimension(100,120));
        this.add(p);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==black)
            isPB = true;
        else if(e.getSource()==white)
            isPB=false;
        if(e.getSource()==start)
            this.setVisible(false);
    }
    public void setLabel(String s){
        this.l.setText(s);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class choosingColor extends JDialog implements ActionListener {
    boolean flip=false;
    JRadioButton white,black;
    public choosingColor(){
        this.setModal(true);

        JPanel p = new JPanel();
        JLabel l=new JLabel("choose color");
        p.add(l);
        white = new JRadioButton("white");
        black = new JRadioButton("black");
        ButtonGroup g=new ButtonGroup();
        g.add(white);
        g.add(black);

        white.addActionListener(this);
        black.addActionListener(this);
        p.add(white);
        p.add(black);
        p.setPreferredSize(new Dimension(100,80));
        this.add(p);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==white)
            flip=true;
        this.setVisible(false);
    }
}

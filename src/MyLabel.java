import javax.swing.*;
import java.awt.*;

public  class MyLabel extends JLabel {
    boolean david;
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(david)
            g.fillOval(this.getWidth()/2-15,this.getHeight()/2-15,30,30);

    }
    public void setDavid(boolean david){
        this.david=david;
        this.repaint();
    }
}

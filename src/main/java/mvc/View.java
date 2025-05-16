package mvc;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel implements Subscriber {
    protected Model model;

    public View(Model model){
        this.model = model;
        model.subscribe(this);
    }
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
        // Additional painting logic can go here
   }
    @Override
    public void update() {
        repaint();
    }

    public void setModel(Model model) {
        this.model.unsubscribe(this);
        this.model = model;
        model.subscribe(this);
        repaint();
    }
}

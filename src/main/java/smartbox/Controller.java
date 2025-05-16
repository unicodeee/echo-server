package smartbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// all controllers implement this interface:
public abstract class Controller implements ActionListener {

    protected Container container;

    public Controller(Container container) {
        super();
        this.container = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmmd = e.getActionCommand();
        execute(cmmd);
    }

    public abstract void execute(String cmmd);

}

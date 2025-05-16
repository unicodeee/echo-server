package mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// AppPanel is the MVC controller
public class AppPanel extends JPanel implements Subscriber, ActionListener  {

    protected Model model;
    protected AppFactory factory;
    protected View view;
    protected JPanel controlPanel;
    private JFrame frame;
    public static int FRAME_WIDTH = 500;
    public static int FRAME_HEIGHT = 300;

    public AppPanel(AppFactory factory) {
        this.factory = factory;
        this.controlPanel = new JPanel();
        this.model = factory.makeModel();
        this.view = factory.makeView(model);


        Color backgroundColor = Color.PINK;
        controlPanel.setBackground(backgroundColor);


        this.setLayout(new GridLayout(1, 2));
        this.add(controlPanel);
        this.add(view);

        frame = new SafeFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }


    public void display() {
        frame.setVisible(true);
    }

    @Override
    public void update() {

    }

    public Model getModel() {
        return model;
    }

    // called by file/open and file/new
    public void setModel(Model newModel) {
        this.model.unsubscribe(this);
        this.model = newModel;
        this.model.subscribe(this);
        view.setModel(this.model);
        model.changed();
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        // add file, edit, and help menus
        JMenu fileMenu = Utilities.makeMenu("File", new String[] {"New",  "Save", "SaveAs", "Open", "Quit"}, this);
        result.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        result.add(editMenu);
        JMenu helpMenu = Utilities.makeMenu("Help", new String[] {"About", "Help"}, this);
        result.add(helpMenu);
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String cmmd = ae.getActionCommand();

            if (cmmd.equals("Save")) {
                Utilities.save(model, false);
            } else if (cmmd.equals("SaveAs")) {
                Utilities.save(model, true);
            } else if (cmmd.equals("Open")) {
                Model newModel = Utilities.open(model);
                if (newModel != null) setModel(newModel);
            } else if (cmmd.equals("New")) {
                Utilities.saveChanges(model);
                setModel(factory.makeModel());
                model.setUnsavedChanges(false);
                view.repaint();
            } else if (cmmd.equals("Quit")) {
                Utilities.saveChanges(model);
                System.exit(0);
            } else if (cmmd.equals("About")) {
                Utilities.inform(factory.about());
            } else if (cmmd.equals("Help")) {
                Utilities.inform(factory.getHelp());
            } else {
                Command command = factory.makeEditCommands(model, cmmd, ae.getSource());
                command.execute();
                repaint();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    protected void handleException(Exception e) {
        Utilities.error(e);
    }
}

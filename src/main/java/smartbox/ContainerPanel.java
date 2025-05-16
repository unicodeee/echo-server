package smartbox;

import mvc.AppFactory;
import mvc.AppPanel;
import mvc.Command;
import mvc.Model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ContainerPanel extends AppPanel {
    private JTextField nameInputField;
    private ContainerView displayView;

    public ContainerPanel(AppFactory factory) {
        super(factory);
        setLayout(new BorderLayout(20, 20));

        // Initialize the main display view
        displayView = new ContainerView(getModel());
        add(displayView, BorderLayout.CENTER);

        // Left-side vertical command button panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        for (String action : factory.getEditCommands()) {
            JButton btn = new JButton(action);
            btn.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(this, "Enter component name:", "Input", JOptionPane.PLAIN_MESSAGE);
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        Command cmd = factory.makeEditCommands(getModel(), action, input.trim());
                        runCommand(cmd);
                    } catch (Exception ex) {
                        handleException(ex);
                    }
                }
            });
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.WEST);
    }

    // Execute command with error handling
    protected void runCommand(Command cmd) {
        try {
            cmd.execute();
            displayView.update();
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    // Ensure container model is initialized
    @Override
    public void setModel(Model m) {
        super.setModel(m);
        ((Container) m).initContainer();
        displayView.setModel(m);
        displayView.update();
    }

    public static void main(String[] args) {
        AppPanel panel = new ContainerPanel(new ContainerFactory());
        panel.display();
    }
}

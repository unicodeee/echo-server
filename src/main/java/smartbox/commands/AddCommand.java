package smartbox.commands;

import mvc.Command;
import mvc.Model;
import smartbox.Container;

public class AddCommand extends Command {
    private String componentName;

    public AddCommand(Model model, Object source) {
        super(model);
        this.componentName = (String)source;
    }

    public void execute() throws Exception {
        Container container = (Container)model;
        container.addComponent(componentName);
    }
}
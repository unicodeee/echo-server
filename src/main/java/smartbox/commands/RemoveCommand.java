package smartbox.commands;

import mvc.Command;
import mvc.Model;
import smartbox.Container;

public class RemoveCommand extends Command {
    private String componentName;

    public RemoveCommand(Model model, Object source) {
        super(model);
        this.componentName = (String)source;
    }

    public void execute() throws Exception {
        Container container = (Container)model;
        container.remComponent(componentName);
    }
}
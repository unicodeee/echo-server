package smartbox.commands;

import mvc.Command;
import mvc.Model;
import smartbox.Container;

public class RunCommand extends Command {
    private String componentName;

    public RunCommand(Model model, Object source) {
        super(model);
        this.componentName = (String)source;
    }

    public void execute() throws Exception {
        Container container = (Container)model;
        container.launch(componentName);
    }
}
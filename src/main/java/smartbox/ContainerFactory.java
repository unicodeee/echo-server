package smartbox;

import mvc.AppFactory;
import mvc.Command;
import mvc.Model;
import mvc.View;
import smartbox.commands.AddCommand;
import smartbox.commands.RemoveCommand;
import smartbox.commands.RunCommand;

public class ContainerFactory implements AppFactory {
    @Override
    public Model makeModel() {
        return new Container();
    }

    @Override
    public View makeView(Model model) {
        return new ContainerView(model);
    }

    @Override
    public String getTitle() {
        return "SmartBox Container";
    }

    @Override
    public String[] getHelp() {
        return new String[] {
                "Add: Add a component to the container",
                "Remove: Remove a component from the container",
                "Run: Execute a component's main method if it implements App"
        };
    }

    @Override
    public String about() {
        return "SmartBox Container v1.0: A Component-Container framework";
    }

    @Override
    public String[] getEditCommands() {
        return new String[] {"Add", "Remove", "Run"};
    }

    @Override
    public Command makeEditCommands(Model model, String type, Object source) {
        Container container = (Container)model;
        if (type.equals("Add")) {
            return new AddCommand(model, source);
        } else if (type.equals("Remove")) {
            return new RemoveCommand(model, source);
        } else if (type.equals("Run")) {
            return new RunCommand(model, source);
        }
        return null;
    }
}
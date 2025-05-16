package simstation;

import mvc.Command;
import mvc.Model;

public class StopCommand extends Command {
    public StopCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        World world = (World) model;
        world.stopAgents();
    }
}

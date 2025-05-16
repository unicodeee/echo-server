package simstation;

import mvc.Model;
import mvc.View;

import java.awt.*;

public class WorldView extends View {
    private World world;
    private static int AGENT_SIZE = 10;
    public WorldView(Model model) {
        super(model);
        world = (World) model;
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        for (Agent a : world.getAgents()) {
            drawAgent(a, gc);
        }
    }

    public void drawAgent(Agent a, Graphics gc) {
        if (!(a instanceof ObserverAgent)) {
            gc.setColor(Color.RED);
            gc.fillOval(a.getXc(), a.getYc(), AGENT_SIZE, AGENT_SIZE);
        }
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);
        world = (World) model;
    }
}

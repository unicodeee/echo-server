package smartbox;

import mvc.Model;
import mvc.View;

import java.awt.*;
import java.util.Collection;

public class ContainerView extends View {

    private List componentsList;

    public ContainerView(Model model) {
        super(model);
        setLayout(new BorderLayout());

        componentsList = new List(10);
        this.add(componentsList, BorderLayout.CENTER);

        update();  // Initialize with any existing components
    }

    public void update() {
        componentsList.removeAll(); // Clear the list

        // Get all components from the container model
        Container container = (Container)model;
        Collection<Component> components = container.getComponents();


        System.out.println("components size: " + container.getComponents());
        // Add each component to the list
        for (Component comp : components) {
            componentsList.add(comp.getName());
        }

        componentsList.invalidate();
        componentsList.validate();
        componentsList.repaint();

//        System.out.println("componentsList: " + componentsList.invalidate(););
//        System.out.println("componentsList: " + componentsList.size());


    }
}
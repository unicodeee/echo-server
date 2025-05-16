package final_prep;

import mvc.AppPanel;
import simstation.World;
import simstation.WorldPanel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SugarScape extends World {

    private Set<SugarPod> sugarPods;
    public static int numPods = 100;
    public static int numAnts = 100;

    public SugarScape() {
        super();
        sugarPods = new HashSet<SugarPod>();
        for(int i = 0; i < numPods; i++) {
            int xc = mvc.Utilities.rng.nextInt(SIZE);
            int yc = mvc.Utilities.rng.nextInt(SIZE);
            SugarPod p = new SugarPod(xc, yc);
            sugarPods.add(p);
        }

    }

    // delegators:
    public boolean contains(SugarPod pod) { return sugarPods.contains(pod); }
    public boolean remove(SugarPod pod) { return sugarPods.remove(pod); }
    public Iterator<SugarPod> podIterator() { return sugarPods.iterator(); }

    public void populate() {
        for(int i = 0; i < numAnts; i++) {
            this.addAgent(new Ant());
        }
    }

    public static void main(String[] args) {
        AppPanel panel = new WorldPanel(new SugarScapeFactory());
        panel.display();
    }
}

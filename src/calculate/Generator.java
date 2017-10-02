package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

class Generator implements Callable<List<Edge>>, Observer {

    private KochFractal koch = new KochFractal();
    private KochManager manager;
    private EdgeEnum edge;
    private List<Edge> edges;

    public Generator(int level, KochManager manager, EdgeEnum edge)
    {
        edges = new ArrayList<>();
        koch.setLevel(level);
        this.manager = manager;
        koch.addObserver(this);
        this.edge = edge;
    }

    @Override
    public List<Edge> call() throws Exception {
        if (edge == EdgeEnum.RIGHT)
            koch.generateRightEdge();
        if (edge == EdgeEnum.LEFT)
            koch.generateLeftEdge();
        if(edge == EdgeEnum.BOTTOM)
            koch.generateBottomEdge();

        return edges;
    }

    @Override
    public void update (Observable o, Object arg) {
        edges.add((Edge)arg);
    }
}

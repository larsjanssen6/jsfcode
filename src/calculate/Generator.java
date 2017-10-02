package calculate;

import java.util.Observable;
import java.util.Observer;

class Generator extends Thread implements Observer {

    private KochFractal koch = new KochFractal();
    private KochManager manager;
    private EdgeEnum edge;

    public Generator(int level, KochManager manager, EdgeEnum edge)
    {
        koch.setLevel(level);
        this.manager = manager;
        koch.addObserver(this);
        this.edge = edge;
    }

    @Override
    public void run() {
        if (edge == EdgeEnum.RIGHT)
        koch.generateRightEdge();
        if (edge == EdgeEnum.LEFT)
        koch.generateLeftEdge();
        if(edge == EdgeEnum.BOTTOM)
        koch.generateBottomEdge();

        manager.addCount();
    }

    @Override
    public void update (Observable o, Object arg) {
        manager.addKoch((Edge)arg);
    }
}

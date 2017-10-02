package calculate;

import java.util.Observable;
import java.util.Observer;

class Generator extends Thread implements Observer {

    private KochFractal koch = new KochFractal();
    private KochManager manager;
    private int side;

    public Generator(int level, KochManager manager, int side)
    {
        koch.setLevel(level);
        this.manager = manager;
        koch.addObserver(this);
        this.side = side;
    }

    @Override
    public void run() {
        if (side == 1)
        koch.generateRightEdge();
        if (side == 2)
        koch.generateLeftEdge();
        if(side == 3)
        koch.generateBottomEdge();

        manager.addCount();
    }

    @Override
    public void update (Observable o, Object arg) {
        manager.addKoch((Edge)arg);
    }
}

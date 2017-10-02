/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;

public class KochManager {
    private JSF31KochFractalFX application;
    private ArrayList<Edge> edges;
    private int count;

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        edges = new ArrayList<>();
    }

    public synchronized void addKoch(Object arg) {
        edges.add((Edge)arg);
    }

    public void drawEdges() {
        application.clearKochPanel();

        for (Edge edge: edges) {
            application.drawEdge(edge);
        }

        application.setTextNrEdges(Integer.toString(edges.size()));
    }

    public synchronized void addCount()
    {
        count++;

        if(count == 3) {
            drawEdges();
        }
    }

    public void changeLevel(int nxt) throws InterruptedException {
        TimeStamp ts = new TimeStamp();
        count = 0;
        ts.setBegin("Start calculation");
        edges.clear();

        Thread rightEdge = new Thread(new Generator(nxt, this, 1));
        rightEdge.start();

        Thread leftEdge = new Thread(new Generator(nxt, this, 2));
        leftEdge.start();

        Thread bottomEdge = new Thread(new Generator(nxt, this, 3));
        bottomEdge.start();

        ts.setEnd();
        application.setTextCalc(ts.toString());

        ts = new TimeStamp();
        ts.setBegin("Draw edges");

        ts.setEnd();
        application.setTextDraw(ts.toString());
    }
}

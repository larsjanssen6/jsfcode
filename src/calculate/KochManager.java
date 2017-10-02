/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        TimeStamp ts = new TimeStamp();
        ts.setBegin("Calculating");

        application.clearKochPanel();

        for (Edge edge: edges) {
            application.drawEdge(edge);
        }

        ts.setEnd();
        application.setTextDraw(ts.toString());
        application.setTextNrEdges(Integer.toString(edges.size()));
    }

    public void changeLevel(int nxt) throws InterruptedException, ExecutionException {
        count = 0;
        edges.clear();

        TimeStamp ts = new TimeStamp();
        ts.setBegin("Calculating");

        Generator leftEdge = new Generator(nxt , this, EdgeEnum.LEFT);
        Generator rightEdge = new Generator(nxt , this, EdgeEnum.RIGHT);
        Generator bottomEdge = new Generator(nxt , this, EdgeEnum.BOTTOM);

        ExecutorService pool = Executors.newCachedThreadPool();

        Future<List<Edge>> featureLeftEdge = pool.submit(leftEdge);
        Future<List<Edge>> featureRightEdge = pool.submit(rightEdge);
        Future<List<Edge>> featureBottomEdge = pool.submit(bottomEdge);

        this.addEdges(featureLeftEdge.get());
        this.addEdges(featureRightEdge.get());
        this.addEdges(featureBottomEdge.get());

        pool.shutdown();

        ts.setEnd();
        application.setTextCalc(ts.toString());

        application.requestDrawEdges();
    }

    public synchronized void addEdges(List<Edge> edges)
    {
        this.edges.addAll(edges);
    }
}

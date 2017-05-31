package com.cutesys.sponsermasterlibrary.Reveal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by athira on 17/09/15.
 */
public class CurvedAnimator {

    protected List<Point> points = new ArrayList<>();

    public CurvedAnimator(float fromX, float fromY, float toX, float toY){
        points.add(new Point(fromX, fromY));
        points.add(new Point(Math.max(fromX, toX)*1.5f,
                (toY + fromY)/2,
                (toX + fromX)/2,
                Math.max(fromY, toY)*2.25f,
                toX,
                toY));
    }

    public Object[] getPoints() {
        return points.toArray();
    }
}
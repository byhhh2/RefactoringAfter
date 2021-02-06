package Model;

import java.awt.*;
public class BoardIndexData {
    public Point    p;
    public int      currentIndex;
    public int      nextIndex;
    public int      prevIndex;
    public boolean  hasOtherPath;
    public int      shortCut;

    public BoardIndexData(){ }

    public BoardIndexData(int x, int y, int current, boolean bl, int index){
        p = new Point(x,y);
        currentIndex = current;
        prevIndex = current-1;
        nextIndex = current+1;
        hasOtherPath = bl;
        shortCut = index;
    }
}

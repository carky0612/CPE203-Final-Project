public class Node {


    private Point point;
    private Node prev;
    private double h;
    private double g;
    private double f;

    public Node(Point p) {
        point = p;
    }
    public Point getPoint()
    {
        return point;
    }

    public void setPrev(Node n)
    {
        this.prev = n;
    }
    public Node getPrev()
    {
        return prev;
    }
    public void setF(double g, double h)
    {
        this.f = g + h;
    }

    public void setH(int x,int y,Point end)
    {
        this.h = Math.abs(x-end.x)+ Math.abs(y-end.y);
    }
    public void setG(double g)
    {
        this.g = g;
    }
    public double getF()
    {
        return f;
    }
    public double getH()
    {
        return h;
    }
    public double getG()
    {
        return g;
    }

    public boolean equals(Object n)
    {
        if (((Node)n).getPoint().equals(this.getPoint()))
        {
            return true;
        }
        return false;
    }

}

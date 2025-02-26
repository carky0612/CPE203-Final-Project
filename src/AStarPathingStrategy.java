import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
class Sort implements Comparator<Node>
{
    public int compare(Node o1, Node o2) {
        return (int)(o1.getF()-o1.getF());
    }
}
class AStarPathingStrategy
        implements PathingStrategy
{
    //    private double bestG;
//    private Point closest;
    Node closest;


    public Point computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        ArrayList<Node> openList = new ArrayList<>();
        List<Point> path = new LinkedList<>();

        Node strt = new Node(start);

        List<Node> visited = new ArrayList<>();
        List<Point> revPath = new ArrayList<>();

        strt.setG(0);
        strt.setH(start.x,start.y,end);
        strt.setF(0,strt.getH());
        //closest = strt;
        //path.add(start);
        openList.add(strt);
        visited.add(strt);

        closest = strt;

        while (!openList.isEmpty()) {
            List<Point> neighbors = potentialNeighbors.apply(closest.getPoint()).filter(canPassThrough).filter(p -> !p.equals(start) && !p.equals(end)).collect(Collectors.toList());
            openList.remove(closest);
//            List<Point> neighbors = potentialNeighbors.apply(closest.getPoint()).filter(canPassThrough).filter(p -> !p.equals(start) && !p.equals(end)).toList();
            //processing neighbors
            for (Point neighbor : neighbors)
            {
                Node next = new Node(neighbor);

                //G,H,F calculation
                next.setG(next.getG() + 1);
                next.setH(next.getPoint().x, next.getPoint().y, end);
                next.setF(next.getG(), next.getH());

                //if we haven't visited this next neighbor
                if (!visited.contains(next) && !openList.contains(next))
                {
                    openList.add(next);

                    next.setPrev(closest);
                    visited.add(next);

                }
                else
                {
                    for (Node same : visited)
                    {
                        if (same.equals(next) && next.getF() < same.getF())
                        {
                            same.setG(next.getG());
                            same.setH(next.getPoint().x,next.getPoint().y,end);
                            same.setF(next.getG(),next.getH());
                            same.setPrev(closest);

                        }
                    }
                }
            }
            Collections.sort(openList, new Sort());
            closest = openList.get(0);


            if (withinReach.test(closest.getPoint(), end))
            {
                while (closest.getPrev() != null)
                { path.add(0,closest.getPoint());
                    closest = closest.getPrev();
                }

                return path.get(0);
            }

        }

        return path.get(0);
    }


}

import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class Node {

    // The number written on this node
    private int value;

    // A*-related costs:
    // F: total cost;
    // G: measured cost from start;
    // H: estimated cost to finish.
//    public int f_value, g_value, h_value;
    public int cost;

    // The four neighbors. May be null for borders.
    private Node north, south, east, west;
    private ArrayList<Node> neighborsOfStartingPoint;

    // The previous node on the quickest path from the origin to here
    private Node previous;

    private static int NB_NODES_CREATED = 0;
    private int id;

    public Node(int newValue, Node newNorth, Node newEast, Node newSouth, Node newWest) {
        value = newValue;
        north = newNorth;
        south = newSouth;
        east = newEast;
        west = newWest;
        previous = null;
//        f_value = 0;
//        g_value = 0;
//        h_value = 0;
        cost = Integer.MAX_VALUE;

        neighborsOfStartingPoint = null;

        id = NB_NODES_CREATED;
        NB_NODES_CREATED++;
    }

    public Node(int newValue) {
        this(newValue, null, null, null, null);
    }

    @Override
    public String toString() {
        return "value: " + this.value + ", cost: " + cost + ", id: " + id;
    }

    public int getValue() {
        return value;
    }

    public void setPrevious(Node newPrev) {
        previous = newPrev;
    }

    public void setNeighbors(Node n, Node e, Node s, Node w) {
        north = n;
        east = e;
        south = s;
        west = w;
        neighborsOfStartingPoint = null;
    }

    public void setNeighbors(ArrayList<Node> listOfNeighbors) {
        neighborsOfStartingPoint = listOfNeighbors;
    }

    public ArrayList<Node> getNeighbors() {

        ArrayList<Node> list = new ArrayList();

        // Problems 81 and 83, and most nodes in problem 82
        if (value != Integer.MIN_VALUE) {
            if (north != null) {
                list.add(north);
            }
            if (east != null) {
                list.add(east);
            }
            if (south != null) {
                list.add(south);
            }
//        if (west != null) {
//            list.add(west);
//        }
        } else {
            // First node of problem 82:
            return neighborsOfStartingPoint;
        }
        return list;
    }

    public CardinalPoint getDirection() {
        if (previous != null) {
            if (previous.equals(north)) {
                return CardinalPoint.NORTH;
            }
            if (previous.equals(east)) {
                return CardinalPoint.EAST;
            }
            if (previous.equals(south)) {
                return CardinalPoint.SOUTH;
            }
            if (previous.equals(west)) {
                return CardinalPoint.WEST;
            }
        }
        return null;
    }

//    /**
//     * Evaluate the cost from this node to the target.
//     */
//    public void evaluateHeuristic() {
//        h_value = 0;
//    }
}

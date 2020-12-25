
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthu
 */
public class Matrix {

    private int nbLines, nbColumns;
    private Node[][] array;
    private Node startingNode;
    private Node targetNode;

    private ArrayList<Node> openList;
    private ArrayList<Node> closedList;

    private int nbSteps;

    public Matrix(String filename) {

        try {
            String folder = "C:\\Users\\arthu\\Documents\\Programmation\\Java\\ProjectEuler\\Projects81and82\\euler81\\src\\";
            FileReader fReader = new FileReader(folder + filename);

            BufferedReader bReader = new BufferedReader(fReader);

            String text;
            nbLines = -1;
            nbColumns = -1;

            int line = 0;
            while ((text = bReader.readLine()) != null) {
                if (nbLines == -1) {
                    // text is "nbLines nbCols"
                    String sizeValues[] = text.split(" ");
                    nbLines = Integer.valueOf(sizeValues[0]);
                    nbColumns = Integer.valueOf(sizeValues[1]);
                    System.out.println(nbLines + " lines, " + nbColumns + " cols");
                    array = new Node[nbLines][];
                    for (int i = 0; i < nbLines; i++) {
                        array[i] = new Node[nbColumns];
                    }
                } else if (line < nbLines) {
                    // Text is "131 673 234 103 18"
                    String values[] = text.split(",");
                    for (int col = 0; col < nbColumns; col++) {
                        System.out.println("col: " + col);
                        // Remove leading whitespaces
                        while (values[col].charAt(0) == ' ') {
                            values[col] = values[col].substring(1);
                        }
                        int value = Integer.valueOf(values[col]);
                        array[line][col] = new Node(value);
                    }
                    line++;
                }
            }
            openList = new ArrayList<>();
            closedList = new ArrayList<>();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        }

//        printGrid();
        nbSteps = 0;
    }

    /**
     * Find the cheapest path between the north-west square and the south-east
     * one, using the A* algorithm.
     */
    public void solve() {

        initSolve();

        boolean terminate = false;
        boolean success = false;

        while (!terminate) {
            step();

//            if (nbSteps == 10 * (nbSteps / 10)) {
//                System.out.println("step " + nbSteps);
//                printGrid();
//            }
            if (closedList.get(closedList.size() - 1).equals(targetNode)) {
                System.out.println("target reached.");
                terminate = true;
                success = true;
                printGrid();
            } else if (openList.isEmpty()) {
                System.out.println("No solution");
                terminate = true;
            }
        }

        if (success) {
            System.out.println("cost: " + targetNode.cost);
        }
    }

    private void initSolve() {
        openList.clear();
        closedList.clear();

//        // Problems 81 and 83:
//        openList.add(array[0][0]);
//        array[0][0].cost = array[0][0].getValue();
        // Problem 82:
        startingNode = new Node(Integer.MIN_VALUE);
        startingNode.cost = 0;
        openList.add(startingNode);

        setupNeighbors();
    }

    /**
     * Link every node with its neighbors, and set the target node.
     */
    private void setupNeighbors() {

        // Problem 82: target is the east neighbor of the whole most-eastern column.
        targetNode = new Node(0);

        for (int line = 0; line < nbLines; line++) {
            for (int col = 0; col < nbColumns; col++) {
                Node currentNode = array[line][col];
                Node north = null, south = null, east = null, west = null;
                if (line >= 1) {
                    north = array[line - 1][col];
                }
                if (line < nbLines - 1) {
                    south = array[line + 1][col];
                }
                if (col >= 1) {
                    west = array[line][col - 1];
                }
                if (col < nbColumns - 1) {
                    east = array[line][col + 1];
                } else {
                    // One common east-neighbors for the last column.
                    east = targetNode;
                }
                currentNode.setNeighbors(north, east, south, west);
            }
        }

        // Problems 81 and 83:
//        targetNode = array[nbLines - 1][nbColumns - 1];
        // Problem 82:
        ArrayList<Node> firstColumn = new ArrayList<>();
        for (int line = 0; line < nbLines; line++) {
            firstColumn.add(array[line][0]);
        }
        startingNode.setNeighbors(firstColumn);
    }

    /**
     * Add a new nodes, without doubles, to the open list.
     *
     * @param candidates
     * @param list
     */
    private void addToOpenList(ArrayList<Node> candidates, Node currentNode) {

//        System.out.println("Node " + currentNode.getValue() + " adds " + candidates.size() + " candidates.");
//
//        for (Node candidate : candidates) {
//            if (candidate != null) {
//                if (!openList.contains(candidate)) {
//
//                }
//            }
//        }
    }

    private void waitForKeypressed() {

        try {
            System.out.println("press key");
            System.in.read();
        } catch (IOException e) {
            System.out.println("grid wait error...");
        }
    }

    /**
     * Print the grid and display arrows to show the previous of each node.
     *
     */
    private void printGrid() {
        for (int line = 0; line < nbLines; line++) {

            // Display the nodes of this line
            for (int col = 0; col < nbColumns; col++) {
                Node n = array[line][col];

                // Diplay an arrow pointing to WEST or EAST parent
                if (n.getDirection() == CardinalPoint.WEST) {
                    System.out.print("<");
                } else {
                    System.out.print(" ");
                }
                System.out.print(String.format("%4d", n.getValue(), n.cost));
                if (n.getDirection() == CardinalPoint.EAST) {
                    System.out.print(">");
                } else {
                    System.out.print(" ");
                }

            }
            System.out.println("");

            // Display the links between nodes of this line and nodes of the next line.
            for (int col = 0; col < nbColumns; col++) {
                Node n = array[line][col];

                // Display an arrow pointing NORTH or SOUTH
                if (n.getDirection() == CardinalPoint.SOUTH) {
                    System.out.print("   v  ");
                } else if (line < nbLines - 1 && array[line + 1][col].getDirection() == CardinalPoint.NORTH) {
                    System.out.print("   ^  ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println("");
        }
        for (int col = 0; col < nbColumns; col++) {
            System.out.print("------");
        }
        System.out.println("");
    }

    /**
     * Compute one step of the A* algorithm
     */
    private void step() {

        if (openList.isEmpty()) {
            // End of algorithm with no path to target
            return;
        }

        Node currentNode = openList.remove(0);

        ArrayList<Node> neighbors = currentNode.getNeighbors();

        for (Node neighbor : neighbors) {
            int currentCost = neighbor.cost;

            if (currentNode.cost + neighbor.getValue() < currentCost) {
                // Found a better way to reach neighbor.
                neighbor.cost = currentNode.cost + neighbor.getValue();
                neighbor.setPrevious(currentNode);
            }
            // Add the neighbors of currentNode to the open list,
            // but only if they are not already in the open or closed list.
            if (!(closedList.contains(neighbor) || openList.contains(neighbor))) {
                addSortedToList(neighbor, openList);
            }
        }

        closedList.add(currentNode);

        nbSteps++;
    }

    /**
     * Add a new element to the list, so that the elements remain sorted.
     *
     * @param newNode the new element of known f_value
     * @param openList the list containing elements sorted by f_value
     */
    private void addSortedToList(Node newNode, ArrayList<Node> openList) {
        if (newNode != null) {
            if (openList.isEmpty()) {
                openList.add(newNode);
                return;
            }
            // At least one existing element:
            int rank = 0;
            while (rank < openList.size() && newNode.cost > openList.get(rank).cost) {
                rank++;
            }
            openList.add(rank, newNode);
        }
    }

}

//package com.cauldron.bodyconquest.AIs;
//
//import com.cauldron.bodyconquest.entities.Location;
//import com.cauldron.bodyconquest.entities.Map;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.PriorityQueue;
//
//public class Pathfinder {
//
//    private Map map = new Map();
//    private Node[][] cell = new Node[(int)map.getMaxX()][(int)map.getMaxY()];
//    private Location initialLocation;
//    private Location finalLocation;
//    private ArrayList<Node> pathList = new ArrayList<Node>();
//    private ArrayList<Node> closedList = new ArrayList<Node>();
//
//    public Pathfinder(Location initialLocation, Location finalLocation) {
//        this.initialLocation = initialLocation;
//        this.finalLocation = finalLocation;
//    }
//
//    // this method reverses the generated pathList and puts it into an array of
//    // locations
//    public ArrayList<Location> getPath() {
//        findPath();
//        ArrayList<Location> path = new ArrayList<Location>();
//        for (int i = pathList.size() - 1; i >= 0; i--) {
//            Node node = pathList.get(i);
//            Location location = new Location(node.x, node.y);
//            path.add(location);
//        }
//        return path;
//    }
//
//    private void generateNodeMap() {
////        for (int x = 0; x < map.getMaxX(); x++) {
////            for (int y = 0; y < map.getMaxY(); y++) {
////                // keeping the first point in the map as (1,1)
////                cell[x][y] = new Node(x, y);
////                if (map.isAccessible(new Location(x, y))) {
////                    // Assigning the Manhattan Heuristic value
////                    cell[x][y].hValue =
////                            (Math.abs(x - finalLocation.getX()) + Math.abs(y - finalLocation.getY()));
////                } else {
////                    // If the boolean value is false, then assigning -1 instead of the absolute
////                    // length
////                    cell[x][y].hValue = -1;
////                }
////            }
////        }
//    }
//
//    private void findPath() {
//        generateNodeMap();
//        PriorityQueue<Node> openList =
//                new PriorityQueue<Node>(
//                        11,
//                        new Comparator<Object>() {
//                            @Override
//                            // Compares 2 Node objects stored in the PriorityQueue and Reorders the Queue
//                            // according to the object which has the lowest fValue
//                            public int compare(Object cell1, Object cell2) {
//                return Double.compare(((Node) cell1).fValue, ((Node) cell2).fValue);
//                            }
//                        });
//        // Adds the starting node inside the openList
//        openList.add(cell[initialLocation.getX()][initialLocation.getY()]);
//
//        int gCost = 0;
//
//        while (true) {
//            // Gets and removes the objects that's stored on the top of the openList and
//            // saves it inside node
//            Node node = openList.poll();
//
//            // Checks whether node is empty; if it is, breaks the while loop
//            if (node == null) {
//                break;
//            }
//
//            // Checks whether the node is the goal node
//            // If it is, then stores that inside the closedList and breaks the while loop
//            if (node.equals(cell[finalLocation.getX()][finalLocation.getY()])) {
//                closedList.add(node);
//                break;
//            }
//
//            // even if it is not, we add the node to the closed list as it has been visited
//            closedList.add(node);
//
//            // checking neighbours
//            // Left Cell
//            try {
//                if (cell[node.x][node.y - 1].hValue != -1
//                        && !openList.contains(cell[node.x][node.y - 1])
//                        && !closedList.contains(cell[node.x][node.y - 1])) {
//                    cell[node.x][node.y - 1].gValue = gCost + 1;
//                    cell[node.x][node.y - 1].fValue =
//                            cell[node.x][node.y - 1].gValue + cell[node.x][node.y - 1].hValue;
//
//                    openList.add(cell[node.x][node.y - 1]);
//                    cell[node.x][node.y - 1].parent = node;
//                }
//            } catch (IndexOutOfBoundsException e) {
//                System.out.println("Left cell out of bounds");
//            }
//
//            // Right Cell
//            try {
//                if (cell[node.x][node.y + 1].hValue != -1
//                        && !openList.contains(cell[node.x][node.y + 1])
//                        && !closedList.contains(cell[node.x][node.y + 1])) {
//                    cell[node.x][node.y + 1].gValue = gCost + 1;
//                    cell[node.x][node.y + 1].fValue =
//                            cell[node.x][node.y + 1].gValue + cell[node.x][node.y + 1].hValue;
//
//                    openList.add(cell[node.x][node.y + 1]);
//                    cell[node.x][node.y + 1].parent = node;
//                }
//            } catch (IndexOutOfBoundsException e) {
//                System.out.println("Right cell out of bounds");
//            }
//
//            // Bottom Cell
//            try {
//                if (cell[node.x + 1][node.y].hValue != -1
//                        && !openList.contains(cell[node.x + 1][node.y])
//                        && !closedList.contains(cell[node.x + 1][node.y])) {
//                    cell[node.x + 1][node.y].gValue = gCost + 1;
//                    cell[node.x + 1][node.y].fValue =
//                            cell[node.x + 1][node.y].gValue + cell[node.x + 1][node.y].hValue;
//
//                    openList.add(cell[node.x + 1][node.y]);
//                    cell[node.x + 1][node.y].parent = node;
//                }
//            } catch (IndexOutOfBoundsException e) {
//                System.out.println("Bottom cell out of bounds");
//            }
//
//            // Top Cell
//            try {
//                if (cell[node.x - 1][node.y].hValue != -1
//                        && !openList.contains(cell[node.x - 1][node.y])
//                        && !closedList.contains(cell[node.x - 1][node.y])) {
//                    cell[node.x - 1][node.y].gValue = gCost + 1;
//                    cell[node.x - 1][node.y].fValue =
//                            cell[node.x - 1][node.y].gValue + cell[node.x - 1][node.y].hValue;
//
//                    openList.add(cell[node.x - 1][node.y]);
//                    cell[node.x - 1][node.y].parent = node;
//                }
//            } catch (IndexOutOfBoundsException e) {
//                System.out.println("Top cell out of bounds");
//            }
//
//            // when going to the next neighbour, the cost of getting there increases by 1
//            gCost++;
//        }
//        // constructing the path
//        // Assigns the last Object in the closedList to the endNode variable
//        Node endNode = closedList.get(closedList.size() - 1);
//
//        /*
//         * Checks whether the endNode variable currently has a parent Node. if it
//         * doesn't then stops moving forward. Stores each parent Node to the PathList so
//         * it is easier to trace back the final path Considering the order of addition,
//         * the resulting path will start with the destination so it will need to be
//         * reversed
//         */
//
//        while (endNode.parent != null) {
//            Node currentNode = endNode;
//            pathList.add(currentNode);
//            endNode = endNode.parent;
//        }
//
//        // adding the initial node
//        // pathList.add(cell[initialLocation.getX()][initialLocation.getY()]);
//
//        // Clears the openList
//        openList.clear();
//    }
//}

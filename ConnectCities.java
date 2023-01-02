import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.locks.Condition;

/*This class is used to represent the path between two cities
 * It works as the  edge of the graph
*/
class Road {
    String connectTo;
    int distance;

    public Road(String v, int w) {
        this.connectTo = v;
        this.distance = w;
    }

    // method to return the string representation of the object
    @Override
    public String toString() {
        return "(" + connectTo + "," + distance + ")";
    }
}

/*
 * This class is used to add the path/edge in between two cities.It also
 * contains function that will print the paths/edges in the form of adjaceny
 * list
 */
public class ConnectCities {
    Map<String, LinkedList<Road>> adj = new LinkedHashMap<>();

    public void addRoad(String source, String destination, int dist) {
        adj.putIfAbsent(source, new LinkedList<>());
        adj.putIfAbsent(destination, new LinkedList<>());
        Road road = new Road(destination, dist);
        adj.get(source).add(road); // add edge
    }

    public void printConnection() {
        for (String key : adj.keySet()) {
            System.out.println(key + " -> " + adj.get(key));
        }
    }

    // find the destination to charge the car

    public List<String> chargeCar(String start, String end, int capacity) {
        List<String> stations = new LinkedList<>();
        String current = start;
        int leftCapacity = capacity;// full initial capacity in miles at starting point
        stations.add(start);
        while (current != end) {
            int nextDistance = adj.get(current).get(0).distance;
            leftCapacity -= 2 * nextDistance;
            if (leftCapacity < 0) {
                // Add current node to output list since we have to charge the car at this
                // station
                stations.add(current);
                leftCapacity = capacity;
            } else {
                String nextKey = adj.get(current).get(0).connectTo;
                // Go to the next node in graph
                current = nextKey;
                leftCapacity += nextDistance;

            }
        }
        stations.add(end); // reached to last station/node
        return stations;
    }

    public static boolean isValiddist(int dist, int capacity) {
        if (dist > 10 && dist < capacity / 2+1)
            return (true);
        return (false);
    }

    public static boolean isCitiesValid(int ncities) {
        if (ncities > 3 && ncities < 20)
            return (true);
        return (false);
    }

    public static boolean isValidcap(int capacity) {
        if (capacity > 250 && capacity < 350)
            return true;
        return false;
    }

    public static void main(String[] args) {
        // main function
        int flag = 1;
        int capacity = 0;
        do {
            System.out
                    .println("Please Enter your choice :1.Solve Electric Car Traveller Problem ,0. Exit from program");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();// User will have choice to solve problem or exit
            switch (choice) {
                case 1:
                    boolean conditionvalid2 = false;
                    do {
                        System.out.println("Enter the maximum capacity of car in miles:");
                        capacity = sc.nextInt();
                        conditionvalid2 = isValidcap(capacity);
                        if (!conditionvalid2) {
                            System.out.println("car Capacity should be in 250 and 350");
                        }
                    } while (!conditionvalid2);
                    int number;
                    boolean conditionvalid;
                    do {
                        System.out.println("Enter the number of cities:");
                        number = sc.nextInt();
                        conditionvalid = !isCitiesValid(number);
                        if (conditionvalid) {
                            System.out.println("The number of cities must be greater than 3 and less than 20");
                        }
                    } while (conditionvalid);
                    String source, destination;
                    int distance;
                    String startCity = null, endCity = null;
                    ConnectCities graph = new ConnectCities(); // represents cities and the roads connecting cities as
                                                               // graph
                    for (int i = 1; i < number; i++) {
                        System.out.println("Enter Source City:");
                        source = sc.next();
                        System.out.println("Enter Destination City:");
                        destination = sc.next();
                        boolean conditionvalid1 = false;
                        do {
                            System.out.println("Enter distance between " + source + " and " + destination);
                            distance = sc.nextInt();
                            conditionvalid1 = !isValiddist(distance, capacity);
                            if (conditionvalid1) {
                                System.out.println(
                                        "The distance should be greater than 10 and less than " + (capacity / 2));
                            }
                        } while (conditionvalid1);
                        if (startCity == null) // start city/node .It will execute only once
                        {
                            startCity = source;
                        }
                        graph.addRoad(source, destination, distance);
                        if (i == number - 1) {
                            endCity = destination;// destination city/node
                        }

                    }

                    // graph.printConnection();
                    List<String> stopStations;
                    stopStations = graph.chargeCar(startCity, endCity, capacity);
                    System.out.println("List of Stop stations is:");
                    System.out.print("{ ");
                    StringJoiner sjoiner = new StringJoiner(",");
                    stopStations.forEach(item -> sjoiner.add(item));
                    System.out.print(sjoiner);
                    System.out.print(" }");
                    System.out.print("\n");
                    break;
                case 0:
                    flag = 0;
                    break;
                default:
                    System.exit(0);
            }
        } while (flag != 0);
    }
}

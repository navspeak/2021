package nav.graph;

import java.util.*;

public class AirportConnection {

    public static void main(String[] args) {
        List<String> AIRPORTS = Arrays.asList(
                "BGI", "CDG", "DEL", "DOH", "DSM", "EWR", "EYW", "HND", "ICN", "JFK", "LGA", "LHR",
                "ORD", "SAN", "SFO", "SIN", "TLV", "BUD");

        String STARTING_AIRPORT = "LGA";

        List<List<String>> routes = new ArrayList<>();
        routes.add(Arrays.asList("DSM", "ORD"));
        routes.add(Arrays.asList("ORD", "BGI"));
        routes.add(Arrays.asList("BGI", "LGA"));
        routes.add(Arrays.asList("SIN", "CDG"));
        routes.add(Arrays.asList("CDG", "SIN"));
        routes.add(Arrays.asList("CDG", "BUD"));
        routes.add(Arrays.asList("DEL", "DOH"));
        routes.add(Arrays.asList("DEL", "CDG"));
        routes.add(Arrays.asList("TLV", "DEL"));
        routes.add(Arrays.asList("EWR", "HND"));
        routes.add(Arrays.asList("HND", "ICN"));
        routes.add(Arrays.asList("HND", "JFK"));
        routes.add(Arrays.asList("ICN", "JFK"));
        routes.add(Arrays.asList("JFK", "LGA"));
        routes.add(Arrays.asList("EYW", "LHR"));
        routes.add(Arrays.asList("LHR", "SFO"));
        routes.add(Arrays.asList("SFO", "SAN"));
        routes.add(Arrays.asList("SFO", "DSM"));
        routes.add(Arrays.asList("SAN", "EYW"));
        new AirportConnection().airportConnections(AIRPORTS, routes, STARTING_AIRPORT);
    }

    public int airportConnections(List<String> airports, List<List<String>> routes, String startingAirPort) {

        Map<String, Node> graph = new HashMap<>();

        for(String airport: airports){
            graph.put(airport, new Node(airport));
        }

        for(List<String> route: routes){
            graph.get(route.get(0)).connections.add(graph.get(route.get(1)));
        }
        //TODO
        return -1;
    }

    static class Node {
        String name;
        List<Node> connections;
        Node(String name){
            this.name = name;
            connections = new ArrayList<>();
        }

    }
}

package ge.kuznetsov.shortestRoute.service;

import ge.kuznetsov.shortestRoute.dijkstra.DijkstraService;
import ge.kuznetsov.shortestRoute.dijkstra.Graph;
import ge.kuznetsov.shortestRoute.dijkstra.Node;
import ge.kuznetsov.shortestRoute.domain.Planet;
import ge.kuznetsov.shortestRoute.domain.Route;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteToGraphService {
    private final RouteService routeService;
    private final PlanetService planetService;

    private final DijkstraService dijkstraService;


    public RouteToGraphService(RouteService routeService, PlanetService planetService, DijkstraService dijkstraService) {
        this.routeService = routeService;
        this.planetService = planetService;
        this.dijkstraService = dijkstraService;
    }

    public List<Node> getAllNodesList(boolean delay) {
        List<Planet> allPlanets = planetService.findAllPlanets();
        List<Route> allRouts = routeService.findAllRoutes();


        List<Node> allNodes = new ArrayList<>();

        for (Planet planet : allPlanets) {
            Node node = new Node();
            node.setNodeId(planet.getPlanetId());
            allNodes.add(node);
        }

        for (Node node : allNodes
        ) {
            for (Route route : allRouts) {
                if (route.getSourceId() == node.getNodeId()) {
                    Node destination = allNodes.stream()
                            .filter(tempNode -> tempNode.getNodeId() == route.getTargetId())
                            .findFirst()
                            .get();

                    node.addDestination(destination, route.getDistance());
                }

                if (route.getTargetId() == node.getNodeId()) {
                    Node destination = allNodes.stream()
                            .filter(tempNode -> tempNode.getNodeId() == route.getSourceId())
                            .findFirst()
                            .get();

                    double distance = Double.MAX_VALUE;

                    if (!delay) {
                        distance = route.getDistance();
                    }
                    if (delay) {
                        distance = route.getTotalDistanceWithRespectToDelay();
                    }
                    node.addDestination(destination, distance);
                }
            }
        }
        return allNodes;
    }


    public Graph getGraphOfAllNodes(boolean delay) {
        Graph graph = new Graph();
        List<Node> nodes = getAllNodesList(delay);
        nodes.forEach(graph::addNode);
        return graph;
    }

    public List<Planet> calculateShortestPathFromSourceToDestination(long sourceId, long targetId, boolean delay) {
        Graph graph = getGraphOfAllNodes(delay);

        Node sourceNode = graph.getNodes()
                .stream()
                .filter(node -> node.getNodeId() == sourceId)
                .findFirst()
                .get();
        Node targetNode = graph.getNodes()
                .stream()
                .filter(node -> node.getNodeId() == targetId)
                .findFirst()
                .get();

        graph = dijkstraService.calculateShortestPathFromSource(graph, sourceNode);

        List<Node> nodePathList = graph.getNodes()
                .stream()
                .filter(node -> (node.getNodeId() == targetId))
                .findFirst()
                .get()
                .getShortestPath();

        List<Planet> planetaryPathList = new ArrayList<>();
        nodePathList.forEach(node -> planetaryPathList.add(planetService.findPlanetById(node.getNodeId()).get()));
        return planetaryPathList;
    }


}

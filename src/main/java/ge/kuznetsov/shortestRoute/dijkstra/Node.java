package ge.kuznetsov.shortestRoute.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Node {
    private Long nodeId;

    private List<Node> shortestPath = new LinkedList<>();

    private Double distance = Double.MAX_VALUE;

    Map<Node, Double> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, double distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + nodeId + '\'' +
                '}';
    }
}

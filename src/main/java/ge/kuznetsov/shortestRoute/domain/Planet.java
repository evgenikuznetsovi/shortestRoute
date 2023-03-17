package ge.kuznetsov.shortestRoute.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "planet")
@Table(name = "PLANET", schema = "ROUTE_INFORMATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long planetId;

    @Column(name = "PLANETE_NODE_NAME", nullable = false, unique = true)
    private String planetNodeName;

    @Column(name = "PLANETE_NAME", nullable = false, unique = true)
    private String planetName;
}

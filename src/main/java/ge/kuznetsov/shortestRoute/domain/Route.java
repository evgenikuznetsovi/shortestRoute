package ge.kuznetsov.shortestRoute.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "route")
@Table(name = "ROUTE", schema = "ROUTE_INFORMATION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long routeId;

    @Column(name = "SOURCE_ID", nullable = false)
    private long sourceId;

    @Column(name = "TARGET_ID", nullable = false)
    private long targetId;

    @Column(name = "DISTANCE", nullable = false)
    private double distance ;

    @Column(name = "DELAY", nullable = false)
    private double trafficDelay;

    @Column(name = "SPEED", nullable = false)
    private double speed = 7500000000000.0;

    @Transient
    private final double lightYear = 9460730472580800.0;

    @Transient
    private double totalDistanceWithRespectToDelay = Double.MAX_VALUE;

    @PostLoad
    private void calculateTotalDistanceWithRespectToDelay() {
        this.totalDistanceWithRespectToDelay = distance + trafficDelay;
    }



}

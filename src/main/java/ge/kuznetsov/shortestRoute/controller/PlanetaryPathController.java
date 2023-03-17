package ge.kuznetsov.shortestRoute.controller;

import ge.kuznetsov.shortestRoute.domain.Planet;
import ge.kuznetsov.shortestRoute.service.RouteToGraphService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("calculate")
public class PlanetaryPathController {

    private  final RouteToGraphService routeToGraphService;

    public PlanetaryPathController(RouteToGraphService routeToGraphService) {
        this.routeToGraphService = routeToGraphService;
    }

    @GetMapping("path/{sourceId}/{targetId}/{delay}")
    @ResponseStatus(HttpStatus.OK)
    public List<Planet> calculateShortestPathFromSourceToDestination(
            @PathVariable("sourceId") long sourceId
            ,@PathVariable("targetId") long targetId
            ,@PathVariable("delay") boolean delay){
        return  routeToGraphService
                .calculateShortestPathFromSourceToDestination(sourceId, targetId, delay);
    }

}

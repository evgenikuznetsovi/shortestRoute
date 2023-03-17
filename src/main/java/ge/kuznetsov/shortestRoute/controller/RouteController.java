package ge.kuznetsov.shortestRoute.controller;

import ge.kuznetsov.shortestRoute.domain.Route;
import ge.kuznetsov.shortestRoute.exception.RouteNotFoundException;
import ge.kuznetsov.shortestRoute.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("routes")
public class RouteController {


    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }


    @PostMapping("saveRouts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveAllRoutes(@RequestParam("routeList") List<Route> routeList) {
        routeService.saveRoutesToDatabase(routeList);
    }

    @PostMapping("saveRoute")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveRoute(@RequestBody Route route) {
        routeService.saveRouteToDatabase(route);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Route update(@PathVariable("id") long id, @RequestBody Route route)
            throws RouteNotFoundException {
        return routeService.update(id, route);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Route> findRouteById(@PathVariable("id") Long id) throws RouteNotFoundException {
        return routeService.findRouteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Route> findAllRoutes() {
        return routeService.findAllRoutes();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRouteById(@PathVariable("id") Long id) {
        routeService.deleteById(id);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoute(@RequestBody Route entity) {
        routeService.delete(entity);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllRouts() {
        routeService.deleteAll();
    }
}

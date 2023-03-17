package ge.kuznetsov.shortestRoute.service;

import ge.kuznetsov.shortestRoute.domain.Route;
import ge.kuznetsov.shortestRoute.exception.RouteNotFoundException;
import ge.kuznetsov.shortestRoute.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routRepository) {
        routeRepository = routRepository;
    }

    public void saveRoutesToDatabase(List<Route> planets) {
        routeRepository.saveAll(planets);
    }

    public void saveRouteToDatabase(Route route) {
        routeRepository.save(route);
    }

    public ArrayList<Route> findAllRoutes() {
        return (ArrayList<Route>) routeRepository.findAll();
    }

    public Optional<Route> findRouteById(long id) {
        return routeRepository.findById(id);
    }

    public Optional<Route> findRouteByEndIds(Long sourceId, Long targetId) throws RouteNotFoundException {
        List<Route> routes = routeRepository.findByEndNames(sourceId, targetId);
        if (routes.size() == 0) {
            throw new RouteNotFoundException("Route with sourceId: "
                    + sourceId + " and targetId: " + targetId
                    + " has been not found");
        }

        if (routes.size() > 1) {
            throw new RouteNotFoundException("More then one route with sourceId: "
                    + sourceId + " and targetId: " + targetId
                    + " has been found");
        }

        return routes.stream().findFirst();
    }

    public Route update(long id, Route route) throws RouteNotFoundException {
        if (routeRepository.existsById(id)) {
            route.setRouteId(id);
            return routeRepository.save(route);
        }
        throw new RouteNotFoundException();
    }

    public Route updateRoutTraffic(long id, double traffic) throws RouteNotFoundException {
        if (routeRepository.existsById(id)) {
            findRouteById(id).get().setTrafficDelay(traffic);
            return routeRepository.save(findRouteById(id).get());
        }
        throw new RouteNotFoundException();

    }

    public boolean existsById(Long id) {
        return routeRepository.existsById(id);
    }

    public Iterable<Route> findAllById(Iterable<Long> ids) {
        return routeRepository.findAllById(ids);
    }

    public long count() {
        return routeRepository.count();
    }

    public void deleteById(Long id) {
        routeRepository.deleteById(id);
    }

    public void delete(Route entity) {
        routeRepository.delete(entity);
    }

    public void deleteAllById(Iterable<Long> ids) {
        routeRepository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<Route> entities) {
        routeRepository.deleteAll(entities);
    }

    public void deleteAll() {
        routeRepository.deleteAll();
    }


}

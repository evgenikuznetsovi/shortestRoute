package ge.kuznetsov.shortestRoute.controller;


import ge.kuznetsov.shortestRoute.domain.Route;
import ge.kuznetsov.shortestRoute.exception.PlanetNotFoundException;
import ge.kuznetsov.shortestRoute.domain.Planet;
import ge.kuznetsov.shortestRoute.exception.RouteNotFoundException;
import ge.kuznetsov.shortestRoute.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("planets")
public class PlanetController {
    private final PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping("savePlanets")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveAllPlanets(@RequestParam("planets") List<Planet> planets) {
        planetService.savePlanetsToDatabase(planets);
    }

    @PostMapping("savePlanet")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void savePlanet(@RequestBody Planet planet) {
        planetService.savePlanetToDataBase(planet);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Planet update(@PathVariable("id") long id, @RequestBody Planet planet)
            throws PlanetNotFoundException {
        return planetService.update(id, planet);
    }


    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Planet> findPlanetById(@PathVariable("id") Long id) throws PlanetNotFoundException {
        return planetService.findPlanetById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Planet> findAllPlanets() {
        return planetService.findAllPlanets();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlanetById(@PathVariable("id") Long id) {
        planetService.deleteById(id);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlanet(@RequestBody Planet entity) {
        planetService.delete(entity);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllPlanets() {
        planetService.deleteAll();
    }

}

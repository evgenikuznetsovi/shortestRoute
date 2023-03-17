package ge.kuznetsov.shortestRoute.service;

import ge.kuznetsov.shortestRoute.domain.Planet;
import ge.kuznetsov.shortestRoute.exception.PlanetNotFoundException;
import ge.kuznetsov.shortestRoute.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {
    private final PlanetRepository planetRepository;

    @Autowired
    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

//    private List<Planet> retrievePlanetList(XSSFWorkbook workbook) {
//        return ExcelUploadService.getPlanetsDataFromExcel(workbook);
//
//    }

    public void savePlanetsToDatabase(List<Planet> planets) {
        this.planetRepository.saveAll(planets);
    }

    public void savePlanetToDataBase(Planet planet) {
        this.planetRepository.save(planet);

    }

    public ArrayList<Planet> findAllPlanets() {
        return (ArrayList<Planet>) planetRepository.findAll();
    }

    public Optional<Planet> findPlanetById(long id) {
        return planetRepository.findById(id);
    }

    public Optional<Planet> findPlanetByNodeName(String nodeName) throws PlanetNotFoundException {
        if (Optional.ofNullable(planetRepository.findByPlanetNodeName(nodeName)).isEmpty()){
            throw new PlanetNotFoundException("Planet with nodeName: " + nodeName + " has not be found.");
        }
        return  Optional.of(planetRepository.findByPlanetNodeName(nodeName).get(0));
    }

    public Planet update(long id, Planet planet) throws PlanetNotFoundException {
        if (planetRepository.existsById(id)) {
            planet.setPlanetId(id);
            return planetRepository.save(planet);
        }
        throw new PlanetNotFoundException();
    }

    public boolean existsById(Long id) {
        return planetRepository.existsById(id);
    }

    public Iterable<Planet> findAllById(Iterable<Long> ids) {
        return planetRepository.findAllById(ids);
    }

    public long count() {
        return planetRepository.count();
    }

    public void deleteById(Long id) {
        planetRepository.deleteById(id);
    }

    public void delete(Planet entity) {
        planetRepository.delete(entity);
    }

    public void deleteAllById(Iterable<Long> ids) {
        planetRepository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<Planet> entities) {
        planetRepository.deleteAll(entities);
    }

    public void deleteAll() {
        planetRepository.deleteAll();
    }


}

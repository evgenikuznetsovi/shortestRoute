package ge.kuznetsov.shortestRoute.repository;

import ge.kuznetsov.shortestRoute.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    @Query(value ="SELECT p FROM planet p WHERE p.planetNodeName = ?1")
    List<Planet> findByPlanetNodeName(@Param("planetNodeName") String planetNodeName);
}

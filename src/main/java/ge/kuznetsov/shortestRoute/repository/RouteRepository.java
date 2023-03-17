package ge.kuznetsov.shortestRoute.repository;


import ge.kuznetsov.shortestRoute.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query(value =
            "SELECT r FROM route r " +
                    "WHERE (r.sourceId = ?1 AND r.targetId = ?2) " +
                    "OR (r.sourceId = ?2 AND r.targetId = ?1)")
    List<Route> findByEndNames(@Param("sourceId") Long sourceId, @Param("targetId") Long targetId);
}


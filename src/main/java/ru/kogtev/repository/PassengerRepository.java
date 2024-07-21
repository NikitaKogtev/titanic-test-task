package ru.kogtev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kogtev.model.Passenger;
import ru.kogtev.model.Sex;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    @Query("SELECT p FROM Passenger p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:survived IS NULL OR p.survived = :survived) AND " +
            "(:minAge IS NULL OR p.age >= :minAge) AND " +
            "(:sex IS NULL OR p.sex = :sex) AND " +
            "(:hasRelatives IS NULL OR " +
            "(:hasRelatives = TRUE AND (p.siblingsSpousesAboard > 0 OR p.parentsChildrenAboard > 0)) OR " +
            "(:hasRelatives = FALSE AND p.siblingsSpousesAboard = 0 AND p.parentsChildrenAboard = 0))")
    Page<Passenger> findByFilters(@Param("name") String name,
                                  @Param("survived") Boolean survived,
                                  @Param("minAge") Integer minAge,
                                  @Param("sex") Sex sex,
                                  @Param("hasRelatives") Boolean hasRelatives,
                                  Pageable pageable);

    @Query("SELECT SUM(p.fare) FROM Passenger p WHERE p.name LIKE %:name%")
    Double getTotalFareByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT SUM(p.fare) FROM Passenger p")
    Double getTotalFare();

    @Query("SELECT COUNT(p.id) FROM Passenger p WHERE (p.siblingsSpousesAboard > 0 OR p.parentsChildrenAboard > 0) AND p.name LIKE %:name%")
    Long getRelativesCountByNameContaining(@Param("name") String name);

    @Query("SELECT COUNT(p.id) FROM Passenger p WHERE p.siblingsSpousesAboard > 0 OR p.parentsChildrenAboard > 0")
    Long getRelativesCount();

    @Query("SELECT COUNT(p) FROM Passenger p WHERE p.survived = true AND p.name LIKE %:name%")
    Long getSurvivorsCountByNameContaining(@Param("name") String name);

    @Query("SELECT COUNT(p) FROM Passenger p WHERE p.survived = true")
    Long getSurvivorsCount();
}

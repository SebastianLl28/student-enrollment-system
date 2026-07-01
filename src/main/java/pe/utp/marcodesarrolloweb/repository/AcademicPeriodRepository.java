package pe.utp.marcodesarrolloweb.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;

/**
 * @author Alonso
 */
public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, Long> {

  boolean existsByName(String name);

  boolean existsByNameAndIdNot(String name, Long id);

  @Query("SELECT p FROM AcademicPeriod p WHERE p.active = true AND p.startDate <= :today AND p.endDate >= :today")
  Optional<AcademicPeriod> findCurrentPeriod(@Param("today") LocalDate today);

  @Query("SELECT COUNT(p) FROM AcademicPeriod p WHERE p.startDate <= :endDate AND p.endDate >= :startDate")
  long countOverlapping(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query("SELECT COUNT(p) FROM AcademicPeriod p WHERE p.startDate <= :endDate AND p.endDate >= :startDate AND p.id <> :excludeId")
  long countOverlappingExcluding(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("excludeId") Long excludeId);
}

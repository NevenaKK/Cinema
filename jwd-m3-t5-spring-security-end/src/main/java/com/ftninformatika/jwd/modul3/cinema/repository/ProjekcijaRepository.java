package com.ftninformatika.jwd.modul3.cinema.repository;

import com.ftninformatika.jwd.modul3.cinema.model.Projekcija;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjekcijaRepository extends JpaRepository<Projekcija,Long> {

    Projekcija findOneById(Long id);

    @Query("SELECT p FROM Projekcija p WHERE" +
            "(p.datumIVreme BETWEEN :datumIVremeOd AND :datumIVremeDo) AND " +
            "(p.cenaKarte BETWEEN :cenaKarteOd AND :cenaKarteDo) AND "+
            "(:tip = NULL OR p.tip LIKE :tip) AND " +
            "(:filmId = NULL OR p.film.id = :filmId) AND " +
            "(:sala = NULL OR p.sala = :sala)")
    Page<Projekcija> search(@Param("datumIVremeOd") LocalDateTime datumIVremeOd, @Param("datumIVremeDo") LocalDateTime datumIVremeDo,
                            @Param("cenaKarteOd") Double cenaKarteOd, @Param("cenaKarteDo") Double cenaKarteDo,
                            @Param("tip") String tip, @Param("filmId") Long filmId, @Param("sala") Integer sala, Pageable pageable);

    List<Projekcija> findByFilmId(Long filmId);
}

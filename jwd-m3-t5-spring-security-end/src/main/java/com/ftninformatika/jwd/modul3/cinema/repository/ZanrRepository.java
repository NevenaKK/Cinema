package com.ftninformatika.jwd.modul3.cinema.repository;

import com.ftninformatika.jwd.modul3.cinema.model.Zanr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZanrRepository extends JpaRepository<Zanr,Long> {

    Zanr findOneById(Long id);

    Page<Zanr> findByNazivIgnoreCaseContains(String naziv, Pageable pageable);

    List<Zanr> findByIdIn(List<Long> ids);
}

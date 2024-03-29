package com.ftninformatika.jwd.modul3.cinema.service;

import com.ftninformatika.jwd.modul3.cinema.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FilmService {

    Film findOne(Long id);

    List<Film> findAll();

    Page<Film> findAll(Pageable pageable);

    Film save(Film film);

    Film update(Film film);

    Film delete(Long id);

    Page<Film> find(String naziv, Long zanrId, Integer trajanjeOd, Integer trajanjeDo, int page);

    List<Film> findByZanrId(Long zanrId);
}

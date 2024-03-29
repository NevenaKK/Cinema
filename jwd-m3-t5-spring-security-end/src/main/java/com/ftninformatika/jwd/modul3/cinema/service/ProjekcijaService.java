package com.ftninformatika.jwd.modul3.cinema.service;

import com.ftninformatika.jwd.modul3.cinema.model.Projekcija;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjekcijaService {

    Projekcija findOne(Long id);

    Page<Projekcija> findAll(int pageNo);

    Projekcija save(Projekcija projekcija);

    Projekcija update(Projekcija projekcija);

    Projekcija delete(Long id);

    Page<Projekcija> find(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long filmId, String tip, Integer sala, Double cenaKarteOd, Double cenaKarteDo,
                          int pageNo);

    List<Projekcija> findByFilmId(Long filmId);
}

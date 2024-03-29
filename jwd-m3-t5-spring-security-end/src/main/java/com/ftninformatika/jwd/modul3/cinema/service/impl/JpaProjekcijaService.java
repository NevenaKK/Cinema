package com.ftninformatika.jwd.modul3.cinema.service.impl;

import com.ftninformatika.jwd.modul3.cinema.model.Projekcija;
import com.ftninformatika.jwd.modul3.cinema.repository.ProjekcijaRepository;
import com.ftninformatika.jwd.modul3.cinema.service.ProjekcijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class JpaProjekcijaService implements ProjekcijaService {

    @Autowired
    private ProjekcijaRepository projekcijaRepository;

    @Override
    public Projekcija findOne(Long id) {
        return projekcijaRepository.findOneById(id);
    }

    @Override
    public Page<Projekcija> findAll(int pageNo) {
        return projekcijaRepository.findAll( PageRequest.of(pageNo, 4));
    }

    @Override
    public Projekcija save(Projekcija projekcija) {
        return projekcijaRepository.save(projekcija);
    }

    @Override
    public Projekcija update(Projekcija projekcija) {
        return projekcijaRepository.save(projekcija);
    }

    @Override
    public Projekcija delete(Long id) {
        Projekcija projekcija = findOne(id);
        if(projekcija != null){
            projekcija.getFilm().getProjekcije().remove(projekcija);
            projekcijaRepository.delete(projekcija);
            return projekcija;
        }
        return null;
    }

    @Override
    public Page<Projekcija> find(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long filmId, String tip, Integer sala,
                                 Double cenaKarteOd, Double cenaKarteDo, int pageNo) {
        if (cenaKarteOd == null) {
            cenaKarteOd = 0.0;
        }

        if (cenaKarteDo == null) {
            cenaKarteDo = Double.MAX_VALUE;
        }

        return projekcijaRepository.search(datumIVremeOd,datumIVremeDo,cenaKarteOd, cenaKarteDo, tip, filmId,sala, PageRequest.of(pageNo, 4));
    }

    @Override
    public List<Projekcija> findByFilmId(Long filmId) {
        return projekcijaRepository.findByFilmId(filmId);
    }

}

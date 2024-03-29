package com.ftninformatika.jwd.modul3.cinema.support;

import com.ftninformatika.jwd.modul3.cinema.model.Film;
import com.ftninformatika.jwd.modul3.cinema.model.Zanr;
import com.ftninformatika.jwd.modul3.cinema.service.FilmService;
import com.ftninformatika.jwd.modul3.cinema.service.ZanrService;
import com.ftninformatika.jwd.modul3.cinema.web.dto.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class FilmDtoToFilm implements Converter<FilmDTO,Film> {

    @Autowired
    private FilmService filmService;

    @Autowired
    private ZanrService zanrService;

    @Override
    public Film convert(FilmDTO dto) {

        Film entity;

        if(dto.getId() == null) {
            entity = new Film();
        }else {
            entity = filmService.findOne(dto.getId());
        }

        if(entity != null) {
            entity.setNaziv(dto.getNaziv());
            entity.setTrajanje(dto.getTrajanje());

            List<Zanr> zanrovi = zanrService.find(new ArrayList<>(dto.getZanrovi().keySet()));
            entity.setZanrovi(new HashSet<>(zanrovi));
        }

        return entity;
    }
}

package com.ftninformatika.jwd.modul3.cinema.support;

import com.ftninformatika.jwd.modul3.cinema.model.Film;
import com.ftninformatika.jwd.modul3.cinema.model.Zanr;
import com.ftninformatika.jwd.modul3.cinema.web.dto.FilmDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class FilmToFilmDto implements Converter<Film, FilmDTO> {

    @Override
    public FilmDTO convert(Film film) {
        FilmDTO dto = new FilmDTO();
        dto.setId(film.getId());
        dto.setNaziv(film.getNaziv());
        dto.setTrajanje(film.getTrajanje());
        LinkedHashMap<Long, String> zanroviMap = new LinkedHashMap<>();
        for (Zanr zanr: film.getZanrovi()) {
            zanroviMap.put(zanr.getId(), zanr.getNaziv());
        }
        dto.setZanrovi(zanroviMap);
        return dto;
    }

    public List<FilmDTO> convert(List<Film> filmovi){
        List<FilmDTO> filmoviDto = new ArrayList<>();

        for(Film film : filmovi) {
            filmoviDto.add(convert(film));
        }

        return filmoviDto;
    }

}


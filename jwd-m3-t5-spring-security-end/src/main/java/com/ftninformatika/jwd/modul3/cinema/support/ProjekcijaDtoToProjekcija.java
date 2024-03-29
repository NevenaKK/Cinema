package com.ftninformatika.jwd.modul3.cinema.support;

import com.ftninformatika.jwd.modul3.cinema.model.Projekcija;
import com.ftninformatika.jwd.modul3.cinema.service.FilmService;
import com.ftninformatika.jwd.modul3.cinema.service.ProjekcijaService;
import com.ftninformatika.jwd.modul3.cinema.web.dto.ProjekcijaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ProjekcijaDtoToProjekcija implements Converter<ProjekcijaDTO, Projekcija> {

    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private FilmService filmService;

    @Override
    public Projekcija convert(ProjekcijaDTO dto) {
        Projekcija projekcija;

        if(dto.getId() == null){
            projekcija = new Projekcija();
        }else{
            projekcija = projekcijaService.findOne(dto.getId());
        }

        if(projekcija != null){
            projekcija.setDatumIVreme(getLocalDateTime(dto.getDatumIVreme()));
            projekcija.setFilm(filmService.findOne(dto.getFilmId()));
            projekcija.setSala(dto.getSala());
            projekcija.setTip(dto.getTip());
            projekcija.setCenaKarte(dto.getCenaKarte());
        }
        return projekcija;
    }

    private LocalDateTime getLocalDateTime(String dateTime) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }
}

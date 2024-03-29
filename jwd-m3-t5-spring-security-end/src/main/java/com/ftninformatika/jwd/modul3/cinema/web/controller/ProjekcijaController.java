package com.ftninformatika.jwd.modul3.cinema.web.controller;

import com.ftninformatika.jwd.modul3.cinema.model.Projekcija;
import com.ftninformatika.jwd.modul3.cinema.service.ProjekcijaService;
import com.ftninformatika.jwd.modul3.cinema.support.ProjekcijaDtoToProjekcija;
import com.ftninformatika.jwd.modul3.cinema.support.ProjekcijaToProjekcijaDto;
import com.ftninformatika.jwd.modul3.cinema.web.dto.ProjekcijaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/projekcije", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjekcijaController {

    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private ProjekcijaDtoToProjekcija toProjekcija;

    @Autowired
    private ProjekcijaToProjekcijaDto toProjekcijaDto;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjekcijaDTO> create(@Valid @RequestBody ProjekcijaDTO projekcijaDTO){
        Projekcija projekcija = toProjekcija.convert(projekcijaDTO);

        if(projekcija.getFilm() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Projekcija sacuvanProjekcija = projekcijaService.save(projekcija);

        return new ResponseEntity<>(toProjekcijaDto.convert(sacuvanProjekcija), HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value= "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjekcijaDTO> update(@PathVariable Long id, @Valid @RequestBody ProjekcijaDTO projekcijaDTO){

        if(!id.equals(projekcijaDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Projekcija projekcija = toProjekcija.convert(projekcijaDTO);

        if(projekcija.getFilm() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Projekcija sacuvanProjekcija = projekcijaService.update(projekcija);

        return new ResponseEntity<>(toProjekcijaDto.convert(sacuvanProjekcija),HttpStatus.OK);
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Projekcija obrisanProjekcija = projekcijaService.delete(id);

        if(obrisanProjekcija != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   // @PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProjekcijaDTO> getOne(@PathVariable Long id){
        Projekcija projekcija = projekcijaService.findOne(id);

        if(projekcija != null) {
            return new ResponseEntity<>(toProjekcijaDto.convert(projekcija), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   // @PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProjekcijaDTO>> getAll(
            @RequestParam(required=false) String datumIVremeOdParametar,
            @RequestParam(required=false) String datumIVremeDoParametar,
            @RequestParam(required=false) Long filmId,
            @RequestParam(required=false) String tip,
            @RequestParam(required=false) Integer sala,
            @RequestParam(required=false) Double cenaKarteOd,
            @RequestParam(required=false) Double cenaKarteDo,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo){

        Page<Projekcija> page;
        try{
            LocalDateTime datumIVremeOd =  getLocalDateTime(datumIVremeOdParametar);
            LocalDateTime datumIVremeDo =  getLocalDateTime(datumIVremeDoParametar);

            page = projekcijaService.find(datumIVremeOd,datumIVremeDo,filmId,tip,sala,cenaKarteOd,cenaKarteDo, pageNo);
        }catch (Exception e){
            page = projekcijaService.findAll(pageNo);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Total-Pages", Integer.toString(page.getTotalPages()));

        return new ResponseEntity<>(toProjekcijaDto.convert(page.getContent()), headers, HttpStatus.OK);
    }

    private LocalDateTime getLocalDateTime(String dateTime) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }
}

package com.ftninformatika.jwd.modul3.cinema.web.controller;

import com.ftninformatika.jwd.modul3.cinema.model.Film;
import com.ftninformatika.jwd.modul3.cinema.model.Zanr;
import com.ftninformatika.jwd.modul3.cinema.service.FilmService;
import com.ftninformatika.jwd.modul3.cinema.service.ZanrService;
import com.ftninformatika.jwd.modul3.cinema.support.FilmToFilmDto;
import com.ftninformatika.jwd.modul3.cinema.support.ZanrDtoToZanr;
import com.ftninformatika.jwd.modul3.cinema.support.ZanrToZanrDto;
import com.ftninformatika.jwd.modul3.cinema.web.dto.FilmDTO;
import com.ftninformatika.jwd.modul3.cinema.web.dto.ZanrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/zanrovi", produces = MediaType.APPLICATION_JSON_VALUE)
public class ZanrController {

    @Autowired
    private ZanrService zanrService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private ZanrToZanrDto toZanrDto;

    @Autowired
    private ZanrDtoToZanr toZanr;

    @Autowired
    private FilmToFilmDto toFilmDto;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ZanrDTO> create(@Valid @RequestBody ZanrDTO zanrDTO){
        Zanr zanr = toZanr.convert(zanrDTO);
        Zanr sacuvanZanr = zanrService.save(zanr);

        return new ResponseEntity<>(toZanrDto.convert(sacuvanZanr), HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ZanrDTO> update(@PathVariable Long id, @Valid @RequestBody ZanrDTO zanrDTO){

        if(!id.equals(zanrDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Zanr zanr = toZanr.convert(zanrDTO);
        Zanr sacuvanZanr = zanrService.update(zanr);

        return new ResponseEntity<>(toZanrDto.convert(sacuvanZanr),HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Zanr obrisanZanr = zanrService.delete(id);

        if(obrisanZanr != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   // @PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ZanrDTO> getOne(@PathVariable Long id){
        Zanr zanr = zanrService.findOne(id);

        if(zanr != null) {
            return new ResponseEntity<>(toZanrDto.convert(zanr), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ZanrDTO>> getAll(
            @RequestParam(required=false) String naziv,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo){

        Page<Zanr> page;

        if(naziv == null){
            page = zanrService.findAll(pageNo);
        }else {
            page = zanrService.find(naziv,pageNo);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Total-Pages", Integer.toString(page.getTotalPages()));

        return new ResponseEntity<>(toZanrDto.convert(page.getContent()), headers, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
    @GetMapping("/{id}/filmovi")
    public ResponseEntity<List<FilmDTO>> findByZanrId(@PathVariable Long id){
        List<Film> filmovi = filmService.findByZanrId(id);

        return new ResponseEntity<>(toFilmDto.convert(filmovi), HttpStatus.OK);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Void> handle() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

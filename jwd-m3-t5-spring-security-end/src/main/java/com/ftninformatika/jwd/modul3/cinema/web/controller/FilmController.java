package com.ftninformatika.jwd.modul3.cinema.web.controller;

import com.ftninformatika.jwd.modul3.cinema.model.Film;
import com.ftninformatika.jwd.modul3.cinema.model.Projekcija;
import com.ftninformatika.jwd.modul3.cinema.service.FilmService;
import com.ftninformatika.jwd.modul3.cinema.service.ProjekcijaService;
import com.ftninformatika.jwd.modul3.cinema.support.FilmDtoToFilm;
import com.ftninformatika.jwd.modul3.cinema.support.FilmToFilmDto;
import com.ftninformatika.jwd.modul3.cinema.support.ProjekcijaToProjekcijaDto;
import com.ftninformatika.jwd.modul3.cinema.web.dto.FilmDTO;
import com.ftninformatika.jwd.modul3.cinema.web.dto.ProjekcijaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/api/filmovi", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private FilmDtoToFilm toFilm;

    @Autowired
    private FilmToFilmDto toFilmDto;

    @Autowired
    private ProjekcijaToProjekcijaDto toProjekcijaDto;

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmDTO> create(@Valid @RequestBody FilmDTO filmDTO){
        Film film = toFilm.convert(filmDTO);
        Film sacuvanFilm = filmService.save(film);

        return new ResponseEntity<>(toFilmDto.convert(sacuvanFilm), HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmDTO> update(@PathVariable Long id, @Valid @RequestBody FilmDTO filmDTO){

        if(!id.equals(filmDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Film film = toFilm.convert(filmDTO);
        Film sacuvanFilm = filmService.update(film);

        return new ResponseEntity<>(toFilmDto.convert(sacuvanFilm),HttpStatus.OK);
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Film obrisanFilm = filmService.delete(id);

        if(obrisanFilm != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getOne(@PathVariable Long id){
        Film film = filmService.findOne(id);

        if(film != null) {
            return new ResponseEntity<>(toFilmDto.convert(film), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<FilmDTO>> getAll(
            @RequestParam(required=false) String naziv,
            @RequestParam(required=false) Long zanrId,
            @RequestParam(required=false) Integer trajanjeOd,
            @RequestParam(required=false) Integer trajanjeDo,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo){

        Page<Film> page = filmService.find(naziv, zanrId, trajanjeOd, trajanjeDo, pageNo);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Total-Pages", Integer.toString(page.getTotalPages()));

        return new ResponseEntity<>(toFilmDto.convert(page.getContent()),headers, HttpStatus.OK);
    }


    @GetMapping("/by-page")
    public ResponseEntity<Page<FilmDTO>> get(Pageable pageable){

        Page<Film> filmovi = filmService.findAll(pageable);
        List<FilmDTO> pronadjeniFilmovi = toFilmDto.convert(filmovi.toList());

        Page<FilmDTO> filmoviDTO = new PageImpl<>(pronadjeniFilmovi, filmovi.getPageable(), filmovi.getTotalElements());

        return new ResponseEntity<>(filmoviDTO, HttpStatus.OK);
    }

   // @PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
    @GetMapping("/{id}/projekcije")
    public ResponseEntity<List<ProjekcijaDTO>> findByFilmId(@PathVariable @Positive(message = "Id must be positive.")  Long id){
        List<Projekcija> projekcije = projekcijaService.findByFilmId(id);

        return new ResponseEntity<>(toProjekcijaDto.convert(projekcije), HttpStatus.OK);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Void> handle() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

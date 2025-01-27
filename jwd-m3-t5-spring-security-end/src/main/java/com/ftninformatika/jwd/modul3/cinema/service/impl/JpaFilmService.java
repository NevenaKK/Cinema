package com.ftninformatika.jwd.modul3.cinema.service.impl;

import com.ftninformatika.jwd.modul3.cinema.model.Film;
import com.ftninformatika.jwd.modul3.cinema.repository.FilmRepository;
import com.ftninformatika.jwd.modul3.cinema.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaFilmService implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Override
	public Film findOne(Long id) {
		Optional<Film> film=filmRepository.findById(id);
		if(film.isPresent()) {
			return film.get();
		} 
		return null;
	}

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Page<Film> findAll(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    @Override
    public Film save(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film update(Film film) {
        return filmRepository.save(film);
    }

    @Override
    //Kada se obrise film obrisace se i sve projekcije koje su vezane za taj film
    public Film delete(Long id) {
        Optional<Film> film = filmRepository.findById(id);
        if(film.isPresent()){
            filmRepository.deleteById(id);
            return film.get();
        }
        return null;
    }

    @Override
    public Page<Film> find(String naziv, Long zanrId, Integer trajanjeOd, Integer trajanjeDo, int page) {
        if (naziv == null) {
            naziv = "";
        }

        if (trajanjeOd == null) {
            trajanjeOd = 0;
        }

        if (trajanjeDo == null) {
            trajanjeDo = Integer.MAX_VALUE;
        }

        if(zanrId == null){
            return filmRepository.findByNazivIgnoreCaseContainsAndTrajanjeBetween(naziv,trajanjeOd,trajanjeDo, PageRequest.of(page, 4) );
        }
        return filmRepository.findByNazivIgnoreCaseContainsAndTrajanjeBetweenAndZanroviId(naziv,trajanjeOd,trajanjeDo,zanrId,  PageRequest.of(page, 4));
    }
    @Override
    public List<Film> findByZanrId(Long zanrId) {
        return filmRepository.findByZanroviId(zanrId);
    }
}

package com.ftninformatika.jwd.modul3.cinema.service.impl;

import com.ftninformatika.jwd.modul3.cinema.model.Zanr;
import com.ftninformatika.jwd.modul3.cinema.repository.ZanrRepository;
import com.ftninformatika.jwd.modul3.cinema.service.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaZanrService implements ZanrService {


    @Autowired
    private ZanrRepository zanrRepository;

    @Override
    public Zanr findOne(Long id) {
        return zanrRepository.findOneById(id);
    }

    @Override
    public Page<Zanr> findAll(int pageNo) {
        return zanrRepository.findAll(PageRequest.of(pageNo, 4));
    }

    @Override
    public List<Zanr> find(List<Long> ids) {
        return zanrRepository.findByIdIn(ids);
    }

    @Override
    public Zanr save(Zanr zanr) {
        return zanrRepository.save(zanr);
    }

    @Override
    public Zanr update(Zanr zanr) {
        return zanrRepository.save(zanr);
    }

    @Override
    public Zanr delete(Long id) {
        Optional<Zanr> zanr = zanrRepository.findById(id);
        if(zanr.isPresent()){
            zanrRepository.deleteById(id);
            return zanr.get();
        }
        return null;
    }

    @Override
    public Page<Zanr> find(String naziv, int pageNo) {
        return zanrRepository.findByNazivIgnoreCaseContains(naziv, PageRequest.of(pageNo, 4));
    }
}

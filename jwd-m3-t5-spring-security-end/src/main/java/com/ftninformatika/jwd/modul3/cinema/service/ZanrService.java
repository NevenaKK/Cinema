package com.ftninformatika.jwd.modul3.cinema.service;

import com.ftninformatika.jwd.modul3.cinema.model.Zanr;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ZanrService {

    Zanr findOne(Long id);

    Page<Zanr> findAll(int pageNo);

    List<Zanr> find(List<Long> ids);

    Zanr save(Zanr zanr);

    Zanr update(Zanr zanr);

    Zanr delete(Long id);

    Page<Zanr> find(String naziv, int pageNo);
}
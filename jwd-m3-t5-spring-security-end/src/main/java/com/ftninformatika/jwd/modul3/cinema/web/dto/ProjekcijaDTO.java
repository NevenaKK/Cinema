package com.ftninformatika.jwd.modul3.cinema.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class ProjekcijaDTO {

    //@Positive(message = "Id mora biti pozitivan broj.")
    private Long id;

    @NotBlank(message = "Datum i vreme nisu zadati.")
    @Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]$", message = "Datum i vreme nisu validni.")
    private String datumIVreme;

    @Positive(message = "Id mora biti pozitivan broj.")
    @NotNull
    private Long filmId;

    private String filmNaziv;

    @NotNull(message = "Nije zadata sala.")
    private int sala;

    @NotBlank(message = "Tip projekcije nije zadat.")
    private String tip;

    @NotNull(message = "Nije zadata cena karte.")
    @Positive(message = "Cena karte nije pozitivan broj.")
    private double cenaKarte;

    public ProjekcijaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumIVreme() {
        return datumIVreme;
    }

    public void setDatumIVreme(String datumIVreme) {
        this.datumIVreme = datumIVreme;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getFilmNaziv() {
        return filmNaziv;
    }

    public void setFilmNaziv(String filmNaziv) {
        this.filmNaziv = filmNaziv;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public double getCenaKarte() {
        return cenaKarte;
    }

    public void setCenaKarte(double cenaKarte) {
        this.cenaKarte = cenaKarte;
    }
}

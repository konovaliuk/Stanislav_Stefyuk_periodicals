package dao.interfaces;

import entities.Periodicals;

import java.util.List;

public interface PeriodicalsInterface {
    Periodicals findById(long id);
    Periodicals findByName(String name);
    List<Periodicals> findByPubHouse(String pub_house);
    List<Periodicals> findAll();
    void createPeriodicals(Periodicals periodicals);
    void updatePeriodicals(long id, Periodicals periodicals);
    void deleteById(long id);
}

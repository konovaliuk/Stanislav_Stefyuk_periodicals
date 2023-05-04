package dao.implementations;

import dao.interfaces.PeriodicalsInterface;
import entities.Periodicals;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

public class PeriodicalsDao implements PeriodicalsInterface {

    private EntityManagerFactory entityManagerFactory;

    public PeriodicalsDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Periodicals findById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Periodicals.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Periodicals findByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT p FROM Periodicals p WHERE p.name = :name", Periodicals.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Periodicals> findByPubHouse(String pub_house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT p FROM Periodicals p WHERE p.pub_house = :pub_house", Periodicals.class)
                    .setParameter("pub_house", pub_house)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Periodicals> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT p FROM Periodicals p", Periodicals.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void createPeriodicals(Periodicals periodicals) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(periodicals);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updatePeriodicals(long id, Periodicals periodicals) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Periodicals oldPeriodicals = entityManager.find(Periodicals.class, id);
            if (oldPeriodicals != null) {
                oldPeriodicals.setName(periodicals.getName());
                oldPeriodicals.setInfo(periodicals.getInfo());
                oldPeriodicals.setPub(periodicals.getPub());
                oldPeriodicals.setPrice(periodicals.getPrice());
                entityManager.merge(oldPeriodicals);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Periodicals periodicals = entityManager.find(Periodicals.class, id);
            if (periodicals != null) {
                entityManager.remove(periodicals);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}

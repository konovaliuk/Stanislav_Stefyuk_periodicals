package dao.implementations;

import dao.interfaces.SubscriptionInterface;
import entities.Periodicals;
import entities.Subscription;
import entities.User;
import jakarta.persistence.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDao implements SubscriptionInterface {
    private final EntityManagerFactory entityManagerFactory;

    public SubscriptionDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Subscription findById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Subscription.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Subscription findByUserAndPeriodicals(long userId, long periodicalsId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Subscription> query = entityManager.createQuery(
                    "SELECT s FROM Subscription s WHERE s.user.id = :userId AND s.periodicals.id = :periodicalsId",
                    Subscription.class);
            query.setParameter("userId", userId);
            query.setParameter("periodicalsId", periodicalsId);
            List<Subscription> subscriptions = query.getResultList();
            return subscriptions.isEmpty() ? null : subscriptions.get(0);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Subscription> findByUser(long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Subscription> query = entityManager.createQuery(
                    "SELECT s FROM Subscription s WHERE s.user.id = :userId",
                    Subscription.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Subscription> findByUser(User user) {
        return findByUser(user.getId());
    }

    @Override
    public List<Subscription> findByPeriodicals(Periodicals periodicals) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Subscription> query = entityManager.createQuery(
                    "SELECT s FROM Subscription s WHERE s.periodicals.id = :periodicalsId",
                    Subscription.class);
            query.setParameter("periodicalsId", periodicals.getId());
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Subscription> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Subscription> query = entityManager.createQuery(
                    "SELECT s FROM Subscription s",
                    Subscription.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void createSubscription(Subscription subscription) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(subscription);
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
    public void updateSubscription(long id, Subscription subscription) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Subscription existingSubscription = entityManager.find(Subscription.class, id);
            if (existingSubscription != null) {
                existingSubscription.setUserId(subscription.getUserId());
                existingSubscription.setPeriodicalsId(subscription.getPeriodicalsId());
                existingSubscription.setSubEnd(subscription.getSubEnd());
                existingSubscription.setSubStart(subscription.getSubStart());
                entityManager.merge(existingSubscription);
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
            Subscription subscription = entityManager.find(Subscription.class, id);
            if (subscription != null) {
                entityManager.remove(subscription);
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

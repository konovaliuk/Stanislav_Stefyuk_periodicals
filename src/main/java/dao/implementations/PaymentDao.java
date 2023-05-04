package dao.implementations;

import dao.interfaces.PaymentInterface;
import entities.Payment;
import entities.Subscription;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao implements PaymentInterface {

    private EntityManagerFactory entityManagerFactory;

    public PaymentDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Payment findById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Payment payment = em.find(Payment.class, id);
        em.close();
        return payment;
    }

    @Override
    public List<Payment> findByUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Payment> query = em.createQuery("SELECT p FROM Payment p WHERE p.user = :user", Payment.class);
        query.setParameter("user", user);
        List<Payment> payments = query.getResultList();
        em.close();
        return payments;
    }

    @Override
    public List<Payment> findByStatus(String status) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Payment> query = em.createQuery("SELECT p FROM Payment p WHERE p.status = :status", Payment.class);
        query.setParameter("status", status);
        List<Payment> payments = query.getResultList();
        em.close();
        return payments;
    }

    @Override
    public List<Payment> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Payment> query = em.createQuery("SELECT p FROM Payment p", Payment.class);
        List<Payment> payments = query.getResultList();
        em.close();
        return payments;
    }

    @Override
    public void createPayment(Payment payment) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(payment);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void updatePayment(long id, Payment payment) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Payment existingPayment = em.find(Payment.class, id);
            if (existingPayment != null) {
                existingPayment.setAmount(payment.getAmount());
                existingPayment.setStatus(payment.getStatus());
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Payment payment = em.find(Payment.class, id);
            if (payment != null) {
                em.remove(payment);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteExpiredSubs() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Subscription> query = em.createQuery("SELECT s FROM Subscription s WHERE s.sub_end < NOW()", Subscription.class);
            List<Subscription> subscriptions = query.getResultList();
            for (Subscription subscription : subscriptions) {
                em.remove(subscription);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}

package dao.interfaces;

import entities.Periodicals;
import entities.Subscription;
import entities.User;

import java.util.List;

public interface SubscriptionInterface {
    Subscription findById(long id);

    Subscription findByUserAndPeriodicals(long userId, long periodicalsId);
    List<Subscription> findByUser(long userId);
    List<Subscription> findByUser(User user);
    List<Subscription> findByPeriodicals(Periodicals periodicals);
    List<Subscription> findAll();
    void createSubscription(Subscription subscription);
    void updateSubscription(long id, Subscription subscription);
    void deleteById(long id);
}

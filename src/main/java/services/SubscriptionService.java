package services;

import connection.DBConnection;
import dao.implementations.PeriodicalsDao;
import dao.implementations.SubscriptionDao;
import dao.implementations.UserDao;
import entities.Periodicals;
import entities.Subscription;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionService {

    public List<Subscription> findByUser(long userId) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        List<Subscription> subscriptionList = subscriptionCon.findByUser(userId);

        subscriptionCon.closeConnection();

        return subscriptionList;
    }

    public List<Subscription> findByUser(String email) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        UserDao userCon = new UserDao(conn);
        long userId = userCon.findByEmail(email).getId();
        List<Subscription> subscriptionList = subscriptionCon.findByUser(userId);

        subscriptionCon.closeConnection();
        userCon.closeConnection();

        return subscriptionList;
    }

    public Subscription findByUserAndPeriodicals(long userId, long periodicalsId) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        Subscription subscription = subscriptionCon.findByUserAndPeriodicals(userId, periodicalsId);

        subscriptionCon.closeConnection();

        return subscription;
    }

    public float getAmount(long subscriptionId) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        PeriodicalsDao periodicalsDao = new PeriodicalsDao(conn);
        Subscription subscription = subscriptionCon.findById(subscriptionId);
        float price = periodicalsDao.findById(subscription.getPeriodicalsId()).getPrice();
        long monthMillis = 1000L *60*60*24*30;
        float amount = (subscription.getSubEnd().getTime()-subscription.getSubStart().getTime())*price/monthMillis;

        subscriptionCon.closeConnection();
        periodicalsDao.closeConnection();

        return amount;
    }
//    public boolean addSubscription(long periodicalsId, long userId, Date subStart, Date subEnd) throws SQLException, IOException {
//        Connection conn = DBConnection.getConnection();
//        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
//
//        subscriptionCon.createSubscription(new Subscription(periodicalsId, userId, subStart, subEnd));
//        Subscription subscription = subscriptionCon.findByUserAndPeriodicals(userId, periodicalsId);
//        subscriptionCon.closeConnection();
//        if(subscription.getId()!=0){
//            return true;}
//        else{return false;}
//    }

    public boolean addSubscription(Subscription subscription) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);

        subscriptionCon.createSubscription(subscription);

        subscriptionCon.closeConnection();

        return true;
    }

    public boolean updateSubscription(long id, Subscription subscription) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        subscriptionCon.updateSubscription(id, subscription);
        Subscription result = subscriptionCon.findByUserAndPeriodicals(subscription.getUserId(), subscription.getPeriodicalsId());

        subscriptionCon.closeConnection();

        return result.getId() != 0;
    }

    public void deleteSubscription(Long subscriptionId) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        subscriptionCon.deleteById(subscriptionId);

        subscriptionCon.closeConnection();

    }
}

package dao.implementations;

import dao.interfaces.SubscriptionInterface;
import entities.Periodicals;
import entities.Subscription;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDao implements SubscriptionInterface {
    private final Connection connection;

    public SubscriptionDao(Connection connection) throws SQLException{
        this.connection = connection;
    }

    public Subscription getSubscription(ResultSet resultSet) throws SQLException{
        long id = resultSet.getLong("id");
        long periodicals_id = resultSet.getLong("periodicals_id");
        long user_id = resultSet.getLong("user_id");
        Date sub_start = resultSet.getDate("sub_start");
        Date sub_end = resultSet.getDate("sub_end");
        return new Subscription(id, periodicals_id, user_id, sub_start, sub_end);
    }
    @Override
    public Subscription findById(long id) {
        Subscription subscription = new Subscription();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM subscription WHERE subscription.id = ?");
            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();

            resultSet.next();
            subscription = getSubscription(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return subscription;
    }

    @Override
    public Subscription findByUserAndPeriodicals(long userId, long periodicalsId) {
        Subscription subscription = new Subscription();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM subscription WHERE subscription.user_id = ? AND subscription.periodicals_id = ?");
            stmt.setLong(1, userId);
            stmt.setLong(2, periodicalsId);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                subscription = getSubscription(resultSet);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return subscription;
    }

    @Override
    public List<Subscription> findByUser(long userId) {
        List<Subscription> subscriptionList= new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM subscription WHERE subscription.user_id = ?");
            stmt.setLong(1, userId);
            resultSet = stmt.executeQuery();

            while(resultSet.next()){
                Subscription subscription = getSubscription(resultSet);
                subscriptionList.add(subscription);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findByUser(User user) {
        List<Subscription> subscriptionList= new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM subscription WHERE subscription.user_id = ?");
            stmt.setLong(1, user.getId());
            resultSet = stmt.executeQuery();

            while(resultSet.next()){
                Subscription subscription = getSubscription(resultSet);
                subscriptionList.add(subscription);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findByPeriodicals(Periodicals periodicals) {
        List<Subscription> subscriptionList= new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM subscription WHERE subscription.periodicals_id = ?");
            stmt.setLong(1, periodicals.getId());
            resultSet = stmt.executeQuery();

            while(resultSet.next()){
                Subscription subscription = getSubscription(resultSet);
                subscriptionList.add(subscription);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findAll() {
        List<Subscription> subscriptionList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `periodicals`.`subscription`");
            while(resultSet.next()){
                Subscription subscription = getSubscription(resultSet);
                subscriptionList.add(subscription);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }

        return subscriptionList;
    }

    @Override
    public void createSubscription(Subscription subscription) {
        if (subscription.getId() != 0){
            throw new IllegalArgumentException("Id have not to be specified");
        }
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("INSERT INTO `periodicals`.`subscription` (`periodicals_id`,`user_id`, `sub_start`, `sub_end`) VALUES (?, ?, ?, ?)");
            stmt.setLong(1, subscription.getPeriodicalsId());
            stmt.setLong(2, subscription.getUserId());
            stmt.setDate(3, subscription.getSubStart());
            stmt.setDate(4, subscription.getSubEnd());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    @Override
    public void updateSubscription(long id, Subscription subscription) {
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("UPDATE `periodicals`.`subscription` SET `periodicals_id` = ?, `user_id` = ?, `sub_start` = ?, `sub_end` = ? WHERE `id` = ?");
            stmt.setLong(1, subscription.getPeriodicalsId());
            stmt.setLong(2, subscription.getUserId());
            stmt.setDate(3, subscription.getSubStart());
            stmt.setDate(4, subscription.getSubEnd());
            stmt.setLong(5, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteById(long id) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM `periodicals`.`subscription` WHERE subscription.id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("An error occurred while closing the connection: " + e.getMessage());
            }
        }
    }
}

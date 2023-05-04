package dao.implementations;

import dao.interfaces.PaymentInterface;
import entities.Payment;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao implements PaymentInterface {

    private final Connection connection;
    public PaymentDao(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public Payment getPayment(ResultSet resultSet) throws SQLException{
        long id = resultSet.getLong("id");
        float amount = resultSet.getFloat("amount");
        long subscription_id = resultSet.getLong("subscription_id");
        String status = resultSet.getString("status");
        return new Payment(id, amount, subscription_id, status);
    }

    @Override
    public Payment findById(long id) {
        Payment payment = new Payment();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM payment WHERE payment.id = ?");
            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();

            resultSet.next();
            payment = getPayment(resultSet);
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
        return payment;
    }

    @Override
    public List<Payment> findByUser(User user) {
        List<Payment> paymentList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try{
            stmt = connection.prepareStatement("SELECT * FROM `periodicals`.`payment` p INNER JOIN `periodicals`.`subscription` s ON p.subscription_id = s.id WHERE s.user_id = ?");
            stmt.setLong(1, user.getId());
            resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Payment payment = getPayment(resultSet);
                paymentList.add(payment);
            }
        }catch (SQLException e){
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

        return paymentList;
    }


    @Override
    public List<Payment> findByStatus(String status) {
        List<Payment> paymentList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM `periodicals`.`payment` WHERE payment.status = ?");
            stmt.setString(1, status);
            resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Payment payment = getPayment(resultSet);
                paymentList.add(payment);
            }
        }catch (SQLException e){
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

        return paymentList;
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> paymentList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `periodicals`.`payment`");
            while(resultSet.next()){
                Payment payment = getPayment(resultSet);
                paymentList.add(payment);
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

        return paymentList;
    }

    @Override
    public void createPayment(Payment payment) {
        if (payment.getId() != 0){
            throw new IllegalArgumentException("Id have not to be specified");
        }
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("INSERT INTO `periodicals`.`payment` (`amount`, `subscription_id`, `status`) VALUES (?, ?, ?)");
            stmt.setFloat(1, payment.getAmount());
            stmt.setLong(2, payment.getSubscriptionId());
            stmt.setString(3, payment.getStatus());
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
    public void updatePayment(long id, Payment payment) {
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("UPDATE `periodicals`.`payment` SET `amount` = ?, `subscription_id` = ?, `status` = ? WHERE `id` = ?");
            stmt.setFloat(1, payment.getAmount());
            stmt.setLong(2, payment.getSubscriptionId());
            stmt.setString(3, payment.getStatus());
            stmt.setLong(4, id);
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
            stmt = connection.prepareStatement("DELETE FROM `periodicals`.`payment` WHERE payment.id = ?");
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

    @Override
    public void deleteExpiredSubs() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM payments WHERE subscription_id IN (SELECT id FROM subscription WHERE sub_end < NOW());");
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
        PreparedStatement stmt2 = null;
        try {
            stmt2 = connection.prepareStatement("DELETE FROM subscription WHERE sub_end < NOW();");
            stmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                if (stmt2 != null) {
                    stmt2.close();
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

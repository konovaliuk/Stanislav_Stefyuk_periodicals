package dao.implementations;

import dao.interfaces.PeriodicalsInterface;
import entities.Periodicals;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalsDao implements PeriodicalsInterface {

    private final Connection connection;

    public PeriodicalsDao(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public Periodicals getPeriodicals(ResultSet resultSet) throws SQLException{
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String pub_house = resultSet.getString("pub_house");;
        String info = resultSet.getString("info");
        float price = resultSet.getFloat("price");;
        return new Periodicals(id, name, pub_house, info, price);
    }

    @Override
    public List<Periodicals> findAll(){
        List<Periodicals> periodicalsList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `periodicals`.`periodicals`");
            while(resultSet.next()){
                Periodicals periodicals = getPeriodicals(resultSet);
                periodicalsList.add(periodicals);
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

        return periodicalsList;

    }

    @Override
    public void createPeriodicals(Periodicals periodicals) {
        if (periodicals.getId() != 0){
            throw new IllegalArgumentException("Id have not to be specified");
        }
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("INSERT INTO `periodicals`.`periodicals` (`name`,`pub_house`, `info`, `price`) VALUES (?, ?, ?, ?)");
            stmt.setString(1, periodicals.getName());
            stmt.setString(2, periodicals.getPub());
            stmt.setString(3, periodicals.getInfo());
            stmt.setFloat(4, periodicals.getPrice());
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
    public void updatePeriodicals(long id, Periodicals periodicals) {
        if (periodicals.getId() != 0){
            throw new IllegalArgumentException("Id have not to be specified");
        }
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("UPDATE `periodicals`.`periodicals` SET `name`=?, `pub_house`=?, `info`=?, `price`=? WHERE `id` = ?");
            stmt.setString(1, periodicals.getName());
            stmt.setString(2, periodicals.getPub());
            stmt.setString(3, periodicals.getInfo());
            stmt.setFloat(4, periodicals.getPrice());
            stmt.setFloat(5, id);
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
            stmt = connection.prepareStatement("DELETE FROM `periodicals`.`periodicals` WHERE periodicals.id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
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
    public List<Periodicals> findByPubHouse(String pub_house){
        List<Periodicals> periodicalsList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM periodicals WHERE periodicals.sub_house = ? ");
            stmt.setString(1, pub_house);
            resultSet = stmt.executeQuery();

            while(resultSet.next()){
                Periodicals periodicals = getPeriodicals(resultSet);
                periodicalsList.add(periodicals);
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

        return periodicalsList;

    }

    @Override
    public Periodicals findById(long id){
        Periodicals periodicals = new Periodicals();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM periodicals WHERE periodicals.id = ?");
            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();

            resultSet.next();
            periodicals = getPeriodicals(resultSet);
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
        return periodicals;
    }

    @Override
    public Periodicals findByName(String name){
        Periodicals periodicals = new Periodicals();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            stmt = connection.prepareStatement("SELECT * FROM periodicals WHERE periodicals.name = ?");
            stmt.setString(1, name);
            resultSet = stmt.executeQuery();

            resultSet.next();
            periodicals = getPeriodicals(resultSet);
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
        return periodicals;
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

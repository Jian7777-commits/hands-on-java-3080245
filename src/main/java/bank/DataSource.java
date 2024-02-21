package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect (){
    String dbFile = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(dbFile);
      System.out.println("database Connected");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return connection;
  }
  
  public static Customer getCustomer(String username){

    String sqlQuery = "select * from customers where username = ?";

    Customer customer = null;
 
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
          statement.setString(1, username);
          try (ResultSet resultSet = statement.executeQuery();) {
            customer = new Customer(resultSet.getInt("id"),
            resultSet.getString("name"), 
            resultSet.getString("username"), 
            resultSet.getString("password"), 
            resultSet.getInt("account_id"));
          } catch (SQLException e) {
            e.printStackTrace();
          }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return customer;

  }

  public static Account getAccount(int accountId){
    String sqlQuery = "select * from accounts where id = ?";
    Account account = null;

    try(Connection connection = connect();
    PreparedStatement statement = connection.prepareStatement(sqlQuery)){
      statement.setInt(1, accountId);
      try(ResultSet resultSet = statement.executeQuery()){
        account = new Account(resultSet.getInt("id"),
        resultSet.getString("type"),
        resultSet.getDouble("balance"));
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }

    return account;
  }

  public static void updateAccountBalance(int accountId, double balance){
    String sql = "update account set balance = ? where id = ?";
    
    try (
      Connection connection = connect();
      PreparedStatement statement = connection.prepareStatement(sql);
    ) {
      statement.setDouble(1, balance);
      statement.setInt(2, accountId);

      statement.executeUpdate();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

}


package net.novascript.pos.DAO;

import net.novascript.pos.DBConnection.DBConnection;
import net.novascript.pos.Model.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

     public boolean userLogin(String username, String password) throws SQLException, ClassNotFoundException {
         Connection connection = DBConnection.getDbConnection().getConnection();
         String sql = "SELECT * FROM user WHERE Username = ? AND Password = ?";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setString(1, username);
         preparedStatement.setString(2, password);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()) {
             return true;
         } else {
             return false;
         }
     }

    public boolean registerUser(String name, String username, String password, String role) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        String sql = "INSERT INTO user (Name, Username, Password, Role) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, role);
        boolean isadded = preparedStatement.executeUpdate()>0;
        return isadded;
    }

    public Product getProductById(String productId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        String sql = "SELECT * FROM product WHERE product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, productId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String productName = resultSet.getString("product_name");
            BigDecimal productPrice = resultSet.getBigDecimal("product_price");
            String productCategory = resultSet.getString("product_category");

            return new Product(productId, productName, productPrice, productCategory);
        } else {
            return null;
        }
    }



}

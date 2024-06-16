package net.novascript.pos.DAO;

import net.novascript.pos.DBConnection.DBConnection;
import net.novascript.pos.Model.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public boolean addProductDB(String productId, String productName, BigDecimal productPrice, String productCat) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        String sql = "INSERT INTO product (product_id, product_name, product_price,product_category) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, productId);
        preparedStatement.setString(2, productName);
        preparedStatement.setBigDecimal(3, productPrice);
        preparedStatement.setString(4, productCat);
        boolean isadded = preparedStatement.executeUpdate()>0;
        return isadded;
    }

    public boolean editProductDB(String productId, String productName, BigDecimal productPrice, String productCat) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        String sql = "UPDATE product SET product_name = ?, product_price = ?, product_category = ? WHERE product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, productName);
        preparedStatement.setBigDecimal(2, productPrice);
        preparedStatement.setString(3, productCat);
        preparedStatement.setString(4, productId);

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }
    public List<Product> getAllProducts() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        String sql = "SELECT * FROM product";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            String productId = resultSet.getString("product_id");
            String productName = resultSet.getString("product_name");
            BigDecimal productPrice = resultSet.getBigDecimal("product_price");
            String productCategory = resultSet.getString("product_category");

            Product product = new Product(productId, productName, productPrice, productCategory);
            products.add(product);
        }
        return products;
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

    public boolean deleteProductDB(String productId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        String sql = "DELETE FROM product WHERE product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, productId);

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }
}

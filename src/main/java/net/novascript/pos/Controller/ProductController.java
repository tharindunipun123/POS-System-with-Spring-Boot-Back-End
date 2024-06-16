package net.novascript.pos.Controller;

import net.novascript.pos.DAO.ProductDAO;
import net.novascript.pos.Model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin
public class ProductController {
        @PostMapping ("/addproducts")
       public ResponseEntity<String> addProducts(@RequestBody Map<String,String> payload) throws SQLException, ClassNotFoundException {
           String productId = payload.get("productId");
           String productName = payload.get("productName");
           String productPriceString = payload.get("productPrice");
           String productCat = payload.get("productCat");
            BigDecimal productPrice = new BigDecimal(productPriceString);
           if (new ProductDAO().addProductDB(productId,productName,productPrice,productCat)){
               return ResponseEntity.ok("successful");
            }else {
               return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unsuccessful");
           }

       }

    @PutMapping("/editproduct")
    public ResponseEntity<String> editProduct(@RequestBody Map<String, String> payload) throws SQLException, ClassNotFoundException {
        String productId = payload.get("productId");
        String productName = payload.get("productName");
        String productPriceString = payload.get("productPrice");
        String productCat = payload.get("productCat");
        BigDecimal productPrice = new BigDecimal(productPriceString);

        if (new ProductDAO().editProductDB(productId, productName, productPrice, productCat)) {
            return ResponseEntity.ok("successful");
        } else {
            return ResponseEntity.ok("unsuccessful");
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() throws SQLException, ClassNotFoundException {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) throws SQLException, ClassNotFoundException {
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/deleteproduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) throws SQLException, ClassNotFoundException {
        if (new ProductDAO().deleteProductDB(productId)) {
            return ResponseEntity.ok("successful");
        } else {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unsuccessful");
        }
    }

}

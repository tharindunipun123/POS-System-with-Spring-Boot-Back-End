package net.novascript.pos.Controller;

import net.novascript.pos.DAO.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/v1")
@CrossOrigin
public class UserController {

    @PostMapping("/loginuser")
    public ResponseEntity<String> logUser(@RequestBody Map<String, String> payload) throws SQLException, ClassNotFoundException {

        String username = payload.get("username");
        String password = payload.get("password");
        UserDAO user = new UserDAO();
        boolean isavailble = user.userLogin(username, password);

        if (isavailble) {
            return ResponseEntity.ok("successful");
        } else {
             return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unsuccessful");
        }

    }
    @PostMapping ("/userregister")
    public ResponseEntity<String> userRegister(@RequestBody Map<String, String> payload) throws SQLException, ClassNotFoundException {
        String name = payload.get("name");
        String username = payload.get("username");
        String password = payload.get("password");
        String role = payload.get("role");
         boolean isadded = new UserDAO().registerUser(name,username,password,role);
        System.out.println(isadded);
         if (isadded) {
             return ResponseEntity.ok("successful");
         }else {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unsuccessful");
         }
    }




}

package com.example.bikash.SpringTest.Controller;

import com.example.bikash.SpringTest.Model.User;
import com.example.bikash.SpringTest.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/public")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create/user")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try{
           User saved = userService.saveUser(user);
           return  new ResponseEntity<>(saved, HttpStatus.OK);
        }
       catch (Exception e){

            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
       }
    }


    @PostMapping("/test/upload/excel")
    public ResponseEntity<?> uploadExcelTest(@RequestParam(name = "file") MultipartFile file) {
        log.info("Received file: " + file.getOriginalFilename());
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get/user/{username}")
    public  ResponseEntity<?> userformusername(@PathVariable String username) {

      User user =   this.userService.findByUsernameQuery(username);
       return  new ResponseEntity<>(user,HttpStatus.OK);
    }
}

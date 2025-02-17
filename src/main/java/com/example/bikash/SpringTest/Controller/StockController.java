
package com.example.bikash.SpringTest.Controller;

import com.example.bikash.SpringTest.Model.Stock;
import com.example.bikash.SpringTest.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/stock")
public class StockController {


    @Autowired
    private StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<?> addStock(@RequestBody Stock stock) {

        Stock stock1 = stockService.addStock(stock);
        return new ResponseEntity<>(stock1, HttpStatus.OK);
    }

    @GetMapping("/{eventName}")
    public ResponseEntity<?> getStockByEvent(@PathVariable String eventName) {


        try {
            Stock savedStock = stockService.getStockByEvent(eventName);
            return new ResponseEntity<>(savedStock, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}


package com.example.bikash.SpringTest.Service;

import com.example.bikash.SpringTest.Exceptions.ApiResponse;
import com.example.bikash.SpringTest.Exceptions.ResourceNotFoundException;
import com.example.bikash.SpringTest.Model.Stock;
import com.example.bikash.SpringTest.Repos.StockRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.AlreadyConnectedException;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public Stock addStock(Stock stock) {
        Optional<Stock> existingStock = stockRepository.findByEventName(stock.getEventName());
        if (existingStock.isPresent()) {
            throw new ValidationException("Stock already exists");
        }
        Stock savedStock = stockRepository.save(stock);
        return savedStock;
    }

    public Stock getStockByEvent(String eventName) {
        Optional<Stock> stock = stockRepository.findByEventName(eventName);
        if (stock.isPresent()) {
            return  stock.get();
        }
        else {
             throw new ValidationException("Stock not found");
        }

    }
}


package com.example.bikash.SpringTest.Repos;

import com.example.bikash.SpringTest.Model.Stock;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.SimpleTimeZone;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByEventName(String eventName);

    //pessimist approach to lock the row
    // we will make  one method to read the role and lock that role for the users that is currently  press book


    // Pessimist lock the row for the users
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select  s from Stock  s where  s.eventName = :eventName ")
    Optional<Stock> findByEventNameForBooking( @Param("eventName") String eventName);



    // atomic sql updates way to grab the concurrency
    @Modifying
    @Transactional
    @Query("update Stock  s SET s.availableTickets = s.availableTickets-1 where s.eventName = :eventName and  s.availableTickets>0")
    int decrementTicket(@Param("eventName")String eventName);

}

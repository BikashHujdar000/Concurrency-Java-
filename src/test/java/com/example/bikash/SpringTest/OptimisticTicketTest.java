package com.example.bikash.SpringTest;

import com.example.bikash.SpringTest.Service.TicketService;
import com.example.bikash.SpringTest.Service.TicketServiceForOptimisticLocaking;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
public class OptimisticTicketTest {


    //          ** Notes
    // optimistic failed for the test like if there are 2 tickets then so better pessimistic

    //optimistic generally used for the update issue solver and all


    private  final TicketServiceForOptimisticLocaking ticketService;

    @Autowired
    public OptimisticTicketTest(TicketServiceForOptimisticLocaking ticketService) {
        this.ticketService = ticketService;
    }

    @Test
    public void testConcurrentBooking() throws InterruptedException {
        int numberOfUsers = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfUsers);

        java.util.function.Consumer<Long> bookTicketTest = (Long userId)-> {
            try {
                startLatch.await();
                ticketService.bookTicket(userId, "testOP");
                log.info(Thread.currentThread().getName() + " successfully booked a ticket for test" + userId + "!");
            } catch (Exception e) {
                log.info(Thread.currentThread().getName() + " failed to book a ticket for test" + userId + "!");
            } finally {
                countDownLatch.countDown();
            }
        };

        for (int i = 0; i < numberOfUsers; i++) {
            Long userId = (long) (i + 1);
            executorService.execute(() -> bookTicketTest.accept(userId));
        }

        startLatch.countDown();
        countDownLatch.await();
        executorService.shutdown();
    }
}

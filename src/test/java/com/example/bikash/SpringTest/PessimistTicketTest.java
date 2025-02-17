package com.example.bikash.SpringTest;

import com.example.bikash.SpringTest.Service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
public class PessimistTicketTest {

    private final TicketService ticketService;

    @Autowired
    public PessimistTicketTest(TicketService ticketService) {
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
                ticketService.bookTicket(userId, "test");
                log.info(Thread.currentThread().getName() + " successfully booked a ticket for test" + userId + "!");
            } catch (Exception e) {
                log.info(Thread.currentThread().getName() + " failed to book a ticket for test" + userId + "!");
            } finally {
                countDownLatch.countDown();
            }
        };

        // Launching multiple users booking tickets concurrently
        for (int i = 0; i < numberOfUsers; i++) {
            Long userId = (long) (i + 1);
            executorService.execute(() -> bookTicketTest.accept(userId));
        }


        startLatch.countDown();
        countDownLatch.await();
        executorService.shutdown();
    }


}



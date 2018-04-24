package com.oldmanw.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    @Test
    public void test1() {
        String name = "oldmanw";
        String password = "123456";
        log.debug("debug...");
        log.info("info...");
        log.info("name: {}, info: {}", name, password);
        log.error("error");
    }

}

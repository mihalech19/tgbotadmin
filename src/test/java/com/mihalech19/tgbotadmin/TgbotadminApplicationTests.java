package com.mihalech19.tgbotadmin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TgbotadminApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void LoadSecurityContext(){

        assertNotNull(SecurityContextHolder.getContext()) ;
    }


}

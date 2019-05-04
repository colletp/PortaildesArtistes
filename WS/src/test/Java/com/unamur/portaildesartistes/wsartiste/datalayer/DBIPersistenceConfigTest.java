package com.unamur.portaildesartistes.wsartiste.datalayer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class DBIPersistenceConfigTest {

    DBIPersistenceConfig test;

    @BeforeEach
    void setUp() {
        test=new DBIPersistenceConfig();
    }


    @Test
    void jodaModule() {
    }

    @Test
    void dbiBean() {
    }

    @AfterEach
    void tearDown() {
        test=null;
    }
}
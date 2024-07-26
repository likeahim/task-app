package com.crud.tasks.trello.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloConfigTest {

    @Autowired
    TrelloConfig trelloConfig;

    @Test
    void shouldReturnConfigurationSettings() {
        //Given
        //When
        //Then
        assertEquals("https://api.trello.com/1", trelloConfig.getTrelloApiEndpoint());
        assertNotEquals("some_user", trelloConfig.getTrelloUsername());
        assertEquals("michapanko1", trelloConfig.getTrelloUsername());
    }

}
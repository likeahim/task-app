package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    TrelloMapper trelloMapper;

    private static TrelloList trelloList;
    private static TrelloListDto trelloListDto;
    private static List<TrelloList> listTrelloList = new ArrayList<>();
    private static List<TrelloListDto> listTrelloListDto = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        trelloList = new TrelloList("list_id_test", "list_name_test", false);
        trelloListDto = new TrelloListDto("list_id_test", "list_name_test", false);
        listTrelloList.add(trelloList);
        listTrelloListDto.add(trelloListDto);
    }

    @Nested
    class NestedBoardsMappingTest {

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("board_test_id", "board_test_name", listTrelloListDto);
        TrelloBoard trelloBoard = new TrelloBoard("board_test_id", "board_test_name", listTrelloList);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();

        @BeforeEach
        void setUp() {
            trelloBoards.add(trelloBoard);
            trelloBoardDtos.add(trelloBoardDto);
        }
        @Test
        void shouldMapToBoards() {
            //Given
            //When
            List<TrelloBoard> returnedList = trelloMapper.mapToBoards(List.of(trelloBoardDto));

            //Then
            assertEquals(1, returnedList.size());
            assertFalse(returnedList.isEmpty());
            assertEquals("list_id_test", returnedList.get(0).getLists().get(0).getId());
            assertEquals("board_test_name", returnedList.get(0).getName());
        }

        @Test
        void shouldMapToBoardsDto() {
            //Given
            //When
            List<TrelloBoardDto> returnedListDto = trelloMapper.mapToBoardsDto(trelloBoards);

            //Then
            assertEquals(1, returnedListDto.size());
            assertFalse(returnedListDto.isEmpty());
            assertEquals("list_id_test", returnedListDto.get(0).getLists().get(0).getId());
            assertEquals("board_test_name", returnedListDto.get(0).getName());
        }
    }

    @Test
    void shouldMapToList() {
        //Given
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(listTrelloListDto);
        //Then
        assertEquals(1, trelloLists.size());
        assertEquals("list_id_test", trelloLists.get(0).getId());
        assertEquals("list_name_test", trelloLists.get(0).getName());
    }

    @Test
    void shouldMapToListDto() {
        //Given
        //When
        List<TrelloListDto> trelloListsDto = trelloMapper.mapToListDto(listTrelloList);
        //Then
        assertEquals(1, trelloListsDto.size());
        assertEquals("list_id_test", trelloListsDto.get(0).getId());
        assertEquals("list_name_test", trelloListsDto.get(0).getName());
    }

    @Nested
    class NestedCardsMappingTests {
        TrelloCard trelloCard;
        TrelloCardDto trelloCardDto;

        @BeforeEach
        void setUp() {
            trelloCard = new TrelloCard("card_name_test", "card_description_test", "card_pos_test", trelloList.getId());
            trelloCardDto = new TrelloCardDto("card_name_test", "card_description_test", "card_pos_test", trelloListDto.getId());
        }
        @Test
        void shouldMapToCardDto() {
            //Given
            //When
            TrelloCardDto returnedCardDto = trelloMapper.mapToCardDto(trelloCard);

            //Then
            assertEquals("card_name_test", returnedCardDto.getName());
            assertEquals("list_id_test", returnedCardDto.getListId());
        }

        @Test
        void shouldMapToCard() {
            //Given
            //When
            TrelloCard returnedCard = trelloMapper.mapToCard(trelloCardDto);

            //Then
            assertEquals("card_name_test", returnedCard.getName());
            assertEquals("list_id_test", returnedCard.getListId());
        }
    }
}
package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TrelloCardDto {

    private String listId;
    private String name;
    private String desc;
    private String pos;
    private Badges badges;
}

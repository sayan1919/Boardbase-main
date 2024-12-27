package com.javaproject.beans;

import java.util.List;

import lombok.Data;

@Data
public class BoardBase {

    private Long id;
    private String name;
    private int level;
    private int minPlayers;
    private String maxPlayers;
    private String gameType;

    private List<Review> reviews;

}

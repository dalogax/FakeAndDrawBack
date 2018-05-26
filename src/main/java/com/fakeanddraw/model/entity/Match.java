package com.fakeanddraw.model.entity;

import java.util.List;

public class Match{
    private int id;
    private MatchStatus status;
    private List<Drawing> draws;

    public enum MatchStatus {
        JOIN, DRAW, TITLE, VOTE,
        DRAW_RESULTS, MATCH_RESULTS 
    }
}
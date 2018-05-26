package com.fakeanddraw.model.entity;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class Match{
	private int id;
	private MatchStatus status;
    private List<Drawing> draws;
    private Set<Player> players;

    public enum MatchStatus {
        JOIN, DRAW, TITLE, VOTE,
        DRAW_RESULTS, MATCH_RESULTS 
    }
}
package com.fakeanddraw.model.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Match{
	@Getter @Setter private int id;
	@Getter @Setter private MatchStatus status;
    @Getter @Setter private List<Drawing> draws;

    public enum MatchStatus {
        JOIN, DRAW, TITLE, VOTE,
        DRAW_RESULTS, MATCH_RESULTS 
    }
}
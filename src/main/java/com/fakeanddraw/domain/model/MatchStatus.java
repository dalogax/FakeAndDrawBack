package com.fakeanddraw.domain.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MatchStatus {
  JOIN, DRAW, TITLE, VOTE, DRAW_RESULTS, MATCH_RESULTS, FINISHED;

  public static MatchStatus findByName(String name) {
    for (MatchStatus t : values()) {
      if (t.name().equals(name)) {
        return t;
      }
    }
    return null;
  }
}

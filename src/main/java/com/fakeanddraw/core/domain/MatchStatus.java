package com.fakeanddraw.core.domain;

import lombok.AllArgsConstructor;

/*
 * Represents the status of a match, see full game status flow here:
 * https://www.lucidchart.com/documents/edit/ed97e83d-74f1-4b0c-b7ee-f33424ef058a
 */
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

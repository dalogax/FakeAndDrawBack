package com.fakeanddraw.entrypoints.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinMessage {

    private String roomCode;
    private String name;
}

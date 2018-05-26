package com.fakeanddraw.model.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Title{
	@Getter @Setter private int id;
	@Getter @Setter private Player player;
	@Getter @Setter private Drawing drawing;
	@Getter @Setter private boolean isOriginal;
	@Getter @Setter private List<Player> votes;
}
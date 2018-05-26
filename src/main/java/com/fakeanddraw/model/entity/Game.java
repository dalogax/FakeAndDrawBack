package com.fakeanddraw.model.entity;

import java.util.List;

public class Game{
	private Long id;
	private String code;
	private Client master;
	private List<Match> matches;

	public Client getMaster()
	{
		return this.master;
	}

	public void setMaster(Client master)
	{
		this.master = master;
	}

	
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return this.code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}


}
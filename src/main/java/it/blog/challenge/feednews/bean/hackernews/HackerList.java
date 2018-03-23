package it.blog.challenge.feednews.bean.hackernews;

import java.util.ArrayList;
import java.util.List;

public class HackerList {

	private List<Hacker> hacker;
	
	public HackerList()
	{
		hacker = new ArrayList<Hacker>();
	}

	public List<Hacker> getHacker() {
		return hacker;
	}

	public void setHacker(List<Hacker> hacker) {
		this.hacker = hacker;
	}
}

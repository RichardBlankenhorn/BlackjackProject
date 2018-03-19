package com.skilldistillery.cards.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	
private List<Card> s = new ArrayList<>();
	
	public Deck() {
		Suit[] suits = Suit.values();
		Rank[] ranks = Rank.values();
		for (Suit s : suits) {
			for (Rank r : ranks) {
				Card c = new Card(s,r);
				this.s.add(c);
			}
		}
	}
	
	public int checkDeckSize() {
		return this.s.size();
	}
	
	public Card dealCard() {
		return this.s.remove(0);
	}
	
	public void shuffle() {
		Collections.shuffle(s);
	}
	
	public int getSize() {
		return this.s.size();
	}

}

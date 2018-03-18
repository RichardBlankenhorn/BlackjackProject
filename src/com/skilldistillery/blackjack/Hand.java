package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.cards.common.Card;

public class Hand {

	private List<Card> hand = new ArrayList<>();
	
	public Hand() {
		
	}

	public void addCard(Card card) {
		hand.add(card);
	}
	
	public void addHand(List<Card> hand) {
		this.hand = hand;
	}

	public List<Card> getCardsInHand() {
		return hand;
	}
	
	public int getSize() {
		return hand.size();
	}

	public int getValueOfHand() {
		int count = 0;
		for (int i = 0; i < hand.size(); i++) {
			count += hand.get(i).getValue();
		}
		return count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hand other = (Hand) obj;
		if (hand == null) {
			if (other.hand != null)
				return false;
		} else if (!hand.equals(other.hand))
			return false;
		return true;
	}

}

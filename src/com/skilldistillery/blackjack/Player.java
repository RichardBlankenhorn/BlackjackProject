package com.skilldistillery.blackjack;

import java.util.List;

import com.skilldistillery.cards.common.Card;

public class Player {

	private String name;
	private Hand hand;
	private double wallet;
	
	public Player() {
		
	}
	
	public Player(Hand hand) {
		this.hand = hand;
	}

	public Player(String name, Hand hand, double wallet) {
		super();
		this.name = name;
		this.hand = hand;
		this.wallet = wallet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getHand() {
		return hand.getCardsInHand();
	}
	
	public int getValueOfHand() {
		return hand.getValueOfHand();
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", hand=" + hand + ", wallet=" + wallet + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(wallet);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Player other = (Player) obj;
		if (hand == null) {
			if (other.hand != null)
				return false;
		} else if (!hand.equals(other.hand))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(wallet) != Double.doubleToLongBits(other.wallet))
			return false;
		return true;
	}

}

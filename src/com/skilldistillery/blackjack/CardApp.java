package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardApp {

	static Scanner kb = new Scanner(System.in);
	static private String numCards;
	static private List<Card> myCards = new ArrayList<>();
	static private Deck deck = new Deck();
	static private int count;

	public static void main(String[] args) {

		getNumberOfCards();
		deckShuffle();
		dealCards();
		printCards();
		printTotalCardValue();

	}

	public static void getNumberOfCards() {

		boolean v = true;
		int counter = 1;
		while (v) {
			System.out.print("How many cards would you like? ");
			numCards = kb.next();

			for (char c : numCards.toCharArray()) {
				if (!Character.isDigit(c)) {
					System.out.println("\nValue entered is not a number");
					continue;
				} else if (Character.isDigit(c)) {
					if (counter == numCards.toCharArray().length) {
						v = false;
					}

				}
			}

		}
	}

	private static void dealCards() {
		for (int i = 0; i < Integer.parseInt(numCards); i++) {
			myCards.add(deck.dealCard());
		}
	}

	public static void deckShuffle() {
		deck.shuffle();
	}

	public static void getTotalCardValue() {
		count = 0;
		for (int i = 0; i < myCards.size(); i++) {
			count += myCards.get(i).getValue();
		}
	}

	public static void printCards() {
		System.out.println(myCards);
	}

	public static void printTotalCardValue() {
		getTotalCardValue();
		System.out.println("The total value of the cards is " + count);
	}

}

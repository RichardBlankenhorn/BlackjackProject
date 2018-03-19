package com.skilldistillery.cards.blackjack;

import java.util.Scanner;
import com.skilldistillery.cards.common.Deck;

public class Game {

	static Scanner kb = new Scanner(System.in);

	private String response;
	private boolean bust;
	private Deck deck = new Deck();
	private Player player1 = new Player();
	private Player house = new Player();

	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

	private void run() {
		// Provide initial greeting
		greeting();

		// Obtain player name and their wallet size
		obtainNameAndWallet();

		// Continuous game play
		// Player will continue to be dealt hands until they choose to quite after a
		// hand
		while (true) {
			bust = false;
			gameMenu();
			if (Integer.parseInt(response) == 2) {
				System.out.println("Goodbye! Thanks for playing");
				break;
			}
			deckShuffle();
			dealCardsForPlayer();
			dealCardsForHouse();
			printPlayerCards();
			playerTurn();
			if (bust == true) {
				System.out.println("You busted! The house wins.");
				continue;
			}
			houseTurn();
			if (bust == true) {
				System.out.println("The dealer busted! You win!");
			}

		}
	}

	// This method will provide a menu to the player (hit or stay)
	// If the player hits, they will be dealt a new card
	// If their hand exceeds 21 in value, they bust and the bust variable is set to
	// true
	// Otherwise, a message will display stating it is the dealers turn
	public void playerTurn() {
		while (true) {
			optionMenu();
			if (response.equals("2")) {
				dealAdditionalCard(2);
				if (player1.getValueOfHand() > 21) {
					printAllForBoth();
					bust = true;
					break;
				}
			} else if (response.equals("1")) {
				printPlayerCards();
				System.out.println("\n********** It is now the dealers turn **********");
				break;
			}
		}
	}

	// This method will cause the dealer receive a card if their hand value is under
	// 17
	// If the dealer hand is 17 or greater, no further cards will be dealt and the
	// verifyWinner() method is called.
	public void houseTurn() {
		while (true) {
			if (house.getValueOfHand() < 17) {
				dealAdditionalCard(1);
				if (house.getValueOfHand() > 21) {
					bust = true;
					printAllForBoth();
					break;
				}
			} else if (house.getValueOfHand() >= 17) {
				verifyWinner();
				break;
			}
		}
	}

	// Deal out cards for the player (only 2)
	// In addition, set the player name and wallet initially
	private void dealCardsForPlayer() {
		player1.setHand(new Hand());
		for (int i = 0; i < 2; i++) {
			if (deck.getSize() == 0) {
				newDeckAndShuffle();
			}
			player1.getHand().add(deck.dealCard());
		}
	}

	// Deal the cards for the house
	private void dealCardsForHouse() {
		house.setHand(new Hand());
		for (int i = 0; i < 2; i++) {
			if (deck.getSize() == 0) {
				newDeckAndShuffle();
			}
			house.getHand().add(deck.dealCard());
		}
	}

	// When called, this method will deal a new card
	// Before a new card is dealt, the size of the deck will be verified
	// If the deck size is 0, the newDeckAndShuffle() method is called
	// After a new card is dealt, the cards are shown
	// The new hand will be verified if the value is over 21
	private void dealAdditionalCard(int val) {
		if (val == 1) {
			if (deck.getSize() == 0) {
				newDeckAndShuffle();
			}
			house.getHand().add(deck.dealCard());
			printPlayerCards();
			verifyBustOrNot();
		} else if (val == 2) {
			if (deck.getSize() == 0) {
				newDeckAndShuffle();
			}
			player1.getHand().add(deck.dealCard());
			printPlayerCards();
			verifyBustOrNot();
		}
	}

	// This method verifies if the player or house hand is a bust
	private void verifyBustOrNot() {
		if (player1.getValueOfHand() > 21) {
			bust = true;
			printAllForBoth();
		} else if (house.getValueOfHand() > 21) {
			bust = true;
			printAllForBoth();
		}

	}

	// This method will verify the winner of the hand
	private void verifyWinner() {
		if ((player1.getValueOfHand() > house.getValueOfHand()) && player1.getValueOfHand() < 22) {
			printAllForBoth();
			System.out.println("\n" + player1.getName() + " won the round!\n");
		} else if ((player1.getValueOfHand() < house.getValueOfHand()) && house.getValueOfHand() < 22) {
			printAllForBoth();
			System.out.println("\nThe house wins:(");
		} else if ((player1.getValueOfHand() == house.getValueOfHand())) {
			printAllForBoth();
			System.out.println("\nYou both have the same score. Draw");
		}

	}

	/*
	 * The following section contains methods for printing out menus and obtaining
	 * user information 1. The obtainNameAndWallet() method will ask the user for
	 * their name and their wallet size or money 2. The greeting() method will
	 * simply display a greeting for the game 3. The gameMenu() method will ask the
	 * player if they would like to deal cards or quite the game 4. The
	 * printPlayerCards() method will print out the hand of the player along with
	 * the total value of their cards 5. The printHouseCards() method will print out
	 * the cards for the house (only 1) and display the value of the card showing
	 */

	private void obtainNameAndWallet() {
		System.out.print("Please enter your name: ");
		player1.setName(kb.nextLine());
		System.out.print("Please enter your wallet size: ");
		player1.setWallet(kb.nextDouble());
	}

	private void greeting() {
		System.out.println("*********************************************");
		System.out.println("*********** Welcome to BlackJack! ***********");
		System.out.println("*********************************************");
	}

	private void gameMenu() {
		boolean v = true;
		while (v) {
			System.out.println("\n" + player1.getName() + ", what would you like to do?\n1. Deal (1)\n2. Quit (2)");
			response = kb.next();
			v = responseValidation();
		}
		if (Integer.parseInt(response) == 2) {
			System.out.println("Goodbye! Thanks for playing");
			System.exit(0);
		}
	}

	private void printPlayerCards() {
		System.out.println(String.format("\n%60s %2s %60s %2s", "PLAYER HAND", "|", "HOUSE HAND", "|"));
		System.out.println(String.format("%s",
				"-------------------------------------------------------------------------------------------------------------------------------"));
		System.out.println(String.format("%60s %2s %60s %2s", player1.getHand(), "|",
				house.getHand().get(1) + ", *********", "|"));
		System.out.println(String.format("%60s %2s %60s %2s", "Hand value is " + player1.getValueOfHand(), "|",
				"House is showing " + house.getHand().get(1).getValue(), "|"));
	}

	private void printAllForBoth() {
		System.out.println(String.format("\n%60s %2s %60s %2s", "PLAYER HAND", "|", "HOUSE HAND", "|"));
		System.out.println(String.format("%s",
				"-------------------------------------------------------------------------------------------------------------------------------"));
		System.out.println(String.format("%60s %2s %60s %2s", player1.getHand(), "|", house.getHand(), "|"));
		System.out.println(String.format("%60s %2s %60s %2s", "Hand value is " + player1.getValueOfHand(), "|",
				"Hand value is " + house.getValueOfHand(), "|"));
	}

	// This method provides the user with the option to hit or stay
	// The response is validated by calling the responseValidation() method
	private void optionMenu() {
		boolean v = true;
		while (v) {
			System.out.print("\nWould you like to Stay (1) or Hit (2)? ");
			response = kb.next();
			v = responseValidation();
		}
	}

	// Shuffle the current deck of cards
	private void deckShuffle() {
		deck.shuffle();
	}

	// This method will create a new deck and shuffle the deck
	private void newDeckAndShuffle() {
		deck = new Deck();
		deck.shuffle();
	}

	// This method will verify if the response to hit or stay is valid
	// This method will verify if the response to deal or quit is valid
	private boolean responseValidation() {
		boolean k = true;
		for (char c : response.toCharArray()) {
			if (!Character.isDigit(c)) {
				System.out.println("\nValue entered is not valid");
				k = true;
				break;
			} else if (Character.isDigit(c)) {
				if (response.toCharArray().length > 1) {
					k = true;
					System.out.println("\nValue entered is no valid");
					break;
				} else if (Character.getNumericValue(response.toCharArray()[0]) > 2
						|| Character.getNumericValue(response.toCharArray()[0]) < 1) {
					k = true;
					System.out.println("\nThe value entered is not valid");
				} else {
					k = false;
					break;
				}
			}
		}
		return k;
	}

}

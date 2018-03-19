package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.skilldistillery.cards.common.Card;
import com.skilldistillery.cards.common.Deck;

public class Game {

	static Scanner kb = new Scanner(System.in);

	private String response;
	private String name;
	private double wallet;

	private Deck deck = new Deck();
	private Hand myCards = new Hand();
	private Hand houseCards = new Hand();
	private Player player1 = new Player();
	private Player house = new Player();

	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

	private void run() {
		// Provide initial greeting
		greeting();

		// Obtain name and their wallet size
		obtainNameAndWallet();

		// Provide initial game menu
		// If 1 is chosen, player will be dealt hand
		// If 2 is chosen,the game will end
		gameMenu();

		// Shuffle Deck
		deckShuffle();

		// Deal the cards to the player
		// This method accepts the player name and the size of their wallet as arguments
		dealCardsForPlayer(name, wallet);
		dealCardsForHouse();

		// Print out the players cards and the house cards
		printPlayerCards();
		//printHouseCards();

		// Continuous Game Play for the player (hit or stay options)
		playerTurn();

		// Continuous Game Play for the house
		houseTurn();
	}
	
	public void printHouseFullHand() {
		System.out.println(house.getHand());
		System.out.println("The house has a total value of " + house.getValueOfHand());
	}

	public void playerTurn() {
		boolean notBust = true;
		while (notBust) {
			optionMenu();
			if (response.equals("2")) {
				dealAdditionalCard(2);
				if (player1.getValueOfHand() > 21) {
					notBust = false;
					System.out.println("The game is over.");
				}
			} else if (response.equals("1")) {
				//printBothHands();
				printPlayerCards();
				System.out.println("\n********** It is now the dealers turn **********");
				notBust = false;
			}
		}
	}
	
	public void houseTurn() {
		boolean notBust = true;
		while (notBust) {
			if (house.getValueOfHand() < 17) {
				dealAdditionalCard(1);
				if (house.getValueOfHand() > 21) {
					notBust = false;
					printAllForBoth();
					System.out.println("\nThe dealer busts, you win!!!!");
				}
			}
			else if (house.getValueOfHand() >= 17) {
				notBust = false;
				verifyWinner();
			}
		}
	}

	private void verifyWinner() {
		if ((player1.getValueOfHand() > house.getValueOfHand()) && player1.getValueOfHand() < 22) {
			System.out.println(player1.getName() + " won the round!\n");
			printAllForBoth();
			
		}
		else if ((player1.getValueOfHand() < house.getValueOfHand()) && house.getValueOfHand() < 22) {
			printAllForBoth();
			System.out.println("\nThe house wins:(");
		}
		else {
			System.out.println("You both have the same score. Draw");
		}
		
	}

	/*
	 * The following section contains methods for printing out menus and obtaining
	 * user information 
	 * 1. The obtainNameAndWallet() method will ask the user for
	 * their name and their wallet size or money 
	 * 2. The greeting() method will
	 * simply display a greeting for the game 
	 * 3. The gameMenu() method will ask the
	 * player if they would like to deal cards or quite the game 
	 * 4. The printPlayerCards() method will print out the hand of the player along with
	 * the total value of their cards 
	 * 5. The printHouseCards() method will print out
	 * the cards for the house (only 1) and display the value of the card showing
	 */
	
	private void obtainNameAndWallet() {
		System.out.print("Please enter your name: ");
		name = kb.nextLine();
		System.out.print("Please enter your wallet size: ");
		wallet = kb.nextDouble();
	}

	private void greeting() {
		System.out.println("*********************************************");
		System.out.println("*********** Welcome to BlackJack! ***********");
		System.out.println("*********************************************");
	}

	private void gameMenu() {
		boolean v = true;
		while (v) {
			System.out.println("\n" + name + ", what would you like to do?\n1. Deal (1)\n2. Quit (2)");
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
		System.out.println(String.format("%s", "-------------------------------------------------------------------------------------------------------------------------------"));
		System.out.println(String.format("%60s %2s %60s %2s", player1.getHand(), "|", house.getHand().get(1) + ", *********", "|"));
		System.out.println(String.format("%60s %2s %60s %2s", "Hand value is " + player1.getValueOfHand(), "|", "House is showing " + house.getHand().get(1).getValue(), "|"));
	}
	
	private void printAllForBoth() {
		System.out.println(String.format("\n%60s %2s %60s %2s", "PLAYER HAND", "|", "HOUSE HAND", "|"));
		System.out.println(String.format("%s", "-------------------------------------------------------------------------------------------------------------------------------"));
		System.out.println(String.format("%60s %2s %60s %2s", player1.getHand(), "|", house.getHand(), "|"));
		System.out.println(String.format("%60s %2s %60s %2s", "Hand value is " + player1.getValueOfHand(), "|", "Hand value is " + house.getValueOfHand(), "|"));
	}
	
	private void optionMenu() {
		boolean v = true;
		while (v) {
			System.out.print("\nWould you like to Stay (1) or Hit (2)? ");
			response = kb.next();
			v = responseValidation();
		}
		if (Integer.parseInt(response) == 1) {
			//System.out.println("Stay");
		} else if (Integer.parseInt(response) == 2) {
			//System.out.println("Hit");
		}
	}

	// Shuffle the current deck of cards
	private void deckShuffle() {
		deck.shuffle();
	}

	// Deal out cards for the player (only 2)
	// In addition, set the player name and wallet initially
	private void dealCardsForPlayer(String name, double wallet) {
		for (int i = 0; i < 2; i++) {
			myCards.addCard(deck.dealCard());
		}
		player1.setHand(myCards);
		player1.setName(name);
		player1.setWallet(wallet);
	}

	// Deal the cards for the house
	private void dealCardsForHouse() {
		for (int i = 0; i < 2; i++) {
			houseCards.addCard(deck.dealCard());
		}
		house.setHand(houseCards);
	}

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
				} else {
					k = false;
					break;
				}
			}
		}
		return k;
	}

	private void dealAdditionalCard(int val) {
		if (val == 1) {
			houseCards.addCard(deck.dealCard());
		}
		else if (val == 2){
			player1.getHand().add(deck.dealCard());
			printPlayerCards();
			verifyBustOrNot();
		}
	}

	private void verifyBustOrNot() {
		if (player1.getValueOfHand() > 21) {
			System.out.print("\nYou bust. ");
		} else {
			System.out.print("\nYou are ok. ");
			// printTotalCardValue();
		}
	}

}

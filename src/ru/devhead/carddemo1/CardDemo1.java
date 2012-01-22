package ru.devhead.carddemo1;

import javax.swing.JFrame;
import java.util.LinkedList;

import javax.swing.*;

public class CardDemo1 extends JFrame {
	// ===================================================================
	// fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LinkedList<Card>_deck;

	// =====================================================================
	// main
	public static void main(String[] args) {
		CardDemo1 window = new CardDemo1();
		window.setTitle("Card Demo 1");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new CardTable(_deck));
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	// ==============================================================
	// constructor
	public CardDemo1() {

		_deck = new LinkedList<Card>();
		
		int xPos = 0; // Where it should be placed initially.
		int yPos = 0;

		// ... Read in the cards using particular file name conventions.
		// Images for the backs and Jokers are ignored here.
		String suits = "shdc";
		String faces = "a23456789tjqk";
		for (int suit = 0; suit < suits.length(); suit++) {
			for (int face = 0; face < faces.length(); face++) {
				// ... Get the image from the images subdirectory.
				String imagePath = "cards/" + faces.charAt(face)
						+ suits.charAt(suit) + ".gif";
				ImageIcon img = new ImageIcon(imagePath);

				// ... Create a card and add it to the deck.
				Card card = new Card(img);
				card.moveTo(xPos, yPos);
				_deck.addFirst(card);

				// ... Update local vars for next card.
				xPos += 5;
				yPos += 4;
			}
		}
	}

}

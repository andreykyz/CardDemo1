package ru.devhead.carddemo1;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class CardTable extends JComponent implements MouseListener,
		MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ================================================================
	// constants
	private static final Color BACKGROUND_COLOR = Color.GREEN;
	private static final int TABLE_SIZE = 400; // Pixels.

	// ... Position in image of mouse press to make dragging look better.
	private int _dragFromX = 0; // Displacement inside image of mouse press.
	private int _dragFromY = 0;

	private LinkedList<Card> _deck; // Should really be in a model, but ...
	private Card _currentCard = null; // Current selected card.

	// ==============================================================
	// constructor
	public CardTable(LinkedList<Card> deck) {
		_deck = deck; // Should be passed a model.

		// ... Initialize graphics
		setPreferredSize(new Dimension(TABLE_SIZE, TABLE_SIZE));
		setBackground(Color.blue);

		// ... Add mouse listeners.
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	// ===========================================================
	// paintComponent
	@Override
	public void paintComponent(Graphics g) {
		// ... Paint background
		int width = getWidth();
		int height = getHeight();
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, width, height);

		// ... Display the cards, starting with the first array element.
		// The array order defines the z-axis depth.
		Iterator<Card> _deckIter = _deck.descendingIterator();
		while (_deckIter.hasNext()) {
			_deckIter.next().draw(g, this);
		}
	}

	// ====================================================== method
	// mousePressed
	// Check to see if press is within any card.
	// If it is,
	// * Set _currentCard so mouseDragged knows what to drag.
	// * Record where in the image (relative to the upper left coordinates)
	// the mouse was clicked, because it looks best if we drag from there.
	// TODO: Move the card to the last position so that it displays on top.
	public void mousePressed(MouseEvent e) {
		int x = e.getX(); // Save the x coord of the click
		int y = e.getY(); // Save the y coord of the click

		// ... Find card image this is in. Check from top down.
		_currentCard = null; // Assume not in any image.
		for (Card testCard: _deck) {
//			Card testCard = _deck[crd];
			if (testCard.contains(x, y)) {
				// ... Found, remember this card for dragging.
				_dragFromX = x - testCard.getX(); // how far from left
				_dragFromY = x - testCard.getY(); // how far from top
				_currentCard = testCard; // Remember what we're dragging.
				_deck.remove(testCard);
				_deck.addFirst(testCard);
				this.repaint(); 
				break; // Stop when we find the first match.
			}
		}
	}

	// =============================================================
	// mouseDragged
	// Set x,y to mouse position and repaint.
	public void mouseDragged(MouseEvent e) {
		if (_currentCard != null) { // Non-null if pressed inside card image.

			int newX = e.getX() - _dragFromX;
			int newY = e.getY() - _dragFromY;

			// --- Don't move the image off the screen sides
			newX = Math.max(newX, 0);
			newX = Math.min(newX, getWidth() - _currentCard.getWidth());

			// --- Don't move the image off top or bottom
			newY = Math.max(newY, 0);
			newY = Math.min(newY, getHeight() - _currentCard.getHeight());

			_currentCard.moveTo(newX, newY);

			this.repaint(); // Repaint because position changed.
		}
	}

	// ======================================================= method
	// mouseExited
	// Turn off dragging if mouse exits panel.
	public void mouseExited(MouseEvent e) {
		_currentCard = null;
	}

	// =============================================== Ignore other mouse
	// events.
	public void mouseMoved(MouseEvent e) {
	} // ignore these events

	public void mouseEntered(MouseEvent e) {
	} // ignore these events

	public void mouseClicked(MouseEvent e) {
	} // ignore these events

	public void mouseReleased(MouseEvent e) {
	} // ignore these events

}

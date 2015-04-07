package minesweeper.gui;

import java.util.EventListener;

public interface BoardChangedListener extends EventListener
{
	public void boardClicked(BoardChangedEvent e);
}

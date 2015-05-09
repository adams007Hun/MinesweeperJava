package minesweeper.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.Dimension;

import javax.swing.JPanel;

import minesweeper.Board;
import minesweeper.Cell;
import minesweeper.CellState;
import minesweeper.Control;

public class MinesweeperGui extends JFrame
{

	private static final long serialVersionUID = 1L;
	private Control ctrl;
	private MineBoardPanel myBoard;
	private MineBoardPanel enemyBoard;
	private JLabel myMineCounter;
	private JLabel enemyMineCounter;
	private Timer gameTimer;
	private JLabel globalTimer;

	private int gameTime; // in seconds
		
	public MinesweeperGui(Control _ctrl)
	{
		super("Minesweeper");
		
		this.ctrl = _ctrl;
		
		setSize(new Dimension(680, 420));
		setMinimumSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		JMenuItem mntmNewGame = new JMenuItem("New game...");
		mntmNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewGameDialog nd = new NewGameDialog(MinesweeperGui.this);
				nd.setVisible(true);
			}
		});
		mnGame.add(mntmNewGame);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnGame.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Minesweeper v0.5\n  ---Developers---  "
						+ "\n B�nyai Tam�s\nDobi�s Zolt�n\nVirovecz �d�m");
			}
		});
		mnHelp.add(mntmAbout);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("300px:grow"),
				ColumnSpec.decode("center:40px:grow"),
				ColumnSpec.decode("300px"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("40px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("300px"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "2, 2, 3, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("center:40px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("center:100px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("center:80px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("center:100px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("center:40px"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		myMineCounter = new JLabel("40");
		panel.add(myMineCounter, "1, 1");
		myMineCounter.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		enemyMineCounter = new JLabel("40");
		panel.add(enemyMineCounter, "9, 1");
		enemyMineCounter.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		globalTimer = new JLabel("00:00");
		panel.add(globalTimer, "5, 1");
		globalTimer.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		JLabel labelMe = new JLabel("Me");
		panel.add(labelMe, "3, 1");
		labelMe.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		JLabel labelEnemy = new JLabel("Enemy");
		panel.add(labelEnemy, "7, 1");
		labelEnemy.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		myBoard = new MineBoardPanel(ctrl, true);
		myBoard.setPreferredSize(new Dimension(495, 230));
		getContentPane().add(myBoard, "2, 4, fill, fill");
		
		enemyBoard = new MineBoardPanel(ctrl, false);
		enemyBoard.setPreferredSize(new Dimension(495, 230));
		getContentPane().add(enemyBoard, "4, 4, fill, fill");
		
		gameTimer = new Timer(1000, new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameTime++;
				
				globalTimer.setText(String.format("%02d:%02d", gameTime/60, gameTime%60));
				
			}
		});
		
		this.setVisible(true);
	}

	public void startClient(String ip)
	{
		// TODO minecount from server
		if(ctrl.startClient(ip) == 0){
			newGameBoardSetup();
			myMineCounter.setText(Integer.toString(ctrl.getMineCount()));
			enemyMineCounter.setText(Integer.toString(ctrl.getMineCount()));
		}
	}
	
	public void startServer(int mineCount)
	{
		ctrl.setMineCount(mineCount);
		ctrl.startServer();
		myMineCounter.setText(Integer.toString(ctrl.getMineCount()));
		enemyMineCounter.setText(Integer.toString(ctrl.getMineCount()));
	}
	
	public void clientConnectedToServer()
	{
		newGameBoardSetup();
	}
	
	private void newGameBoardSetup()
	{
		myBoard.resetBoard();
		enemyBoard.resetBoard();
		myBoard.playable = true;
		myBoard.setUpBoard();
		gameTime = 0;
		globalTimer.setText(String.format("%02d:%02d", gameTime/60, gameTime%60));
		gameTimer.restart();
	}
	
	public void updateRemoteBoard(Board received)
	{
		if (enemyBoard == null)
			return;
		enemyMineCounter.setText(Integer.toString(received.getNumMines()-received.getNumFlagged()));
		enemyBoard.updateBoard(received);
		if (received.getWin())
		{
			gameTimer.stop();
			myBoard.playable = false;
			JOptionPane.showMessageDialog(null, "The enemy has won the game! :(");
		}
		else if (received.getDefeat())
		{
			gameTimer.stop();
			myBoard.playable = false;
			JOptionPane.showMessageDialog(null, "The enemy has blown up a mine. You win! :)");
		}
	}
	
	private class MineBoardPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		private MineButton mineField[][];
		private Board board;
		private Control control;
		private boolean playable;
		
		private int rows;
		private int columns;
		
		public MineBoardPanel(Control _ctrl, boolean _playable)
		{
			super(new GridLayout(Control.getSize(), Control.getSize(), 0,0));
			this.control = _ctrl;
			this.playable = _playable;
			this.rows = Control.getSize();
			this.columns = Control.getSize();
			
			resetBoard();
		}
		
		public void updateBoard(Board received) {
			board = received;
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					mineField[i][j].updateButton(board.getCellAt(i,j));
				}
			}
			return;
		}
		
		private void resetBoard()
		{
			removeAll();
			revalidate();
			repaint();   
			mineField = new MineButton[rows][columns];
			this.setSize(rows*20, columns*20);
			
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					mineField[i][j] = new MineButton();
					add(mineField[i][j]);
				}
			}
		}
		
		public void setUpBoard()
		{
			this.board = new Board(15,15,control.getMineCount());
			
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					mineField[i][j].updateButton(new Cell(CellState.Hidden, 0));
					if (playable)
						mineField[i][j].addMouseListener(new MouseHandler(i,j));
				}
			}
		}

		private class MouseHandler extends MouseAdapter
		{
			private int r,c;
			public MouseHandler(int r, int c)
		    {
		        this.r = r;
		        this.c = c;
		    }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!board.getWin() && !board.getDefeat() && playable) {
					super.mouseClicked(e);
					Cell clickedCell = board.getCellAt(r,c);
					if (e.getButton() == MouseEvent.BUTTON3)
					{
						if (clickedCell.getCellState() == CellState.Hidden) {
							board.flag(r, c);
							((MineButton)e.getSource()).updateButton(clickedCell);
						}
						else if (clickedCell.getCellState() == CellState.Flagged) {
							board.unFlag(r,c);
							((MineButton)e.getSource()).updateButton(clickedCell);
						}
						myMineCounter.setText(Integer.toString(board.getNumMines()-board.getNumFlagged()));
						control.sendBoard(board); 
					}
					else
					{
						// Debug only message
						//System.out.print(r + " " + c + "\n");
						if (clickedCell.getCellState() == CellState.Hidden) {
							board.reveal(r, c);
							updateBoard(board);
							control.sendBoard(board); 
							if (board.getWin())
							{
								gameTimer.stop();
								JOptionPane.showMessageDialog(null, "You win the game! :)");
							}
							else if (board.getDefeat())
							{
								gameTimer.stop();
								JOptionPane.showMessageDialog(null, "You lost the game! :(");
							}
						}
					}
				}
			}
			
		}
	}


}

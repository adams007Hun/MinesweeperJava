package minesweeper.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.Dimension;

import javax.swing.JPanel;

import minesweeper.Control;

public class MinesweeperGui extends JFrame
{

	private static final long serialVersionUID = 1L;
	private Control ctrl;
	private int gameTime; // in seconds
	
	public MinesweeperGui(Control _ctrl)
	{
		super("Minesweeper");
		
		this.ctrl = _ctrl;
		
		setSize(new Dimension(680, 420));
		setMinimumSize(new Dimension(400, 400));
		//this.setSize(640, 480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		JMenuItem mntmNewGame = new JMenuItem("New game...");
		mntmNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewGameDialog nd = new NewGameDialog(null);
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
						+ "\n Bányai Tamás\nDobiás Zoltán\nVirovecz Ádám");
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
		
		JLabel myMineCounter = new JLabel("40");
		panel.add(myMineCounter, "1, 1");
		myMineCounter.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		JLabel enemyMineCounter = new JLabel("40");
		panel.add(enemyMineCounter, "9, 1");
		enemyMineCounter.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		JLabel globalTimer = new JLabel("00:00");
		panel.add(globalTimer, "5, 1");
		globalTimer.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		JLabel labelMe = new JLabel("Me");
		panel.add(labelMe, "3, 1");
		labelMe.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		JLabel labelEnemy = new JLabel("Enemy");
		panel.add(labelEnemy, "7, 1");
		labelEnemy.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		MineBoardPanel myBoard = new MineBoardPanel(true);
		myBoard.setPreferredSize(new Dimension(495, 230));
		getContentPane().add(myBoard, "2, 4, fill, fill");
		myBoard.setBoard(ctrl.getLocalBoard());
		
		MineBoardPanel enemyBoard = new MineBoardPanel(false);
		enemyBoard.setPreferredSize(new Dimension(495, 230));
		getContentPane().add(enemyBoard, "4, 4, fill, fill");
		enemyBoard.setBoard(ctrl.getRemoteBoard());
		
		Timer gameTimer = new Timer(1000, new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameTime++;
				
				globalTimer.setText(String.format("%02d:%02d", gameTime/60, gameTime%60));
				
			}
		});
		gameTimer.start();
		
		this.setVisible(true);
	}

}

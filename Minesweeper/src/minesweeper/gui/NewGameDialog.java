package minesweeper.gui;

import java.awt.Frame;

import javax.swing.JDialog;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewGameDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JButton btnConnectToServer;
	
	private static final String IPV4PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public NewGameDialog(Frame frame)
	{
		// Create a new dialog with the given title, and set the modality to true
		super(frame, "Start new game...", true);
		setSize(new Dimension(450, 244));
		setResizable(false);
		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("100px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("100px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("40px"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblGameSetup = new JLabel("Game setup");
		lblGameSetup.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblGameSetup, "2, 2");
		
		JLabel lblBoardSizex = new JLabel("Board size: 40x40");
		lblBoardSizex.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblBoardSizex, "2, 4");
		
		JLabel lblMineCount = new JLabel("Mine count:");
		lblMineCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblMineCount, "2, 6");
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(40, 1, 100, 1));
		getContentPane().add(spinner, "4, 6");
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "2, 8, 3, 1");
		
		JLabel lblConnectionSetup = new JLabel("Connection setup");
		lblConnectionSetup.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblConnectionSetup, "2, 10");
		
		JLabel lblIpAddress = new JLabel("IP address:");
		lblIpAddress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblIpAddress, "2, 12");
		
		textField = new JTextField();
		getContentPane().add(textField, "4, 12, fill, default");
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblNewLabel, "2, 14, 3, 1");
		
		JButton btnStartServer = new JButton("Start server...");
		getContentPane().add(btnStartServer, "2, 16");
		
		btnConnectToServer = new JButton("Connect to server");
		getContentPane().add(btnConnectToServer, "4, 16");
		
		btnConnectToServer.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Pattern pattern = Pattern.compile(IPV4PATTERN);
			    Matcher matcher = pattern.matcher(textField.getText());
			    if (matcher.matches()) // TODO Connect client
			    	dispose();
			    else
			    	JOptionPane.showMessageDialog(null, "Please enter a correct IPv4 address!");
				
			}
		});

		btnStartServer.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Start server
				dispose();
			}
		});
	}
	
}

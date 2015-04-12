package minesweeper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class Network {
	protected Control ctrl;

	Network(Control c) {
		ctrl = c;
	}

	abstract void connect(String ip);

	abstract void disconnect();

	abstract void sendBoard(Board messageBoard);
	
	private static final String IPV4PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public static boolean validate(final String ipv4String){          

	      Pattern pattern = Pattern.compile(IPV4PATTERN);
	      Matcher matcher = pattern.matcher(ipv4String);
	      return matcher.matches();
	}

}

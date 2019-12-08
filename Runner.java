import java.io.IOException;

import javax.swing.JFrame;

public class Runner extends JFrame
{
	private GUI graphics;
	
	public static void main(String[] args) throws IOException
	{
		Runner runner = new Runner();
	}
	
	public Runner() throws IOException
	{
		super("Seven Wonders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
		
		graphics = new GUI();
		
		add(graphics);
		setContentPane(graphics);
		pack();
	}
}

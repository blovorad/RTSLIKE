package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import configuration.GameConfiguration;
import engine.Camera;
import engine.FactionManager;

public class MainGui extends JFrame implements Runnable
{
	private final static long serialVersionUID = 1L;
	
	private final static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
	
	private GameDisplay dashboard;
	
	private Camera camera;
	
	private FactionManager manager;

	public MainGui()
	{
		super("Game");
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		manager = new FactionManager();
		camera = new Camera();
		dashboard = new GameDisplay(camera, manager);
		
		MouseControls mouseControls = new MouseControls();
		dashboard.addMouseListener(mouseControls);
		
		KeyControls keyboardListener = new KeyControls();
		JTextField textField = new JTextField();
		textField.addKeyListener(keyboardListener);
		this.getContentPane().add(textField);
		
		dashboard.setPreferredSize(preferredSize);
		contentPane.add(dashboard, BorderLayout.CENTER);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setPreferredSize(preferredSize);
	}

	@Override
	public void run() 
	{
		while(true)
		{
			try 
			{
				Thread.sleep(GameConfiguration.GAME_SPEED);
			}catch (InterruptedException e) 
			{
				System.out.println(e.getMessage());
			}
			manager.update();
			camera.update();
			dashboard.repaint();
		}
	}
	
	private class KeyControls implements KeyListener {

		@Override
		public void keyPressed(KeyEvent event) 
		{
			char keyChar = event.getKeyChar();
			switch (keyChar) 
			{
				case 'q':
					camera.move(-15, camera.getSpeed().getVy());
					break;
					
				case 'd':
					camera.move(15, camera.getSpeed().getVy());
					break;
					
				case 'z':
					camera.move(camera.getSpeed().getVx(), -15);
					break;
					
				case 's':
					camera.move(camera.getSpeed().getVx(), 15);
					break;
					
				default:
					break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{

		}

		@Override
		public void keyReleased(KeyEvent event) 
		{
			char keyChar = event.getKeyChar();
			switch (keyChar) 
			{
				case 'q':
					camera.move(0, camera.getSpeed().getVy());
					break;
					
				case 'd':
					camera.move(0, camera.getSpeed().getVy());
					break;
					
				case 'z':
					camera.move(camera.getSpeed().getVx(), 0);
					break;
					
				case 's':
					camera.move(camera.getSpeed().getVx(), 0);
					break;
					
				default:
					break;
			}
		}
	}
	
	private class MouseControls implements MouseListener 
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
			
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			
		}
	}
	
	public static void main(String[] args)
	{
		MainGui n = new MainGui();
		
		Thread gameThread = new Thread(n);
		gameThread.start();
	}

	public FactionManager getManager() 
	{
		return manager;
	}

	public void setManager(FactionManager manager) 
	{
		this.manager = manager;
	}
}

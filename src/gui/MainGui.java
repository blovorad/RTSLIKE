package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import configuration.GameConfiguration;
import engine.Camera;
import engine.FactionManager;
import engine.Mouse;
import engine.Position;

public class MainGui extends JFrame implements Runnable
{
	private final static long serialVersionUID = 1L;
	
	private final static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
	
	private GameDisplay dashboard;
	
	private Camera camera;
	
	private FactionManager manager;
	
	private Mouse mouse;

	public MainGui()
	{
		super("Game");
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		mouse = new Mouse();
		manager = new FactionManager();
		camera = new Camera(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
		dashboard = new GameDisplay(camera, manager, mouse);
		
		MouseControls mouseControls = new MouseControls();
		MouseMotion mouseMotion = new MouseMotion();
		dashboard.addMouseListener(mouseControls);
		dashboard.addMouseMotionListener(mouseMotion);
		
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
		System.out.println("resolution: " + GameConfiguration.WINDOW_WIDTH + "x" + GameConfiguration.WINDOW_HEIGHT);
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
			if(e.getButton() == 1)
			{
				if(mouse.getId() > -1)
				{
					//System.out.println("mouse " + mouse.getX() + "," + mouse.getY());
					manager.getFactions().get(0).createBuilding(mouse.getId(), new Position(e.getX() - camera.getX(), e.getY() - camera.getY()));
					//System.out.println("construction a " + mouse.getBuilding().getPosition().getX() + "," + mouse.getBuilding().getPosition().getY());
					mouse.setId(-1);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			if(e.getButton() == 1)
			{
				/*if(mouse.getId() > -1)
				{
					manager.getFactions().get(0).createBuilding(mouse.getBuilding());
					System.out.println("construction a " + mouse.getBuilding().getPosition().getX() + "," + mouse.getBuilding().getPosition().getY());
					mouse.setId(-1);
					mouse.setBuilding(null);
				}*/
			}
			else if(e.getButton() == 3)
			{
				System.out.println("REALISED BUTTON 2");
			}
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
	
	private class MouseMotion implements MouseMotionListener
	{
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			if(x < camera.getRectX() || x > camera.getRectX() + camera.getRectW() || y < camera.getRectY() || y > camera.getRectY() + camera.getRectH())
			{
				double angle = Math.atan2(y - GameConfiguration.WINDOW_HEIGHT / 2, x - GameConfiguration.WINDOW_WIDTH / 2);
				camera.move((int)(20 * Math.cos(angle)), (int)(20 * Math.sin(angle)));
			}
			else
			{
				camera.move(0, 0);
			}
			
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

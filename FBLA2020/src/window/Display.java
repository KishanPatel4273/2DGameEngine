package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import controller.InputHandler;
import engine.Engine;
import engine.Render;
import sound.SoundEffect;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	static final int SCALE = 100;
	public static final int WIDTH = 10 * SCALE;
	public static final int HEIGHT = 10 * SCALE;
	public static final int MAX_NUM_PIXELS = WIDTH*HEIGHT;
	public static final String TITLE = "Tester";
	public static long numberOfTicks = 0;
	
	private Thread thread;

	private BufferedImage img;
	private boolean running = false;
	private int[] pixels;
	private int fps;
	
	private Screen screen;
	private InputHandler input;
		
	public Display() {
		//sets up the size of the window
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);//creates an image using rgb int values
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();//creates a 1D array of rgb ints
		
		screen = new Screen(WIDTH, HEIGHT);
		//creates inputhandler
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	/**
	 * starts main loop to initiate game
	 */
	private void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);//creates threat to run program on
		thread.start();
	}
	
	/**
	 * stops the main loop
	 */
	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();//ends thread to close program correctly
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(0);//closes canvas
		}
	}
	
	/**
	 * main game loop
	 * gives tick() an update 20 times per second
	 * and render() an update 60 times per second
	 */
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			requestFocus();//has the window selected when program is lanched

			while (unprocessedSeconds > secondsPerTick) {
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 3 == 0) {//calls on tick 20x/second
					tick();
					numberOfTicks++;
				}
				if (tickCount % 60 == 0) {
					//System.out.println(frames + "fps");
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}
			}
			render();
			frames++;
		}
	}

	private void tick() {
		screen.update();//updates the screen at 20tick/second
		//sets all pixel values in array in engine equal to current pixel array values
		for (int i = 0; i < MAX_NUM_PIXELS; i ++) {
			pixels[i] = screen.engine.pixels[i]; 
		}
	}
	
	private void render() {
		//has 3 images ready to display
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		//Initiates Graphics class using bufferStrategy
		Graphics g = bs.getDrawGraphics();

		
		
		
		//displays img on screen
		g.drawImage(img, 0, 0, null);

		g.setColor(Color.CYAN);
		g.drawString(fps + " FPS", 10, 10);
		g.setFont(new Font("Arial", 0, 45));

		g.dispose();//clears graphics
		bs.show();//shows graphics
	}

	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		game.start();
	}
}

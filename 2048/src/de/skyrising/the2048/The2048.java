package de.skyrising.the2048;

import java.awt.Dimension;

import javax.swing.JFrame;

public class The2048 extends JFrame implements Runnable
{
    private static final long serialVersionUID = 1L;
    private Playfield playfield;

    public The2048()
    {
	this.playfield = new Playfield();
    }

    @Override
    public void run()
    {
	this.setResizable(false);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.add(playfield);
	this.pack();
	this.setLocationRelativeTo(null);
	this.addKeyListener(playfield);
	this.setVisible(true);
	double pending = 1;
	int frames = 0;
	long time = System.currentTimeMillis();
	double nsPerTick = 1000000000.0 / 60;
	long lastNanos = System.nanoTime();
	while(true)
	{
	    pending += (System.nanoTime() - lastNanos)/nsPerTick;
	    lastNanos = System.nanoTime();
	    if(pending >= 1)
	    {
		this.repaint();
		frames++;
		pending--;
	    }
	    if(System.currentTimeMillis() >= time + 1000)
	    {
		System.out.println(frames + " FPS " + pending);
		frames = 0;
		time = System.currentTimeMillis();
	    }
	    try
	    {
		Thread.sleep(1);
	    } catch(InterruptedException e)
	    {}
	}
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	new Thread(new The2048()).start();
    }

}

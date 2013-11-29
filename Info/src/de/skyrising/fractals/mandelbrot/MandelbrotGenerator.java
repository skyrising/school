package de.skyrising.fractals.mandelbrot;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MandelbrotGenerator implements Runnable // extends Applet
{
    double xOffset, yOffset, zoom;
    int width, height, finished;
    boolean inverted = false, axis = false, show = false;
    public static final String help = "MandelbrotSetGenerator 1.0:\n"
	    + "Usage: program-name <options>\n"
	    + "Options:\n\t"
	    + "w=<int>, width=<int>\t\t\tSet width of image to <int> (default: 1920)\n\t"
	    + "h=<int>, height=<int>\t\t\tSet height of image to <int> (default: 1080)\n\t"
	    + "x=<int>, xOffset=<double>\t\tSet x-offset to <double> (default: -0.5)\n\t"
	    + "y=<int>, yOffset=<double>\t\tSet y-offset to <double> (default: 0)\n\t"
	    + "z=<int>, zoom=<double>\t\t\tSet zoom to <double> (default: e (=2.718281828459045))\n\t"
	    + "inverted=<boolean>, inverted\t\tInvert colors = <boolean> (default: false)\n\t"
	    + "axis=<boolean>, axis\t\t\tDraw axis = <boolean> (default: false)\n\t"
	    + "--help, -help, /?, -h\t\t\tDisplay this help message";
    public BufferedImage bi;
    public int Fwidth, Fheight;
    public Canvas c;
    String[] args;
    boolean shouldNotify = true;
    boolean drawOnBi = true;

    public static void main(String[] args)
    {
	System.out.print("Args:");
	if (args.length == 0)
	    System.out.print(" none");
	for (String s : args)
	{
	    System.out.print(" " + s);
	}
	System.out.println();
	MandelbrotGenerator a = new MandelbrotGenerator();
	a.finished = 0;
	a.inverted = false;// false;
	a.axis = true;// false;
	a.xOffset = -0.5;
	a.yOffset = 0;
	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
		.getDefaultScreenDevice();
	int width = gd.getDisplayMode().getWidth();
	int height = gd.getDisplayMode().getHeight();
	a.width = width;// 16000;
	a.height = height;// 9000;
	a.zoom = Math.E;// 23;
	if (args.length > 0)
	{
	    for (String s : args)
	    {
		if (s.startsWith("w=") || s.startsWith("width="))
		    try
		    {
			a.width = Integer
				.decode(s.substring(s.indexOf("=") + 1));
		    } catch (Exception e)
		    {}
		if (s.startsWith("h=") || s.startsWith("height="))
		    try
		    {
			a.height = Integer
				.decode(s.substring(s.indexOf("=") + 1));
		    } catch (Exception e)
		    {}
		if (s.startsWith("x=") || s.startsWith("xOffset="))
		    try
		    {
			a.xOffset = Double.parseDouble(s.substring(s
				.indexOf("=") + 1));
		    } catch (Exception e)
		    {}
		if (s.startsWith("y=") || s.startsWith("yOffset="))
		    try
		    {
			a.yOffset = Double.parseDouble(s.substring(s
				.indexOf("=") + 1));
		    } catch (Exception e)
		    {}
		if (s.startsWith("z=") || s.startsWith("zoom="))
		    try
		    {
			a.zoom = Double
				.parseDouble(s.substring(s.indexOf("=") + 1));
		    } catch (Exception e)
		    {}
		if (s.equalsIgnoreCase("inverted=true")
			|| s.equalsIgnoreCase("inverted"))
		    a.inverted = true;
		if (s.equalsIgnoreCase("show=true")
			|| s.equalsIgnoreCase("show"))
		    a.show = false;
		if (s.equalsIgnoreCase("axis=true")
			|| s.equalsIgnoreCase("axis"))
		    a.axis = true;
		if (s.equalsIgnoreCase("-help") || s.equalsIgnoreCase("--help")
			|| s.equalsIgnoreCase("/?") || s.equalsIgnoreCase("-h"))
		{
		    System.out.println(help);
		    System.exit(0);
		}
	    }
	}
	new Thread(a).start();
    }

	public void run()
	{
		System.out.println("Generating image file: " + width + "x" + height
				+ " Pixels (about" + width * height * 4 / 1024 / 1024 + "MB");
		try
		{
			bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} catch (OutOfMemoryError e1)
		{
			System.out
					.println("Out of memory: try to increase heap space with -Xmx[memory in mb]M");
			e1.printStackTrace();
			System.exit(1);
			return;
		}
		if (show)
		{
			final JFrame frame = new JFrame("Mandelbrot Set");
			GraphicsDevice gd = GraphicsEnvironment
					.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			int Dwidth = gd.getDisplayMode().getWidth();
			int Dheight = gd.getDisplayMode().getHeight();
			Fwidth = this.width;
			Fheight = this.height;
			while (Fwidth > Dwidth * 0.75 || Fheight > Dheight * 0.75)
			{
				Fwidth /= 2;
				Fheight /= 2;
			}
			frame.setSize(Fwidth, Fheight);
			c = new Canvas()
			{
				private static final long serialVersionUID = 1L;

				public void paint(Graphics g)
				{
					g.clearRect(0, 0, getWidth(), getHeight());
					render(drawOnBi ? bi.getGraphics() : g);
					if (drawOnBi)
						g.drawImage(bi, 0, 0, getWidth(), getHeight(), this);
					width = getWidth();
					height = getHeight();

				}
			};
			c.addKeyListener(new KeyListener()
			{

				@Override
				public void keyTyped(KeyEvent e)
				{
				}

				@Override
				public void keyPressed(KeyEvent e)
				{
					double pixel = 0.00625 / zoom;
					if (e.getKeyChar() == '+')
					{
						zoom *= 1.5;
						System.out.println("Increased Zoom");
					}
					if (e.getKeyChar() == '-')
					{
						zoom /= 1.5;
						System.out.println("Decreased Zoom");
					}
					if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					{
						xOffset += 50 * pixel;
						System.out.println("Moved Right");
					}
					if (e.getKeyCode() == KeyEvent.VK_LEFT)
					{
						xOffset -= 50 * pixel;
						System.out.println("Moved Left");
					}
					if (e.getKeyCode() == KeyEvent.VK_UP)
					{
						yOffset -= 50 * pixel;
						System.out.println("Moved Down");
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN)
					{
						yOffset += 50 * pixel;
						System.out.println("Moved Up");
					}
					c.setSize(frame.getSize());
					// render(bi.getGraphics());
					c.repaint();
				}

				@Override
				public void keyReleased(KeyEvent e)
				{
				}

			});
			c.addComponentListener(new ComponentListener()
			{

				@Override
				public void componentShown(ComponentEvent e)
				{
				}

				@Override
				public void componentResized(ComponentEvent e)
				{
					c.repaint();
				}

				@Override
				public void componentMoved(ComponentEvent e)
				{
				}

				@Override
				public void componentHidden(ComponentEvent e)
				{
				}
			});
			frame.add(c);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			while (true)
			{
				if (c.getGraphics() != null)
				{
					render(c.getGraphics());
					synchronized (c.getGraphics())
					{
						try
						{
							c.getGraphics().wait();
						} catch (InterruptedException e1)
						{
						}
					}
				}
			}
		}
		int passes = 1;
		System.out.println("Parameters: \n\twidth = " + width + "\n\theight = "
				+ height + "\n\txOffset = " + xOffset + "\n\tyOffset = "
				+ yOffset + "\n\tzoom = " + zoom + "\n\tinverted = " + inverted
				+ "\n\taxis = " + axis);
		System.out.println("Rendering image in " + passes + " pass(es)");
		for (int i = 0; i < passes; i++)
		{
			render(bi.getGraphics());
		}
		while (finished != passes)
		{
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
			}
		}
		if (!show)
		{
			try
			{
				String fbase = "mandelbrot-" + zoom + "-" + width + "x"
						+ height;
				if (inverted)
					fbase += " inverted";
				if (axis)
					fbase += " axis";
				fbase = fbase.replaceAll("2.718281828459045", "e");
				fbase = fbase.replaceAll("3.141592653589793", "pi");
				File f = new File(fbase + ".png");
				int i = 1;
				while (f.exists())
				{
					f = new File(fbase + " (" + i++ + ").png");
				}
				System.out.println("Writing to File: " + f.getAbsolutePath());
				ImageIO.write(bi, "png", f);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

    // C-Werte checken nach Zn+1 = Zn^2 + C, Zo = 0. 30 Iterationen.
	public double checkCdouble(double x0, double y0, int iter)
	{
		// cardiod test
		double q = (x0 - 0.25) * (x0 - 0.25) + y0 * y0;
		if ((q * (q + (x0 - 0.25)) < 0.25 * y0 * y0))
		{
			return iter;
		}
		double x = 0, y = 0;
		int i = 0;
		for (; i < iter; i++)
		{
			if (x * x + y * y >= 4)
				break;
			double xtemp = x * x - y * y + x0;
			y = 2 * x * y + y0;
			x = xtemp;
		}
		return i + 1 - Math.log(Math.log(Math.abs(iter))) / Math.log(2);
	}

    public int checkCint(double x0, double y0, int iter)
    {
	// cardiod test
	double q = (x0 - 0.25) * (x0 - 0.25) + y0 * y0;
	if ((q * (q + (x0 - 0.25)) < 0.25 * y0 * y0))
	{
	    return iter;
	}
	double x = 0, y = 0;
	for (int i = 0; i < iter; i++)
	{
	    if (x * x + y * y >= 4)
		return i;
	    double xtemp = x * x - y * y + x0;
	    y = 2 * x * y + y0;
	    x = xtemp;
	}
	return iter;
    }

    // Punkte berechnen und setzen.

    public PaintThread paintThread;

    public void render(Graphics g)
    {
	/*
	 * try { if (paintThread != null) paintThread.join(); } catch
	 * (InterruptedException e) { e.printStackTrace(); }
	 */
	/*
	 * paintThread = new PaintThread(); paintThread.start(g, show);
	 */
	if(paintThread != null)
	    try
	    {
		paintThread.join(100);
	    } catch (InterruptedException e)
	    {
		e.printStackTrace();
	    }
	paintThread = new PaintThread();
	paintThread.start(g, drawOnBi ? bi : c.getGraphics());
	int i = 0;
	while (shouldNotify)
	{
	    synchronized (drawOnBi ? bi : c.getGraphics())
	    {
		(drawOnBi ? bi : c.getGraphics()).notify();
		i++;
	    }
	}
	System.out.println("Notified " + i + " times");
	shouldNotify = true;
    }

    public class PaintThread extends Thread
    {
	private Graphics g;
	private Object drawingOn;

	public void start(Graphics g, Object drawingOn)
	{
	    this.g = g;
	    this.drawingOn = drawingOn;
	    start();
	}

	public void run()
	{
	    Random rand = new Random();
	    synchronized (drawingOn)
	    {
		System.out.println(this.toString() + " is waiting");
		try
		{
		    drawingOn.wait();
		} catch (InterruptedException e)
		{}
	    }
	    shouldNotify = false;
	    long startTime = System.currentTimeMillis();
	    long lastTime = startTime;
	    int perc = 0;
	    System.out.println("Started: " + this.toString());
	    int iter = 0x64;
	    double reC, imC, zelle = 0.00625 / zoom; // Ein Pixel = 0.00625
	    int x, y;

	    reC = xOffset - (width / 2 * zelle); // oberer Rand
	    for (x = 0; x < width; x++)
	    {
		imC = yOffset - (height / 2 * zelle); // linker Rand
		for (y = 0; y < height; y++)
		{
		    Color col = getColor(checkCdouble(reC, imC, iter), iter);
		    g.setColor(col);
		    if (axis
			    && (reC + zelle > 0 && reC < 0 || (imC + zelle > 0 && imC < 0))) 
			g.setColor(new Color(255 - col.getRed(), 255 - col
				.getGreen(), 255 - col.getBlue()));
		    g.drawLine(x, y, x, y);
		    imC += zelle; // n�chste Spalte
		    double perc2 = ((((double) x * height + (double) y) / ((double) width * (double) height)) * 100);
		    if ((int) perc2 > perc
			    && (System.currentTimeMillis() - lastTime) > 10000)
		    {
			lastTime = System.currentTimeMillis();

			boolean flag = true;
			if (flag)
			{
			    System.out.println(perc2 + "%: Thread Nr. "
				    + this.getId() + " "
				    + ((lastTime - startTime) / 1000) + "s");
			}

			perc = (int) perc2;
		    }

		}
		reC += zelle; // n�chste Zeile
	    }
	    synchronized (drawingOn)
	    {
		drawingOn.notify();
	    }
	    System.out.println("Finished in "
		    + ((System.currentTimeMillis() - startTime) / 1000) + "s: "
		    + this.toString() + " (" + Thread.activeCount()
		    + " active Threads)");
	    finished++;
	    if (c != null && c.isShowing() && !drawingOn.equals(c))
	    {
		//c.repaint();
	    }
	}

	public Color getColor(double iter, int maxIter)
	{
		if(true)
	    return new Color(Color.HSBtoRGB((float) (0.95f + 10 * (iter/maxIter)), 0.6F, 1F));
	    
	    int c = (int) ((maxIter - iter) / maxIter * 255);
	    if (c > 255)
		c = 255;
	    int r = c, g = c / 5, b = c;
	    // c = (c + 255) / 2;
	    try
	    {
		if (inverted)
		{
		    if (iter == maxIter)
			return new Color((0xFF - r) % 0xFF, (0xFF - g) % 0xFF,
				(0xFF - b) % 0xFF);
		    else
			return new Color((0xFF - c) % 0xFF, (0xFF - c) % 0xFF,
				(0xFF - c) % 0xFF);
		} else
		{
		    if (iter == maxIter)
			return new Color(r % 0xFF, g % 0xFF, b % 0xFF);
		    else
			return new Color(c % 0xFF, c % 0xFF, c % 0xFF);
		}
	    } catch (Exception e)
	    {
		System.out.println("c: " + c + "rgb: (" + r + ", " + g + ", "
			+ b + ")");
		return Color.WHITE;
	    }
	}

	@Override
	public String toString()
	{
	    return "PaintThread " + this.getId();
	}
    }
}

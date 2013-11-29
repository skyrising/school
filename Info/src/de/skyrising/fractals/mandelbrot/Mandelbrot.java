package de.skyrising.fractals.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class Mandelbrot extends JPanel
{
    private int maxIter = 1000;
    private double abstand = 4;
    /** Appease the serialization gods*/
    private static final long serialVersionUID = 1L;
    
    public int istMandelbrot(double x0, double y0)
    {
	double q = (x0 - 0.25) * (x0 - 0.25) + y0 * y0;
	if ((q * (q + (x0 - 0.25)) < 0.25 * y0 * y0))
	{
	    return maxIter;
	}
	double x = 0, y = 0;
	for (int i = 0; i < maxIter; i++)
	{
	    if (x * x + y * y >= 4)
		return i;
	    double xtemp = x * x - y * y + x0;
	    y = 2 * x * y + y0;
	    x = xtemp;
	}
	return maxIter;
    }
    
    @Override
    public void paint(Graphics g)
    {
	int width = Math.min(getWidth(), getHeight());
	int height = width;
	double fw = 2D/width;
	double fh = 2D/height;
	int xoff = (getWidth() - width)/2;
	int yoff = (getHeight() - height)/2;
	g.clearRect(0, 0, getWidth(), getHeight());
	for(int x = 0; x < width; x++)
	{
	    double c_x = x*fw - 1.4;
	    for(int y = 0; y < height; y++)
	    {
		double c_y = y*fh - 1;
		int i = istMandelbrot(c_x, c_y);
		int c = (int)(256 - (i/(double)maxIter)*256);
		g.setColor(new Color(c << 16 | c << 8 | c));
		g.fillRect(x + xoff, y + yoff, 1, 1);
	    }
	}
    }

    public static void main(String[] args)
    {
	JFrame frame = new JFrame("Mandelbrot");
	Mandelbrot mb = new Mandelbrot();
	frame.add(mb);
	frame.setSize(1024, 768);
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
    }

}

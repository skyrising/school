package de.skyrising.the2048;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Playfield extends JPanel implements KeyListener
{
    public static final int MOVE_NONE = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_UP = 2;
    public static final int MOVE_RIGHT = 3;
    public static final int MOVE_DOWN = 4;
    public static final double FOURS = 0.2;

    private static final long serialVersionUID = 1L;
    private int[][] values = new int[4][4];
    private int[][] moves = new int[4][4];
    private int move = MOVE_NONE;
    private int lastMove = MOVE_NONE;
    private int moveProgress = 0;
    private Random random = new Random();
    private long lastTime = 0;
    private Map<Integer, BufferedImage> renderings = new HashMap<Integer, BufferedImage>();
    private boolean screenshot = false;

    public Playfield()
    {
	Dimension d = new Dimension(4 * 107 + 5 * 15, 4 * 107 + 5 * 15);
	this.setSize(d);
	this.setMinimumSize(d);
	this.setMaximumSize(d);
	this.setPreferredSize(d);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
	long timeDiff = System.currentTimeMillis() - lastTime;
	lastTime += timeDiff;
	BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
	Graphics2D g2d = (Graphics2D) image.getGraphics();
	g2d.setFont(new Font("Helvetica Neue", Font.BOLD, 55));
	RenderingHints hints = g2d.getRenderingHints();
	hints.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
	hints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
	int width = this.getWidth();
	int height = this.getHeight();
	moveProgress = (int) Math.max(0, moveProgress - timeDiff);
	if(moveProgress == 0)
	    moves = new int[4][4];
	g2d.setColor(new Color(0xBBADA0));
	g2d.fillRect(0, 0, width, height);
	int fwidth = 107;
	int fheight = 107;
	int space = 15;
	g2d.setColor(new Color(238, 228, 218, (int) (255 * 0.35)));
	for(int y = 0; y < 4; y++)
	{
	    for(int x = 0; x < 4; x++)
	    {
		int x0 = x * fwidth + x * space + space;
		int y0 = y * fheight + y * space + space;
		g2d.fillRoundRect(x0, y0, fwidth, fheight, 3, 3);
	    }
	}
	int sum = 0;
	for(int y = 0; y < 4; y++)
	    for(int x = 0; x < 4; x++)
		sum += values[x][y];
	if(sum == 0)
	{
	    values = new int[4][4];
	    int i = 0;
	    while(i < 2)
	    {
		int x = random.nextInt(4);
		int y = random.nextInt(4);
		if(values[x][y] == 0)
		{
		    values[x][y] = random.nextDouble() <= FOURS ? 4 : 2;
		    i++;
		}
	    }
	}
	if(move != MOVE_NONE)
	{
	    int[][] newvalues = new int[4][4];
	    if(move == MOVE_LEFT)
	    {
		for(int y = 0; y < 4; y++)
		    for(int x = 0; x < 4; x++)
		    {
			int value = values[x][y];
			if(value == 0)
			    continue;
			int xn = 0;
			for(; xn <= x; xn++)
			    if(newvalues[xn][y] == 0 || (newvalues[xn][y] == value && (xn == 3 || newvalues[xn+1][y] == 0)))
				break;
			moves[x][y] = x - xn;
			newvalues[xn][y] += value;
		    }
	    }
	    else if(move == MOVE_UP)
	    {
		for(int x = 0; x < 4; x++)
		    for(int y = 0; y < 4; y++)
		    {
			int value = values[x][y];
			if(value == 0)
			    continue;
			int yn = 0;
			for(; yn <= y; yn++)
			    if(newvalues[x][yn] == 0 || (newvalues[x][yn] == value && (yn == 3 || newvalues[x][yn+1] == 0)))
				break;
			moves[x][y] = y - yn;
			newvalues[x][yn] += value;
		    }
	    }
	    else if(move == MOVE_RIGHT)
	    {
		for(int y = 0; y < 4; y++)
		    for(int x = 3; x >= 0; x--)
		    {
			int value = values[x][y];
			if(value == 0)
			    continue;
			int xn = 3;
			for(; xn >= x; xn--)
			    if(newvalues[xn][y] == 0 || (newvalues[xn][y] == value && (xn == 3 || newvalues[xn-1][y] == 0)))
				break;
			moves[x][y] = x - xn;
			newvalues[xn][y] += value;
		    }
	    }
	    else if(move == MOVE_DOWN)
	    {
		for(int x = 0; x < 4; x++)
		    for(int y = 3; y >= 0; y--)
		    {
			int value = values[x][y];
			if(value == 0)
			    continue;
			int yn = 3;
			for(; yn >= y; yn--)
			    if(newvalues[x][yn] == 0 || (newvalues[x][yn] == value && (yn == 3 || newvalues[x][yn-1] == 0)))
				break;
			moves[x][y] = y - yn;
			newvalues[x][yn] += value;
		    }
	    }
	    lastMove = move;
	    move = MOVE_NONE;
	    if(!Arrays.deepEquals(values, newvalues))
	    {
		moveProgress = 100;
		while(true)
		{
		    int x = random.nextInt(4);
		    int y = random.nextInt(4);
		    if(newvalues[x][y] == 0)
		    {
			newvalues[x][y] = random.nextDouble() <= FOURS ? 4 : 2;
			break;
		    }
		}
	    }
	    values = newvalues;
	}
	for(int y = 0; y < 4; y++)
	{
	    for(int x = 0; x < 4; x++)
	    {
		int x0 = x * fwidth + x * space + space;
		int y0 = y * fheight + y * space + space;
		int value = values[x][y];
		if(value == 0)
		    continue;
		if(!renderings.containsKey(value))
		{
		    BufferedImage img = new BufferedImage(fwidth, fheight, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2di = (Graphics2D) img.getGraphics();
		    g2di.setFont(g2d.getFont().deriveFont((float)getFontSize(value)));
		    g2di.setColor(getBackgroundColor(value));
		    g2di.fillRoundRect(0, 0, fwidth, fheight, 3, 3);
		    g2di.setColor(getColor(value));
		    Rectangle2D bounds = g2di.getFontMetrics().getStringBounds("" + value, g2di);
		    g2di.drawString("" + value, fwidth / 2 - (int) bounds.getCenterX(), fheight / 2 - (int) bounds.getCenterY());
		    g2di.dispose();
		    renderings.put(value, img);
		}
		g2d.drawImage(renderings.get(value), x0, y0, this);
	    }
	}
	if(screenshot) {
	    File f = new File("screenshots/Screenshot-" + new SimpleDateFormat("dd-MM-yy_HH-mm-ss").format(new Date()) + ".png");
	    try
	    {
		ImageIO.write(image, "PNG", f);
		System.out.println("Screenshot saved to " + f.getAbsolutePath());
	    } catch(IOException e)
	    {
		e.printStackTrace();
	    }
	    screenshot = false;
	}
	g.drawImage(image, 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
	if(e.getKeyChar() == 'p')
	    screenshot = true;
	else if(e.getKeyChar() == 'm') {
	    values = new int[][]{{2,256,512,0},{4,128,1024,0},{8,64,2048,0},{16,32,4096,0}};
	    System.out.println("Magic!!!");
	}
	else if(e.getKeyChar() == 'c') {
	    renderings = new HashMap<Integer, BufferedImage>();
	    System.out.println("Cache cleared");
	}
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
	if(moveProgress > 0)
	    return;
	int key = e.getKeyCode();
	if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
	{
	    move = MOVE_LEFT;
	    System.out.println("left");
	}
	else if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
	{
	    move = MOVE_UP;
	    System.out.println("up");
	}
	else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
	{
	    move = MOVE_RIGHT;
	    System.out.println("right");
	}
	else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
	{
	    move = MOVE_DOWN;
	    System.out.println("down");
	}
	else if(key == KeyEvent.VK_N)
	{
	    values = new int[4][4];
	    moveProgress = 100;
	    System.out.println("new");
	}
    }

    @Override
    public void keyReleased(KeyEvent e)
    {}

    public static Color getColor(int value)
    {
	if(value >= 8)
	    return new Color(0xF9F6F2);
	return new Color(0x776E65);
    }

    public static Color getBackgroundColor(int value)
    {
	if(value == 4)
	    return new Color(0xEDE0C8);
	else if(value == 8)
	    return new Color(0xF2B179);
	else if(value == 16)
	    return new Color(0xF59563);
	else if(value == 32)
	    return new Color(0xF67C5F);
	else if(value == 64)
	    return new Color(0xF65E3B);
	else if(value == 128)
	    return new Color(0xEDCF72);
	else if(value == 256)
	    return new Color(0xEDCC61);
	else if(value == 512)
	    return new Color(0xEDC850);
	else if(value == 1024)
	    return new Color(0xEDC53F);
	else if(value == 2048)
	    return new Color(0xEDC22E);
	else if(value > 2048)
	    return new Color(0x3C3A32);
	return new Color(0xEEE4DA);
    }
    
    public static int getFontSize(int value) {
	if(value >= 100 && value <= 1000)
	    return 45;
	else if(value >= 1000 && value < 4096)
	    return 35;
	else if(value >= 4096)
	    return 30;
	return 55;
    }
}

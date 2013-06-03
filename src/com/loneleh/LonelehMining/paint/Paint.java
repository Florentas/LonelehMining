package com.loneleh.LonelehMining.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;
import com.loneleh.LonelehMining.gui.GUI;
import com.loneleh.LonelehMining.misc.Functions;
import com.loneleh.LonelehMining.misc.Variables;
import com.loneleh.LonelehMining.script.LonelehMining;
import com.loneleh.LonelehMining.script.mining.MiningVars;
import com.loneleh.LonelehMining.script.mining.Ore;

public class Paint
{
	public static boolean showPaint = true;
	public static boolean showDetails = false;
	public static int selectorIndex = 0;
	
	private final Color color1 = new Color(0, 0, 0);
	private final Color bgColor = new Color(102, 102, 102, 220);
	private final Color titleColor = new Color(20, 20, 20);
	
	private final Font font1 = new Font("Arial", 0, 9);
	private final Font font2 = new Font("Berlin Sans FB", 0, 13);
	private final Font font3 = new Font("Calibri", 1, 17);
	private final Font font4 = new Font("Bitstream Vera Sans Mono", 0, 15);
	private final Font titleFont = new Font("Elementary Gothic Bookhand", 0, 28);
	
	private final Color hoverTileColor = new Color(0xFF, 0xE8, 0x73, 150);
	private final Color miningRockColor = new Color(0xFF, 0x07, 0x00, 35);
	
	//private final static Image logo = getImage("http://i818.photobucket.com/albums/zz107/mrjohnson90/smartmetallogo_zps0c770f8d.png");
	//private final static Image logo = getImage("http://i818.photobucket.com/albums/zz107/mrjohnson90/good_metal_zps0a333127.png");
	//private final static Image paintBg = getImage("http://i818.photobucket.com/albums/zz107/mrjohnson90/paintbg_zpsdec1192d.png");
	//private final static Image selectorImg = getImage("http://i818.photobucket.com/albums/zz107/mrjohnson90/selector_zps315c1a63.png");
	private final Image miningImg = getImage("http://i.imgur.com/JPeAkIQ.png");
	
	
	
	private final static Image hideButtonImg = getImage("http://i818.photobucket.com/albums/zz107/mrjohnson90/hidebutton_zpsb3bbc274.png");
	private final static Image showButtonImg = getImage("http://i818.photobucket.com/albums/zz107/mrjohnson90/showbutton_zpsf2f05b52.png");
	private final static Image showDetailButtonImg = getImage("http://i818.photobucket.com/albums/zz107/mrjohnson90/showdetailbutton_zpse2affc88.png");
	private final static Image lessDetailsButtonImg = getImage("http://i818.photobucket.com/albums/zz107/mrjohnson90/showlessdetailbutton_zpsa95e932f.png");
	
	public static Rectangle hideButtonRect = new Rectangle(new Point(465, 396), new Dimension(hideButtonImg.getWidth(null), hideButtonImg.getHeight(null)));
	public static Rectangle showButtonRect = new Rectangle(new Point(465, 396), new Dimension(showButtonImg.getWidth(null), showButtonImg.getHeight(null)));
	public static Rectangle showDetailButtonRect = new Rectangle(new Point(446, 396), new Dimension(showDetailButtonImg.getWidth(null), showDetailButtonImg.getHeight(null)));
	public static Rectangle lessDetailsButtonRect = new Rectangle(new Point(446, 396), new Dimension(lessDetailsButtonImg.getWidth(null), lessDetailsButtonImg.getHeight(null)));
	public static Rectangle menuMainRect = new Rectangle(new Point(20, 400), new Dimension(59, 23));
	public static Rectangle menuMiningRect = new Rectangle(new Point(20, 430), new Dimension(59, 23));
	public static Rectangle menuSmeltingRect = new Rectangle(new Point(20, 460), new Dimension(59, 23));
	public static Rectangle menuSmithingRect = new Rectangle(new Point(20, 490), new Dimension(59, 23));
	
	
	private String version_s = "";
	
	public Paint()
	{
		DecimalFormat df = new DecimalFormat("#.####");
		version_s = "v" + df.format(LonelehMining.getVersion());
	}
	
	public static Image getImage(String url)
	{
		try 
		{
			return ImageIO.read(new URL(url));
		}
		catch(IOException e)
		{
			LonelehMining.logger.severe("Cannot retrieve image from " + url + "!");
			return null;
		}
	}
	
	public void onRepaint(Graphics g1)
	{
		Graphics2D g = (Graphics2D)g1;
		//TODO paint da walls?? testing shit here
		/*
		SceneObject[] blockages = SceneEntities.getLoaded(new Filter<SceneObject>() {
			@Override
			public boolean accept(SceneObject so)
			{
				return so != null && so.validate();
			}
		});
		
		
		
		if (MiningVars.rockMining != null && MiningVars.rockMining.getBounds() != null)
		{
			for (SceneObject b : blockages)
			{
				Polygon[] rmBounds = MiningVars.rockMining.getBounds();
				long overlap = 0;
				
				for (int i = 0 ; i < b.getBounds().length ; i++)
				{
					for (int j = 0 ; j < rmBounds.length ; j++)
					{
						for (int x = 0 ; x < rmBounds[j].xpoints.length ; x++)
						{
							for (int y = 0 ; y < rmBounds[j].ypoints.length ; y++)
							{
								if (b.getBounds()[i].contains(rmBounds[j].xpoints[x], rmBounds[j].ypoints[y]))
								{
									overlap++;
								}
							}
						}
					}
				}
				
				System.out.println(overlap);
				
				
				
				
				/*
			if (s.getLocation() != null)
			{
				int orientation = Players.getLocal().getOrientation();
				
				
				
				
				switch (orientation)
				{
				case 0: //E
					
						break;
				case 90: //N
					
					break;
				case 180: //W
					
					break;
				case 270: //S
					
					break;
				default:
					break;
				}
				
			}
			}
			
		}
		*/
		if (GUI.isFinished)
		{
			if (showPaint)
			{
				g.setColor(bgColor);
				g.fillRoundRect(1, 388, 517, 141, 20, 20);
				g.setFont(titleFont);
				g.setColor(titleColor);
				g.drawString("Loneleh Mining", 128, 387);
				
				g.drawImage(miningImg, 43, 431, null);
				
				g.drawImage(hideButtonImg, hideButtonRect.x, hideButtonRect.y, null);
				
				g.setFont(font1);
				g.setColor(color1);
				g.drawString(version_s, 477, 67);
				
				String gold_s, goldPerHour_s, exp_s, expPerHour_s;
				long profit = 0;
				double exp = 0;
				for (Ore o : MiningVars.oresToMine)
				{
					profit += (double)o.getCount()*o.getPrice();
					exp += o.getCount()*o.getExp();
				}
				
				int x = 160;
				int y = 418;
				int lineShift = 17;
				int valueShift = 106;
				g.setFont(font3);
				
				g.drawString("Time Elapsed:", x, y);
				if (MiningVars.miningStrategy.equalsIgnoreCase("banking"))
				{
					g.drawString("Ores mined: ", x, y+lineShift);
					g.drawString("Gems mined: ", x, y+(2*lineShift));
					g.drawString("Profit: ", x, y+(3*lineShift));
				}
				g.drawString("Exp: ", x, y+(4*lineShift));
				g.setFont(font4);
				g.drawString(Variables.miningTimer.getElapsedTimeString(), x+valueShift, y);
				
				double d;
				if (MiningVars.miningStrategy.equalsIgnoreCase("banking"))
				{
					g.drawString(String.format("%,d", MiningVars.oresMined), x+valueShift, y+(lineShift));
					g.drawString(String.format("%,d", MiningVars.gemsMined), x+valueShift, y+(2*lineShift));
					if (profit >= 10000000)
					{
						g.setColor(new Color(0x00, 0x76, 0x33));
						gold_s = String.format("%,.1fM gp", (profit/1000000.0));
					}
					else if (profit >= 100000)
					{
						g.setColor(new Color(0xff, 0xff, 0xff));
						gold_s = String.format("%,.1fk gp", (profit/1000.0));
					}
					else if (profit >= 10000)
					{
						g.setColor(new Color(0xFF, 0x8B, 0x00));
						gold_s = String.format("%,.1fk gp", (profit/1000.0));
					}
					else
					{
						g.setColor(new Color(0xff, 0xff, 0x00));
						gold_s = String.format("%,d gp", profit);
					}
					g.drawString(gold_s, x+valueShift + 3, y+(3*lineShift));
					
					d = perHour(profit);
					if (d >= 10000000)
					{
						g.setColor(new Color(0x00, 0x76, 0x33));
						goldPerHour_s = String.format("(%,.1fM gp/hr", d/1000000.0);
					}
					else if (d >= 100000)
					{
						g.setColor(new Color(0xff, 0xff, 0xff));
						goldPerHour_s = String.format("(%,.1fk gp/hr)", d/1000.0);
					}
					else if (d >= 10000)
					{
						g.setColor(new Color(0xFF, 0x8B, 0x00));
						goldPerHour_s = String.format("(%,.1fk gp/hr)", d/1000.0);
					}
					else
					{
						g.setColor(new Color(0xff, 0xff, 0x00));
						goldPerHour_s = String.format("(%,.2f gp/hr)", d);
					}
					g.drawString(goldPerHour_s, x+valueShift+(g.getFontMetrics().stringWidth(gold_s) + 3), y+(3*lineShift));
				}
				
				d = exp;
				if (d >= 10000000)
				{
					g.setColor(new Color(0x00, 0x76, 0x33));
					exp_s = String.format("%,.1fM exp", d/1000000.0);
				}
				else if (d >= 100000)
				{
					g.setColor(new Color(0xff, 0xff, 0xff));
					exp_s = String.format("%,.1fk exp", d/1000.0);
				}
				else if (d >= 10000)
				{
					g.setColor(new Color(0xFF, 0x8B, 0x00));
					exp_s = String.format("%,.1fk exp", d/1000.0);
				}
				else
				{
					g.setColor(new Color(0xff, 0xff, 0x00));
					exp_s = String.format("%,.2f exp", d);
				}
				g.drawString(exp_s, x+valueShift + 3, y+(4*lineShift));
				
				d = perHour(exp);
				if (d >= 10000000)
				{
					g.setColor(new Color(0x00, 0x76, 0x33));
					expPerHour_s = String.format("(%,.1fM exp/hr", d/1000000.0);
				}
				else if (d >= 100000)
				{
					g.setColor(new Color(0xff, 0xff, 0xff));
					expPerHour_s = String.format("(%,.1fk exp/hr)", d/1000.0);
				}
				else if (d >= 10000)
				{
					g.setColor(new Color(0xFF, 0x8B, 0x00));
					expPerHour_s = String.format("(%,.1fk exp/hr)", d/1000.0);
				}
				else
				{
					g.setColor(new Color(0xff, 0xff, 0x00));
					expPerHour_s = String.format("(%,.2f exp/hr)", d);
				}
				g.drawString(expPerHour_s, x+valueShift+(g.getFontMetrics().stringWidth(exp_s) + 3), y+(4*lineShift));
				
				x = 15;
				y = 500;
				d = (
						(double)(Skills.getExperience(Skills.MINING)-Variables.xpsToLevel[Skills.getLevel(Skills.MINING)])
						/
						(double)(Variables.xpsToLevel[Skills.getLevel(Skills.MINING)+1]-Variables.xpsToLevel[Skills.getLevel(Skills.MINING)])
						)
						*100.0;
				String percent = String.format("%,.1f %%", d);
				percentBar(false, x, y, 485, 20, d, new Color(0xD3, 0xD3, 0xD3), new Color(0x70, 0x80, 0x90), new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1.0f), g);
				g.setColor(Color.BLACK);
				g.drawString(String.format("%,d", Skills.getLevel(Skills.MINING)), x+2, y+17);
				x += printCenter(g, percent, x, y+17, 485);
				printRight(g, Time.format(Functions.getTimeToNextLv(Skills.MINING, (int)perHour(exp))) + " to " + (Skills.getLevel(Skills.MINING) + 1), 500, y + 17);
				
				if (showDetails)
				{
					g.drawImage(lessDetailsButtonImg, lessDetailsButtonRect.x, lessDetailsButtonRect.y, null);
					
					int detailX = 20;
					int detailY = 55;
					
					//start at (20, 55)
					if (MiningVars.oresToMine.size() < 9)
					{
						detailY += 23;
					}
					
					for (int i = 0 ; i < MiningVars.oresToMine.size() ; i++)
					{
						Ore o = MiningVars.oresToMine.get(i);
						g.drawImage(o.getImage(), detailX, detailY + (35*i), null);
						printDetails(g, detailX + 35, detailY + 17 + (35*i), o);
					}
					
					if (Players.getLocal().getAnimation() != 16385)
					{
						if (MiningVars.rockMining != null &&
								MiningVars.rockMining.isOnScreen() &&
								Calculations.distanceTo(MiningVars.rockMining) <= 23 &&
								MiningVars.rockMining.validate())
						{
							g.setColor(miningRockColor);
							MiningVars.rockMining.draw(g);
						}
						if (MiningVars.rockHover != null &&
								MiningVars.rockHover.isOnScreen() &&
								Calculations.distanceTo(MiningVars.rockHover) <= 23 &&
								MiningVars.rockHover.validate())
						{
							if (MiningVars.rockHover.getLocation().getBounds().length > 0)
							{
								g.setColor(hoverTileColor);
								g.fillPolygon(MiningVars.rockHover.getLocation().getBounds()[0]);
							}
						}
					}
					/*
					//TODO paint da walls??
					SceneObject[] walls = SceneEntities.getLoaded(new Filter<SceneObject>() {
						@Override
						public boolean accept(SceneObject so)
						{
							return so != null && so.validate() &&
									so.getDefinition() == null;
						}
					});
					
					for (SceneObject w : walls)
					{
						w.draw(g);
					}
					 */
				}
				else
				{
					g.drawImage(showDetailButtonImg, showDetailButtonRect.x, showDetailButtonRect.y, null);
				}
			}
			else
			{
				g.drawImage(showButtonImg, showButtonRect.x, showButtonRect.y, null);
			}
		} //end if gui.isfinished
	}
	
	private void printDetails(Graphics g, int x, int y, Ore ore)
	{
		String count_s = "", gold_s = "", goldPerHour_s = "", exp_s = "", expPerHour_s = "";
		long profit = ore.getCount()*ore.getPrice();
		long count = ore.getCount();
		
		g.setFont(font2);
		
		double d;
		if (MiningVars.miningStrategy.equalsIgnoreCase("banking"))
		{
			//COUNT
			g.setColor(Color.BLACK);
			count_s = String.format(":%,d ~ ", count);
			g.drawString(count_s, x, y);
			
			//GOLD
			if (profit >= 10000000)
			{
				g.setColor(new Color(0x00, 0x76, 0x33));
				gold_s = String.format("%,.2fM gp", (profit/1000000.0));
			}
			else if (profit >= 100000)
			{
				g.setColor(new Color(0xff, 0xff, 0xff));
				gold_s = String.format("%,.1fk gp", (profit/1000.0));
			}
			else if (profit >= 10000)
			{
				g.setColor(new Color(0xFF, 0x8B, 0x00));
				gold_s = String.format("%,.1fk gp", (profit/1000.0));
			}
			else
			{
				g.setColor(new Color(0xff, 0xff, 0x00));
				gold_s = String.format("%,d gp", profit);
			}
			g.drawString(gold_s, x+=(g.getFontMetrics().stringWidth(count_s) + 3), y);
			
			d = perHour(profit);
			if (d >= 10000000)
			{
				g.setColor(new Color(0x00, 0x76, 0x33));
				goldPerHour_s = String.format("(%,.1fM gp/hr)", d/1000000.0);
			}
			else if (d >= 100000)
			{
				g.setColor(new Color(0xff, 0xff, 0xff));
				goldPerHour_s = String.format("(%,.1fk gp/hr)", d/1000.0);
			}
			else if (d >= 10000)
			{
				g.setColor(new Color(0xFF, 0x8B, 0x00));
				goldPerHour_s = String.format("(%,.1fk gp/hr)", d/1000.0);
			}
			else
			{
				g.setColor(new Color(0xff, 0xff, 0x00));
				goldPerHour_s = String.format("(%,.2f gp/hr)", d);
			}
			g.drawString(goldPerHour_s, x+=(g.getFontMetrics().stringWidth(gold_s) + 3), y);
		}
		
		//EXP
		d = (ore.getCount())*ore.getExp();
		if (d >= 10000000)
		{
			g.setColor(new Color(0x00, 0x76, 0x33));
			exp_s = String.format(" ~ %,.1fM exp", d/1000000.0);
		}
		else if (d >= 100000)
		{
			g.setColor(new Color(0xff, 0xff, 0xff));
			exp_s = String.format(" ~ %,.1fk exp", d/1000.0);
		}
		else if (d >= 10000)
		{
			g.setColor(new Color(0xFF, 0x8B, 0x00));
			exp_s = String.format(" ~ %,.1fk exp", d/1000.0);
		}
		else
		{
			g.setColor(new Color(0xff, 0xff, 0x00));
			exp_s = String.format(" ~ %,.2f exp", d);
		}
		g.drawString(exp_s, x+=(g.getFontMetrics().stringWidth(goldPerHour_s) + 3), y);
		
		d = perHour(ore.getCount()*ore.getExp());
		if (d >= 10000000)
		{
			g.setColor(new Color(0x00, 0x76, 0x33));
			expPerHour_s = String.format("(%,.1fM exp/hr)", d/1000000.0);
		}
		else if (d >= 100000)
		{
			g.setColor(new Color(0xff, 0xff, 0xff));
			expPerHour_s = String.format("(%,.1fk exp/hr)", d/1000.0);
		}
		else if (d >= 10000)
		{
			g.setColor(new Color(0xFF, 0x8B, 0x00));
			expPerHour_s = String.format("(%,.1fk exp/hr)", d/1000.0);
		}
		else
		{
			g.setColor(new Color(0xff, 0xff, 0x00));
			expPerHour_s = String.format("(%,.2f exp/hr)", d);
		}
		g.drawString(expPerHour_s, x+=(g.getFontMetrics().stringWidth(exp_s) + 3), y);
	}
	
	private static double perHour(double gained) //money or exp etc
	{
		if (Variables.miningTimer == null) return 0.0;
		return gained / (Variables.miningTimer.getElapsedTime()/1000.0/3600.0);
	}
	
	private void percentBar(boolean vertical, int x, int y, int width, int height, double percentage, Color percentColor, Color color, Stroke stroke, Graphics2D g)
	{
		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		int drawPercentage = (int)(((percentage > 100 ? 100 : percentage) / 100) * width);
		g.setColor(percentColor);
		
		if (!vertical)
			g.fillRect(x, y, (width > height ? drawPercentage : width), (height >= width ? drawPercentage : height));
		else
		{
			int x1 = (width > height ? width - drawPercentage : 0);
			int y2 = (height >= width ? height - drawPercentage : 0);
			g.fillRect(x + x1, y + y2, width - x1, height - y2);
		}
		
		g.setColor(color);
		g.setStroke(stroke);
		g.drawRect(x, y, width, height);
	}
	
	/**
	 * @param x Center location
	 */
	public int printCenter(Graphics2D g, String s, int x, int y, int width)
	{
		int len = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();  
		int start = width/2 - len/2;
		g.drawString(s, start + x, y);
		
		return (start + x);
	}
	
	public void printRight(Graphics2D g, String s, int x, int y)
	{
		g.drawString(s, x - g.getFontMetrics().stringWidth(s), y);
	}
}

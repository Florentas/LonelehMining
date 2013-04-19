package com.loneleh.LonelehMining.script;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.powerbot.core.Bot;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.util.Random;

import com.loneleh.LonelehMining.gui.GUI;
import com.loneleh.LonelehMining.misc.SubmitServer;
import com.loneleh.LonelehMining.misc.Variables;
import com.loneleh.LonelehMining.paint.Paint;
import com.loneleh.LonelehMining.script.mining.Mining;
import com.loneleh.LonelehMining.script.mining.MiningVars;
import com.loneleh.LonelehMining.script.mining.Ore;

@Manifest(
		version = 1.33,
		authors = {"xPropel"},
		description = "Makes mining ever so simple. Supports Al Kharid, Lumbridge Swamp(W), Dwarven Hidden Mine, and Mining Guild; Banking and power mining!",
		name = "Loneleh Mining",
		singleinstance = true,
		topic = 956629,
		website = "http://www.powerbot.org/community/topic/956629-loneleh-mining-always-a-step-ahead/",
		vip = false,
		hidden = false)

public class LonelehMining extends ActiveScript implements PaintListener, MouseListener, MessageListener
{
	private final static List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private static Tree jobContainer = null;
	
	public static synchronized final void provide(final Node... jobs)
	{
		for (final Node job : jobs)
		{
			if(!jobsCollection.contains(job))
			{
				jobsCollection.add(job);
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}
	public static synchronized final void revoke(final Node... jobs)
	{
		for (final Node job : jobs)
		{
			if(jobsCollection.contains(job))
			{
				jobsCollection.remove(job);
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	}
	public final void submit(final Job... jobs)
	{
		for (final Job job : jobs)
		{
			getContainer().submit(job);
		}
	}
	
	public static String getPbName()
	{
		return Bot.context().getDisplayName();
	}
	public static String getName()
	{
		return LonelehMining.class.getAnnotation(Manifest.class).name();
	}
	public static double getVersion()
	{
		return LonelehMining.class.getAnnotation(Manifest.class).version();
	}
	public static boolean getHidden()
	{
		return LonelehMining.class.getAnnotation(Manifest.class).hidden();
	}
	
	public static boolean stop = false;
	
	public static GUI mainWindow;
	public static Paint paint = new Paint();
	public static SubmitServer submitter;
	public static Logger logger = Bot.context().getScriptHandler().log;
	
	private void initialize()
	{
		logger.info(getName() + " is initializing...");
		
		try
		{
			EventQueue.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						mainWindow = new GUI();
						mainWindow.setTitle(getName() + " v" + getVersion());
						mainWindow.setLocationRelativeTo(null);
						mainWindow.setVisible(true);
						
						mainWindow.loadingPopup(getName(), getName() + " is warming up...", true);
					}
					catch (Exception e)
					{
						logger.severe("You dun goof'ed");
						e.printStackTrace();
					}
				}
			});

			provide(
					new Mining(new Node[]{new com.loneleh.LonelehMining.script.mining.nodes.Antiban(), 
							new com.loneleh.LonelehMining.script.mining.nodes.CheckRequirements(), 
							new com.loneleh.LonelehMining.script.mining.nodes.WalkToMines(),
							new com.loneleh.LonelehMining.script.mining.nodes.WalkToMines2(),
							new com.loneleh.LonelehMining.script.mining.nodes.HoverRock(), 
							new com.loneleh.LonelehMining.script.mining.nodes.MineOres(),
							new com.loneleh.LonelehMining.script.mining.nodes.WalkToBank2(),
							new com.loneleh.LonelehMining.script.mining.nodes.WalkToBank(), 
							new com.loneleh.LonelehMining.script.mining.nodes.BankOres(), 
							new com.loneleh.LonelehMining.script.mining.nodes.DropOres()})
					);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void onStart()
	{
		logger.info("Welcome to " + getName() + ", " + getPbName());
		
		Task.sleep(2000, 3000);
		
		initialize();
	}
	
	@Override
	public void onStop()
	{
		if (submitter != null)
			submitter.submit();

		logger.info("Thanks for using " + getName());
		if (Variables.miningTimer != null && Variables.miningTimer.getElapsedTime() > 0);
			logger.info("You gained " + Ore.getTotalExp() + " exp and profitted " + Ore.getTotalProfit() + "gp from mining " + MiningVars.oresMined + " ores and " + MiningVars.gemsMined + " gems in " + Variables.miningTimer.getElapsedTimeString());
	}
	@Override
	public int loop()
	{
		if (Game.isLoggedIn())
		{
			if (jobContainer != null)
			{
				final Node job = jobContainer.state();
				if (job != null)
				{
					jobContainer.set(job);
					getContainer().submit(job);
					job.join();
				}
			}
			
			if (Walking.getEnergy() > 40 && !Walking.isRunEnabled())
			{
				Walking.setRun(true);
			}
		}
		
		if (stop)
			return -1;
		
		return Random.nextInt(100, 500);
	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (Paint.hideButtonRect.contains(e.getPoint()) || Paint.showButtonRect.contains(e.getPoint()))
		{
			Paint.showPaint = !Paint.showPaint;
		}
		else if (Paint.lessDetailsButtonRect.contains(e.getPoint()) || Paint.showDetailButtonRect.contains(e.getPoint()))
		{
			Paint.showDetails = !Paint.showDetails;
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
	@Override
	public void mousePressed(MouseEvent e)
	{
		
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}
	@Override
	public void onRepaint(Graphics g)
	{
		paint.onRepaint(g);
	}
	@Override
	public void messageReceived(MessageEvent e)
	{
		int id = e.getId();
		String msg = e.getMessage().toString();
		
		if (id == 109)
		{
			if (msg.contains("You manage to mine some"))
			{
				for (Ore ore : MiningVars.oresToMine)
				{
					if (msg.contains(ore.getName().split(" ")[0].toLowerCase()))
					{
						ore.addCount();
					}
				}
			}
			else if (msg.contains("You just found a")) //including "an" [emerald]
			{
				for (String name : MiningVars.gemsList)
				{
					if (msg.contains(name.toLowerCase()))
					{
						MiningVars.gemsMined++;
					}
				}
			}
		}
	}
}

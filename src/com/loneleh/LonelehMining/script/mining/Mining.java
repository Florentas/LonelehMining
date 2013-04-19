package com.loneleh.LonelehMining.script.mining;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import com.loneleh.LonelehMining.misc.Condition;
import com.loneleh.LonelehMining.misc.Utilities;
import com.loneleh.LonelehMining.misc.Variables;
import com.loneleh.LonelehMining.script.LonelehMining;

public class Mining extends Branch
{
	
	private static int index = 0;
	//private static ArrayList<Ore> priorityOresToMine;
	
	/*
	private static Filter<SceneObject> generalRockFilter = new Filter<SceneObject>() {
		@Override
		public boolean accept(SceneObject obj)
		{
			for (int i : MiningVars.oresToMineIds)
			{
				if (obj != null && i == obj.getId() 
						&& (MiningVars.rockHover != null && !obj.getLocation().equals(MiningVars.rockHover.getLocation())))
				{
					return true;
				}
			}
			return false;
		}
	};
	 */
	
	private static Filter<SceneObject> priorityRockFilter = new Filter<SceneObject>() {
		
		@Override
		public boolean accept(SceneObject obj)
		{
			for (int i : MiningVars.oresToMine.get(index).getRockGroupIds())
			{
				if (obj != null && obj.getInstance() != null)
				{
					if (MiningVars.rockMining != null)
					{
						if (obj.getId() == i && !obj.getLocation().equals(MiningVars.rockMining.getLocation()))
						{
							return true;
						}
					}
					else
					{
						if (obj.getId() == i)
						{
							return true;
						}
					}
				}
				else
				{
					return false;
				}
			}
			return false;
		}
	};
	
	private static Condition isRockHoverOnScreen = new Condition() {
		@Override
		public boolean validate()
		{
			return Utilities.isOnScreen(MiningVars.rockHover);
		}
	};
	
	private static Condition isRockMiningOnScreen = new Condition() {
		@Override
		public boolean validate()
		{
			return Utilities.isOnScreen(MiningVars.rockMining);
		}
	};
	
	public Mining(Node[] nodes)
	{
		super(nodes);
		LonelehMining.logger.info("Accepted: Mining");
	}
	
	@Override
	public boolean branch()
	{
		return Variables.choseMining;
	}
	
	public static boolean isAtBank()
	{
		return MiningLocation.getBankArea(MiningVars.miningLocation).contains(Players.getLocal());
	}
	
	public static boolean isAtMines()
	{
		if (MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()))
		{
			Tile tile = new Tile(3022, 9741, 0);
			return tile != null && tile.canReach();
		}
		else
		{
			return MiningLocation.getMiningArea(MiningVars.miningLocation).contains(Players.getLocal());
		}
	}
	
	public static SceneObject findNewRock()
	{
		//TODO find rock with highest priority first,
		//and then find rock with least crowded people around
		
		SceneObject rock = null;
		
		for (int i = 0 ; i < MiningVars.oresToMine.size() ; i++)
		{
			index = i;
			rock = SceneEntities.getNearest(priorityRockFilter);
			if (rock != null && MiningLocation.getMiningArea(MiningVars.miningLocation).contains(rock.getLocation()))
			{
				return rock;
			}
		}
		
		return rock;
	}
	
	public static boolean waitForRockHoverOnScreen(long milli)
	{
		return Utilities.waitFor(isRockHoverOnScreen, milli);
	}
	
	public static boolean waitForRockMiningOnScreen(long milli)
	{
		return Utilities.waitFor(isRockMiningOnScreen, milli);
	}
}

/*
 * TODO: find least crowded
 * things to consider:
 * moving camera to find least crowded? or just what's on the screen
 * find a far rock that's close or a close rock with people?
 * find a rock within a set distance?
 */
/*
Tile[] around = new Tile[9];
Tile loc = obj.getLocation();
for (int x = -1 ; x < 1 ; x++)
{
	for (int y = -1 ; y < 1 ; y++)
	{
		around[x+y+2] = new Tile(loc.getX()+x, loc.getY()+y, 0);
	}
}
 */
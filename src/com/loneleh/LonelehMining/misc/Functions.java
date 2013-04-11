package com.loneleh.LonelehMining.misc;

import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.api.wrappers.node.SceneObject;

import com.loneleh.LonelehMining.script.LonelehMining;

public class Functions
{
	public static void stopScript()
	{
		LonelehMining.stop = true;
	}
	
	public static boolean closeEnough(Locatable locatable, int distance)
	{
		if (locatable == null)
		{
			return false;
		}
		
		return Calculations.distanceTo(locatable.getLocation()) <= distance;
	}
	
	public static boolean chanceOf(int percent)
	{
		return Random.nextInt(0, 99) < percent;
	}
	
	public static long getTimeToNextLv(int skill, int xpPerHour)
	{
		return xpPerHour < 1 ? 0L : (long)(Skills.getExperienceToLevel(skill, Skills.getLevel(skill) + 1) * 3600000D / xpPerHour);
	}
	
	public static Object[] reverse(Object[] array)
	{
		if (array == null)
		{
			return null;
		}
		Object[] arr = new Object[array.length];
		for (int i = 0 ; i < array.length ; i++)
			arr[array.length-1-i] = array[i];
		
		return arr;
	}
	
	/**
	 * Thanks to Roflgod's methods!
	 * @param loc
	 * @return
	 */
	public static double getRealDistance(Locatable loc)
	{
		if (loc == null || loc.getLocation() == null) return 9001;
		
		LocalPath lp = new LocalPath(loc.getLocation());
		
		if (lp.init())
		{
			Tile lT = null;
			
			double totaldist = 0;
			
			for (org.powerbot.game.api.wrappers.Tile cT : lp.getTilePath().toArray())
			{
				totaldist += lT != null ? Calculations.distance(lT, cT): 0;
				
				lT = cT;
			}
			
			return totaldist;
			
		}
		
		return 9001;
	}
	
	/**
	 * Thanks to Roflgod's methods!
	 * @param loc
	 * @return
	 */
	public static double getAbsDistanceToNearest(Locatable loc)
	{
		return Math.abs(Calculations.distanceTo(loc) - getRealDistance(loc));
	}
	
	/**
	 * Thanks to Roflgod's methods!
	 * @param loc
	 * @return
	 */
	public static boolean canReachToRealNearest(Locatable loc)
	{
		return getAbsDistanceToNearest(loc) < 5;
	}
	
	/**
	 * Thanks to Roflgod's methods!
	 * @param loc
	 * @return
	 */
	public static Filter<NPC> npcRealDistanceFilter(final String mon)
	{
		return  new Filter<NPC>()
				{
			public boolean accept(final NPC monster)
			{
				String name = monster.getName();
				
				if(name == null || !name.toLowerCase().contains(mon.toLowerCase())) return false;
				
				Tile loc = monster.getLocation();
				
				if(loc == null  || name.contains("*") || monster.isInCombat()) return false;
				
				return canReachToRealNearest(loc);
			}
				};
	}
	
	/**
	 * Thanks to Roflgod's methods! Modified for SceneEntities
	 * @param loc
	 * @return
	 */
	public static Filter<SceneObject> sceneObjectRealDistanceFilter(final String sceneEntity)
	{
		return  new Filter<SceneObject>()
				{
			public boolean accept(final SceneObject sO)
			{
				String name = sO.getDefinition().getName();
				
				if (name == null || !name.toLowerCase().contains(sceneEntity.toLowerCase())) return false;
				
				Tile loc = sO.getLocation();
				
				if (loc == null) return false;
				
				return canReachToRealNearest(loc);
			}
				};
	}
}
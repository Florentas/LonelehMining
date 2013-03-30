package com.loneleh.LonelehMining.misc;

import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Locatable;

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
}

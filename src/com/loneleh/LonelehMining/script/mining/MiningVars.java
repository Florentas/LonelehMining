package com.loneleh.LonelehMining.script.mining;

import java.util.ArrayList;

import org.powerbot.game.api.wrappers.node.SceneObject;

public class MiningVars
{
	public static long profit = 0;
	public static double exp = 0;
	public static long oresMined = 0;
	public static long gemsMined = 0;
	
	//GUI settings
	public static ArrayList<Ore> oresToMine = new ArrayList<Ore>();
	public static String[] oreTypeStrings = new String[]{"Tin ore", "Copper ore", "Iron ore", "Silver ore", "Coal", "Gold ore", "Mithril ore", "Adamantite ore", "Runite ore", "Clay"};
	public static String[] gemsList = new String[]{"Sapphire", "Emerald", "Ruby", "Diamond"};
	public static String miningLocation = "";
	public static String miningStrategy = "";
	
	public static int action = -1;
	
	public static boolean hasLodestone = true;
	
	public static String[] miningStrategies = {"Banking", "Power Mining"};
	
	
	public static boolean requirementChecked = false;
	
	
	public static ArrayList<Integer> oresToMineIds = new ArrayList<Integer>();
	public static int[] gemIds = {1623, 1621, 1619, 1617};
	public static int[] pickIds = {1265, 1267, 1269, 1271, 1273, 1275, 14099, 14107, 15259, 16297, 16299, 16301, 16303, 16305, 16307, 16309, 16311, 16313, 16315};
	
	public static SceneObject rockHover;
	public static SceneObject rockMining;
}

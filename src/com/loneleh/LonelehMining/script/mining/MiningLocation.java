package com.loneleh.LonelehMining.script.mining;

import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;

public enum MiningLocation
{
	AL_KHARID("Al Kharid"),
	LUMBRIDGE_SWAMP_W("Lumbridge Swamp (W)"),
	MINING_GUILD("Mining Guild"),
	MINING_GUILD_RD("Mining Guild (Resource Dungeon)"),
	DWARVEN_HIDDEN_MINE("Dwarven Hidden Mine"),
	CRAFTING_GUILD("Crafting Guild");
	
	private String name;
	
	MiningLocation(String n)
	{
		name = n;
	}
	
	public static String[] valuesToString()
	{
		String vals[] = new String[MiningLocation.values().length];
		for (int i = 0 ; i < MiningLocation.values().length ; i++)
		{
			vals[i] = MiningLocation.values()[i].getName();
		}
		return vals;
	}
	
	public String getName()
	{
		return name;
	}
	
	public static String[] getAvailableRocks(String loc)
	{
		if (loc.equalsIgnoreCase(MiningLocation.AL_KHARID.getName()))
		{
			return new String[]{"Tin ore", "Copper ore", "Iron ore", "Silver ore", "Coal", "Gold ore", "Mithril ore", "Adamantite ore"};
		}
		else if (loc.equalsIgnoreCase(MiningLocation.LUMBRIDGE_SWAMP_W.getName()))
		{
			return new String[]{"Coal", "Mithril ore", "Adamantite ore"};
		}
		else if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()))
		{
			return new String[]{"Coal", "Mithril ore"};
		}
		else if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD_RD.getName()))
		{
			return new String[]{};//{"Mithril ore", "Adamantite ore", "Runite ore"};
		}
		else if (loc.equalsIgnoreCase(MiningLocation.DWARVEN_HIDDEN_MINE.getName()))
		{
			return new String[]{"Silver ore", "Coal", "Mithril ore"};
		}
		else if (loc.equalsIgnoreCase(MiningLocation.CRAFTING_GUILD.getName()))
		{
			return new String[]{};//{"Gold ore"};
		}
		else
		{
			return new String[]{};
		}
	}
	
	public static Area getBankArea(String loc)
	{
		if (loc.equalsIgnoreCase(MiningLocation.AL_KHARID.getName()))
		{
			return new Area(new Tile[] { new Tile(3272, 3171, 0), new Tile(3267, 3171, 0), new Tile(3267, 3164, 0), 
					new Tile(3272, 3164, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.LUMBRIDGE_SWAMP_W.getName()))
		{
			return new Area(new Tile[] { new Tile(3089, 3246, 0), new Tile(3095, 3246, 0), new Tile(3095, 3240, 0), 
					new Tile(3089, 3240, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()))
		{
			return new Area(new Tile[] { new Tile(3008, 3358, 0), new Tile(3016, 3358, 0), new Tile(3015, 3354, 0), 
					new Tile(3008, 3354, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD_RD.getName()))
		{
			//TODO mining guild resource dungeon bank area
			return new Area();
		}
		else if (loc.equalsIgnoreCase(MiningLocation.DWARVEN_HIDDEN_MINE.getName()))
		{
			return new Area(new Tile[] { new Tile(1044, 4576, 0), new Tile(1040, 4576, 0), new Tile(1040, 4579, 0),
					new Tile(1044, 4579, 0)
			});
		}
		else if (loc.equalsIgnoreCase(MiningLocation.CRAFTING_GUILD.getName()))
		{
			//TODO crafting guild bank area
			return new Area();
		}
		
		return null;
	}
	
	public static Area getMiningArea(String loc)
	{
		if (loc.equalsIgnoreCase(MiningLocation.AL_KHARID.getName()))
		{
			return new Area(new Tile[] { new Tile(3296, 3282, 0), new Tile(3301, 3282, 0), new Tile(3303, 3285, 0), 
					new Tile(3306, 3288, 0), new Tile(3307, 3290, 0), new Tile(3305, 3293, 0), 
					new Tile(3303, 3295, 0), new Tile(3303, 3297, 0), new Tile(3302, 3298, 0), 
					new Tile(3302, 3300, 0), new Tile(3304, 3302, 0), new Tile(3305, 3303, 0), 
					new Tile(3306, 3303, 0), new Tile(3306, 3304, 0), new Tile(3306, 3307, 0), 
					new Tile(3306, 3310, 0), new Tile(3306, 3312, 0), new Tile(3306, 3314, 0), 
					new Tile(3305, 3315, 0), new Tile(3304, 3316, 0), new Tile(3303, 3317, 0), 
					new Tile(3302, 3318, 0), new Tile(3301, 3319, 0), new Tile(3300, 3318, 0), 
					new Tile(3298, 3318, 0), new Tile(3297, 3318, 0), new Tile(3296, 3318, 0), 
					new Tile(3296, 3317, 0), new Tile(3295, 3316, 0), new Tile(3295, 3315, 0), 
					new Tile(3294, 3313, 0), new Tile(3293, 3312, 0), new Tile(3294, 3311, 0), 
					new Tile(3294, 3309, 0), new Tile(3294, 3307, 0), new Tile(3294, 3306, 0), 
					new Tile(3293, 3304, 0), new Tile(3292, 3304, 0), new Tile(3293, 3303, 0), 
					new Tile(3293, 3301, 0), new Tile(3292, 3301, 0), new Tile(3291, 3300, 0), 
					new Tile(3290, 3299, 0), new Tile(3290, 3298, 0), new Tile(3289, 3297, 0), 
					new Tile(3290, 3296, 0), new Tile(3291, 3295, 0), new Tile(3292, 3294, 0), 
					new Tile(3293, 3294, 0), new Tile(3294, 3292, 0), new Tile(3294, 3291, 0), 
					new Tile(3293, 3290, 0), new Tile(3293, 3288, 0), new Tile(3293, 3287, 0), 
					new Tile(3294, 3286, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.LUMBRIDGE_SWAMP_W.getName()))
		{
			return new Area(new Tile[] { new Tile(3145, 3151, 0), new Tile(3144, 3151, 0), new Tile(3144, 3150, 0), 
					new Tile(3142, 3149, 0), new Tile(3142, 3148, 0), new Tile(3141, 3147, 0), 
					new Tile(3141, 3146, 0), new Tile(3141, 3145, 0), new Tile(3141, 3144, 0), 
					new Tile(3142, 3143, 0), new Tile(3144, 3142, 0), new Tile(3145, 3142, 0), 
					new Tile(3147, 3142, 0), new Tile(3147, 3143, 0), new Tile(3148, 3143, 0), 
					new Tile(3149, 3144, 0), new Tile(3149, 3145, 0), new Tile(3150, 3145, 0), 
					new Tile(3150, 3146, 0), new Tile(3150, 3147, 0), new Tile(3150, 3148, 0), 
					new Tile(3150, 3147, 0), new Tile(3150, 3148, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()))
		{
			return new Area(new Tile[] { new Tile(3015, 9730, 0), new Tile(3060, 9730, 0), new Tile(3060, 9756, 0), new Tile(3015, 9756, 0)});
		}
		else if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD_RD.getName()))
		{
			//TODO mining guild resource dungeon area
			return new Area();
		}
		else if (loc.equalsIgnoreCase(MiningLocation.DWARVEN_HIDDEN_MINE.getName()))
		{
			return new Area(new Tile[] { new Tile(1041, 4565, 0), new Tile(1041, 4580, 0), new Tile(1071, 4580, 0),
					new Tile(1071, 4565, 0)
			});
		}
		else if (loc.equalsIgnoreCase(MiningLocation.CRAFTING_GUILD.getName()))
		{
			//TODO crafting guild area
			return new Area();
		}
		
		return null;
	}
	
	public static TilePath getMinesToBankPath(String loc)
	{
		if (loc.equalsIgnoreCase(MiningLocation.AL_KHARID.getName()))
		{
			return Walking.newTilePath(new Tile[] { new Tile(3299, 3302, 0), new Tile(3297, 3298, 0), new Tile(3299, 3290, 0), 
					new Tile(3298, 3286, 0), new Tile(3298, 3279, 0), new Tile(3296, 3277, 0), 
					new Tile(3295, 3274, 0), new Tile(3293, 3270, 0), new Tile(3292, 3266, 0), 
					new Tile(3293, 3263, 0), new Tile(3293, 3256, 0), new Tile(3292, 3253, 0), 
					new Tile(3292, 3248, 0), new Tile(3291, 3245, 0), new Tile(3291, 3240, 0), 
					new Tile(3289, 3237, 0), new Tile(3284, 3234, 0), new Tile(3280, 3228, 0), 
					new Tile(3277, 3225, 0), new Tile(3280, 3218, 0), new Tile(3281, 3214, 0), 
					new Tile(3281, 3210, 0), new Tile(3280, 3206, 0), new Tile(3280, 3201, 0), 
					new Tile(3280, 3194, 0), new Tile(3280, 3191, 0), new Tile(3279, 3186, 0), 
					new Tile(3279, 3183, 0), new Tile(3277, 3180, 0), new Tile(3275, 3179, 0), 
					new Tile(3274, 3176, 0), new Tile(3274, 3173, 0), new Tile(3273, 3169, 0), 
					new Tile(3274, 3168, 0), new Tile(3270, 3166, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.LUMBRIDGE_SWAMP_W.getName()))
		{
			return Walking.newTilePath(new Tile[] { new Tile(3146, 3147, 0), new Tile(3147, 3150, 0), new Tile(3147, 3154, 0), 
					new Tile(3147, 3157, 0), new Tile(3146, 3162, 0), new Tile(3146, 3166, 0), 
					new Tile(3145, 3170, 0), new Tile(3145, 3173, 0), new Tile(3144, 3176, 0), 
					new Tile(3144, 3180, 0), new Tile(3143, 3184, 0), new Tile(3142, 3187, 0), 
					new Tile(3141, 3189, 0), new Tile(3139, 3193, 0), new Tile(3138, 3195, 0), 
					new Tile(3135, 3199, 0), new Tile(3132, 3201, 0), new Tile(3130, 3203, 0), 
					new Tile(3127, 3206, 0), new Tile(3124, 3209, 0), new Tile(3122, 3210, 0), 
					new Tile(3119, 3212, 0), new Tile(3115, 3215, 0), new Tile(3112, 3218, 0), 
					new Tile(3110, 3221, 0), new Tile(3107, 3224, 0), new Tile(3103, 3226, 0), 
					new Tile(3102, 3227, 0), new Tile(3100, 3228, 0), new Tile(3097, 3230, 0), 
					new Tile(3095, 3232, 0), new Tile(3093, 3234, 0), new Tile(3091, 3236, 0), 
					new Tile(3089, 3237, 0), new Tile(3086, 3238, 0), new Tile(3085, 3240, 0), 
					new Tile(3085, 3244, 0), new Tile(3085, 3246, 0), new Tile(3086, 3248, 0), 
					new Tile(3089, 3248, 0), new Tile(3092, 3247, 0), new Tile(3092, 3245, 0), 
					new Tile(3090, 3244, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()))
		{
			return Walking.newTilePath(new Tile[] { new Tile(3022, 3337, 0), new Tile(3030, 3337, 0), new Tile(3030, 3348, 0), 
					new Tile(3022, 3352, 0), new Tile(3021, 3361, 0), new Tile(3015, 3360, 0), 
					new Tile(3014, 3356, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD_RD.getName()))
		{
			//TODO mining guild resource dungeon mines to bank path
			return Walking.newTilePath(new Tile[]{});
		}
		else if (loc.equalsIgnoreCase(MiningLocation.DWARVEN_HIDDEN_MINE.getName()))
		{
			return Walking.newTilePath(new Tile[]{new Tile(1055, 4574, 0), new Tile(1043, 4577, 0)});
		}
		else if (loc.equalsIgnoreCase(MiningLocation.CRAFTING_GUILD.getName()))
		{
			//TODO crafting guild mines to bank path
			return Walking.newTilePath(new Tile[]{});
		}
		else
		{
			return Walking.newTilePath(new Tile[]{});
		}
	}
	
	public static TilePath getLodestoneToBankPath(String loc)
	{
		if (loc.equalsIgnoreCase(MiningLocation.AL_KHARID.getName()))
		{
			return Walking.newTilePath(new Tile[] { new Tile(3296, 3182, 0), new Tile(3292, 3182, 0), new Tile(3288, 3182, 0), 
					new Tile(3282, 3181, 0), new Tile(3279, 3181, 0), new Tile(3276, 3180, 0), 
					new Tile(3275, 3177, 0), new Tile(3274, 3173, 0), new Tile(3274, 3170, 0), 
					new Tile(3273, 3167, 0), new Tile(3270, 3167, 0) });
		}
		else if (loc.equalsIgnoreCase(MiningLocation.LUMBRIDGE_SWAMP_W.getName()))
		{
			return Walking.newTilePath(new Tile[] { new Tile(3104, 3297, 0), new Tile(3106, 3295, 0), new Tile(3108, 3293, 0), 
					new Tile(3108, 3291, 0), new Tile(3108, 3289, 0), new Tile(3107, 3286, 0), 
					new Tile(3105, 3282, 0), new Tile(3104, 3279, 0), new Tile(3104, 3274, 0), 
					new Tile(3103, 3271, 0), new Tile(3103, 3268, 0), new Tile(3103, 3266, 0), 
					new Tile(3103, 3262, 0), new Tile(3103, 3258, 0), new Tile(3103, 3255, 0), 
					new Tile(3103, 3251, 0), new Tile(3103, 3250, 0), new Tile(3100, 3249, 0), 
					new Tile(3097, 3249, 0), new Tile(3095, 3249, 0), new Tile(3092, 3248, 0), 
					new Tile(3091, 3246, 0), new Tile(3092, 3244, 0), new Tile(3090, 3243, 0) });
		}
		else
		{
			return Walking.newTilePath(new Tile[]{});
		}
		//TODO perhaps more lodestone needed for different mines?
	}
	
	public static SceneObject getEntranceEnter(String loc)
	{
		if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()))
		{
			return SceneEntities.getNearest(2113);
		}
		else
		{
			return null;
		}
	}
	
	public static SceneObject getEntranceExit(String loc)
	{
		if (loc.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()))
		{
			return SceneEntities.getNearest(6226);
		}
		else
		{
			return null;
		}
	}
}

package script.mining;

import java.awt.Image;


public class Ore
{
	private int id = -1;
	private String name = "";
	private OreType type;
	private long count = -1;
	private double exp = -1.0;
	//private static long goldEarned = 0;
	//private static double expEarned = 0.0;
	
	public Ore(int id)
	{
		switch (id)
		{
		case 436:
			name = "Copper ore";
			exp = 17.5;
			type = OreType.COPPER;
			break;
		case 438:
			name = "Tin ore";
			exp = 17.5;
			type = OreType.TIN;
			break;
		case 440:
			name = "Iron ore";
			exp = 35;
			type = OreType.IRON;
			break;
		case 442:
			name = "Silver ore";
			exp = 40;
			type = OreType.SILVER;
			break;
		case 444:
			name = "Gold ore";
			exp = 65;
			type = OreType.GOLD;
			break;
		case 447:
			name = "Mithril ore";
			exp = 80;
			type = OreType.MITHRIL;
			break;
		case 449:
			name = "Adamantite ore";
			exp = 95;
			type = OreType.ADAMANT;
			break;
		case 451:
			name = "Runite ore";
			exp = 125;
			type = OreType.RUNE;
			break;
		case 453:
			name = "Coal";
			exp = 50;
			type = OreType.COAL;
			break;
		case 1623:
			name = "Sapphire";
			exp = 65;
			break;
		case 1621:
			name = "Emerald";
			exp = 65;
			break;
		case 1619:
			name = "Ruby";
			exp = 65;
			break;
		case 1617:
			name = "Diamond";
			exp = 65;
			break;
		case -2:
			name = "Gem";
			exp = 65;
		default:
			return;
		}
		
		count = 0;
	}
	
	public Ore(String name)
	{
		if (name != null)
		{
			if (name.equals("Copper ore"))
			{
				id = 436;
				exp = 17.5;
				type = OreType.COPPER;
			}
			else if (name.equals("Tin ore"))
			{
				id = 438;
				exp = 17.5;
				type = OreType.TIN;
			}
			else if (name.equals("Silver ore"))
			{
				id = 442;
				exp = 40;
				type = OreType.SILVER;
			}
			else if (name.equals("Coal"))
			{
				id = 453;
				exp = 50;
				type = OreType.COAL;
			}
			else if (name.equals("Gold ore"))
			{
				id = 444;
				exp = 65;
				type = OreType.GOLD;
			}
			else if (name.equals("Mithril ore"))
			{
				id = 447;
				exp = 80;
				type = OreType.MITHRIL;
			}
			else if (name.equals("Adamantite ore"))
			{
				id = 449;
				exp = 95;
				type = OreType.ADAMANT;
			}
			else if (name.equals("Runite ore"))
			{
				id = 451;
				exp = 125;
				type = OreType.RUNE;
			}
			else if (name.equals("Iron ore"))
			{
				id = 440;
				exp = 35;
				type = OreType.IRON;
			}
			else if (name.equals("Sapphire"))
			{
				id = 1623;
				exp = 65;
			}
			else if (name.equals("Emerald"))
			{
				id = 1621;
				exp = 65;
			}
			else if (name.equals("Ruby"))
			{
				id = 1619;
				exp = 65;
			}
			else if (name.equals("Diamond"))
			{
				id = 1617;
				exp = 65;
			}
			else if (name.equalsIgnoreCase("gem"))
			{
				id = -2;
				exp = 65;
			}
			
			this.name = name;
			count = 0;
		}
		else
		{
			System.out.println("Invalid Ore name");
		}
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public long getCount()
	{
		//return (id == -2) ? MiningVars.gemsMined : count;
		return count;
	}
	
	public int getPrice()
	{
		return type.getPrice();
	}
	
	public Image getImage()
	{
		return type.getImage();
	}
	
	public double getExp()
	{
		return exp;
	}
	
	public void addCount()
	{
		/*
		if (isGem())
			MiningVars.gemsMined++;
		else
			MiningVars.oresMined++;
			*/
		MiningVars.oresMined++;
		count++;
	}
	
	public boolean isGem()
	{
		for (int i : script.mining.MiningVars.gemIds)
		{
			if (this.id == i) return true;
		}
		return false;
	}
	
	//TODO a function that returns a all the rocks of the same type in a given mining location
	//getrockgroup() - can't decide whether to have just ids, my old Rock, and/or my RockGroup
	//	problem with ids is that it'll be harder to implement the timer-auto walk thing before rock
	//respawns (still have to figure out the complete logic for that too). since it'll be int.
	//unless i create an array (at each runtime) that will be filled with timers and each timer
	//keeps track of when each rock is going to respawn (this can be applied to woodcutting later)
	//	problem with the RockGroup/Rock is that in order for RockGroup to work, you will need Rock,
	//and that is simply way too much memory, since you will only be needing 1 location at a time, 
	//making tons of rockgroups for different locations is just retarded as only 1 will be used each time
	
	public int[] getRockGroupIds()
	{
		//System.out.println("loc: " + MiningVars.miningLocation);
		if (MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.AL_KHARID.getName()))
		{
			switch (type)
			{
			case TIN:
				return new int[]{11933};
			case COPPER:
				return new int[]{11936, 11938};
			case IRON:
				return new int[]{37307, 37308, 37309};
			case SILVER:
				return new int[]{37304, 37305, 37306};
			case COAL:
				return new int[]{11930, 11932};
			case GOLD:
				return new int[]{37310, 37312};
			case MITHRIL:
				return new int[]{11942, 11944};
			case ADAMANT:
				return new int[]{11939, 11941};
			case RUNE:
				return new int[]{};
			default:
				break;
			}
		}
		else if (MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.LUMBRIDGE_SWAMP.getName()))
		{
			//TODO still haven't got values
			switch (type)
			{
			case TIN:
				return new int[]{};
			case COPPER:
				return new int[]{};
			case IRON:
				return new int[]{};
			case SILVER:
				return new int[]{};
			case COAL:
				return new int[]{3032, 3233};
			case GOLD:
				return new int[]{};
			case MITHRIL:
				return new int[]{3041, 3280};
			case ADAMANT:
				return new int[]{3273, 3040};
			case RUNE:
				return new int[]{};
			default:
				break;
			}
		}
		else if (MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()))
		{
			//TODO still haven't got values
			switch (type)
			{
			case TIN:
				return new int[]{};
			case COPPER:
				return new int[]{};
			case IRON:
				return new int[]{};
			case SILVER:
				return new int[]{};
			case COAL:
				return new int[]{11930, 11932};
			case GOLD:
				return new int[]{};
			case MITHRIL:
				return new int[]{11942, 11944};
			case ADAMANT:
				return new int[]{};
			case RUNE:
				return new int[]{};
			default:
				break;
			}
		}
		
		return new int[]{};
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		return ((Ore)obj).getName().equalsIgnoreCase(this.name) || ((Ore)obj).getId() == this.id;
	}
}

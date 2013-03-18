package script.mining;

import java.awt.Image;

import org.powerbot.game.api.util.net.GeItem;

import paint.Paint;

public enum OreType
{
	TIN("Tin ore"),
	COPPER("Copper ore"),
	IRON("Iron ore"),
	SILVER("Silver ore"),
	COAL("Coal"),
	GOLD("Gold ore"),
	MITHRIL("Mithril ore"),
	ADAMANT("Adamantite ore"),
	RUNE("Runite ore"),
	SAPPHIRE("Sapphire"),
	EMERALD("Emerald"),
	RUBY("Ruby"),
	DIAMOND("Diamond");
	
	private String name;
	private int price;
	private Image img;
	
	OreType(String n)
	{
		name = n;
		GeItem gi = GeItem.lookup(name);
		if (gi != null)
		{
			price = gi.getPrice();
			img = Paint.getImage(gi.getSpriteURL().toString());
		}
		else
		{
			System.out.println("Cannot get ore (" + name + ") price and image!");
		}
	}
	
	public static String[] valuesToString()
	{
		String vals[] = new String[OreType.values().length];
		for (int i = 0 ; i < OreType.values().length ; i++)
		{
			vals[i] = OreType.values()[i].getName();
		}
		return vals;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public Image getImage()
	{
		return img;
	}
}
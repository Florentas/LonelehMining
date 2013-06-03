package com.loneleh.LonelehMining.script.mining;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.powerbot.game.api.util.net.GeItem;

import com.loneleh.LonelehMining.paint.Paint;
import com.loneleh.LonelehMining.script.LonelehMining;

public enum OreType
{
	TIN("Tin ore", 438),
	COPPER("Copper ore",  436),
	IRON("Iron ore", 440),
	SILVER("Silver ore", 442),
	COAL("Coal", 453),
	GOLD("Gold ore", 444),
	MITHRIL("Mithril ore", 447),
	ADAMANT("Adamantite ore", 449),
	RUNE("Runite ore", 451),
	SAPPHIRE("Sapphire", 1623),
	EMERALD("Emerald", 1621),
	RUBY("Ruby", 1619),
	DIAMOND("Diamond", 1617),
	CLAY("Clay", 434);
	
	private String name;
	private int price;
	private int id;
	private Image img;
	
	OreType(String n, int i)
	{
		name = n;
		id = i;
		
		String ret = submit("http://scriptwith.us/api/?item=" + id);
		
		JSONObject jo = new JSONObject(ret.substring(1, ret.length() -1));
		
		if (jo.has("price"))
			price = jo.getInt("price");
		else
		{
			LonelehMining.logger.severe("Cannot get price of " + name);
			price = 0;
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
	
	private String submit(String endpoint)
	{
		String result = null;
		
		try
		{
			URL url = new URL(endpoint);
			URLConnection submitConn = url.openConnection();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(submitConn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			
			while ((line = rd.readLine()) != null)
				sb.append(line);
			
			result = sb.toString();
			rd.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LonelehMining.logger.severe("An error has occured while getting prices: " + e.getCause());
		}
		return result;
	}
}
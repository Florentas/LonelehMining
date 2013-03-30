package com.loneleh.LonelehMining.misc;

public enum Target
{
	PROFIT("Profit"),
	EXP("Exp"),
	HOURS("Hours"),
	NONE("None");
	
	private String name;
	
	Target(String n)
	{
		name = n;
	}
	
	public static String[] valuesToString()
	{
		String vals[] = new String[Target.values().length];
		for (int i = 0 ; i < Target.values().length ; i++)
		{
			vals[i] = Target.values()[i].getName();
		}
		return vals;
	}
	
	public String getName()
	{
		return name;
	}
}

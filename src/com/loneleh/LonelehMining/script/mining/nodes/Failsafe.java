package com.loneleh.LonelehMining.script.mining.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;

import com.loneleh.LonelehMining.misc.Condition;
import com.loneleh.LonelehMining.misc.Utilities;
import com.loneleh.LonelehMining.script.mining.MiningVars;

public class Failsafe extends Node
{
	@Override
	public boolean activate()
	{
		return MiningVars.requirementChecked;
	}
	
	@Override
	public void execute()
	{
		if (MiningVars.action == 3 || MiningVars.action == 4)
		{
			if (MiningVars.rockMining != null && MiningVars.rockMining.validate() &&
					!Utilities.waitFor(new Condition()
					{
						@Override
						public boolean validate()
						{
							return Players.getLocal().getAnimation() == 624 || Players.getLocal().getAnimation() == 625;
						}
					}, 2000))
			{
				MiningVars.rockMining.interact("Mine", MiningVars.rockMining.getDefinition().getName());
			}
		}
	}
}

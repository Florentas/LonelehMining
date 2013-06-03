package com.loneleh.LonelehMining.script.mining.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;

import com.loneleh.LonelehMining.script.LonelehMining;
import com.loneleh.LonelehMining.script.mining.Mining;
import com.loneleh.LonelehMining.script.mining.MiningLocation;
import com.loneleh.LonelehMining.script.mining.MiningVars;

public class WalkToMines3 extends Node
{
	SceneObject portal = null;
	
	@Override
	public boolean activate()
	{
		return MiningVars.requirementChecked && !Inventory.isFull() &&
				(!MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.MINING_GUILD_RD.getName()) || (MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.MINING_GUILD_RD.getName()) && Mining.isInMiningGuild())) &&
				!Mining.isAtMines();
	}

	@Override
	public void execute()
	{
		if (!MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.MINING_GUILD.getName()) &&
				!MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.MINING_GUILD_RD.getName()))
			LonelehMining.revoke(this);
		
		MiningVars.action = 6;
		portal = MiningLocation.getInnerEntranceEnter(MiningVars.miningLocation);
		if (portal != null)
		{
			portal.click(true);
			Task.sleep(1200, 1700);
		}
	}
}

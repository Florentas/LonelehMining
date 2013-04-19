package com.loneleh.LonelehMining.script.mining.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;

import com.loneleh.LonelehMining.misc.Condition;
import com.loneleh.LonelehMining.misc.Utilities;
import com.loneleh.LonelehMining.script.mining.Mining;
import com.loneleh.LonelehMining.script.mining.MiningLocation;
import com.loneleh.LonelehMining.script.mining.MiningVars;

public class WalkToBank2 extends Node
{
	SceneObject portal = null;
	
	@Override
	public boolean activate()
	{
		if (!MiningVars.miningStrategy.equalsIgnoreCase("banking")) return false;
		
		return MiningVars.requirementChecked && Inventory.isFull() && Mining.isAtMines();
	}
	
	@Override
	public void execute()
	{
		MiningVars.action = 5;
		
		portal = MiningLocation.getEntranceExit(MiningVars.miningLocation);
		if (portal != null)
		{
			if (!portal.isOnScreen())
			{
				Walking.walk(portal);
				Utilities.cameraTurnTo(portal);
			}
			if (Utilities.waitFor(new Condition() {
				@Override
				public boolean validate()
				{
					return portal.isOnScreen();
				}
				
			}, 750))
			{
				portal.click(true);
				Task.sleep(1200, 1700);
			}
		}
	}
}

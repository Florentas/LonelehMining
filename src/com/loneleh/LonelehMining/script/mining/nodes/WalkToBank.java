package com.loneleh.LonelehMining.script.mining.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

import com.loneleh.LonelehMining.misc.Functions;
import com.loneleh.LonelehMining.misc.Lodestone;
import com.loneleh.LonelehMining.script.mining.Mining;
import com.loneleh.LonelehMining.script.mining.MiningLocation;
import com.loneleh.LonelehMining.script.mining.MiningVars;

public class WalkToBank extends Node
{
	@Override
	public boolean activate()
	{
		if (!MiningVars.miningStrategy.equalsIgnoreCase("banking")) return false;
		
		return MiningVars.requirementChecked && Inventory.isFull() && !Mining.isAtBank();
	}
	
	@Override
	public void execute()
	{
		MiningVars.action = 5;
		
		if (MiningLocation.getLodestoneToBankPath(MiningVars.miningLocation).toArray().length > 0 &&
				!Functions.closeEnough(MiningLocation.getLodestoneToBankPath(MiningVars.miningLocation).getStart(), 100))
		{
			if (MiningVars.hasLodestone)
			{
				if (MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.AL_KHARID.getName()))
					Lodestone.teleport(Lodestone.AL_KHARID);
				else if (MiningVars.miningLocation.equalsIgnoreCase(MiningLocation.LUMBRIDGE_SWAMP_W.getName()))
					Lodestone.teleport(Lodestone.DRAYNOR_VILLAGE);
			}
			
			//TODO will only run once - so you can't walk quite from lodestone to bank...
			//MiningLocation.getLodestoneToBankPath(MiningVars.miningLocation).traverse();
		}
		MiningLocation.getLodestoneToBankPath(MiningVars.miningLocation).traverse();
		MiningLocation.getMinesToBankPath(MiningVars.miningLocation).traverse();
		Task.sleep(1200, 1700);
	}
}

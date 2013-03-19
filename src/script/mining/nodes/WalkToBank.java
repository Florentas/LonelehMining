package script.mining.nodes;

import misc.Functions;
import misc.Lodestone;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Tile;

import script.mining.Mining;
import script.mining.MiningLocation;
import script.mining.MiningVars;

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
		
		if (!Functions.closeEnough(MiningLocation.getLodestoneToBankPath(MiningVars.miningLocation).getStart(), 100))
		{
			if (MiningVars.hasLodestone)
			{
				if (MiningVars.miningLocation.equalsIgnoreCase("al kharid"))
					Lodestone.teleport(Lodestone.AL_KHARID);
				else if (MiningVars.miningLocation.equalsIgnoreCase("lumbridge swamp"))
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

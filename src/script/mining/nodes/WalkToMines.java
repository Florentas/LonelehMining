package script.mining.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

import script.mining.Mining;
import script.mining.MiningLocation;
import script.mining.MiningVars;

public class WalkToMines extends Node
{
	@Override
	public boolean activate()
	{
		return MiningVars.requirementChecked && !Inventory.isFull() && !Mining.isAtMines();
	}
	
	@Override
	public void execute()
	{
		MiningVars.action = 6;
		MiningLocation.getMinesToBankPath(MiningVars.miningLocation).reverse().traverse();
		Task.sleep(1200, 1700);
	}
}

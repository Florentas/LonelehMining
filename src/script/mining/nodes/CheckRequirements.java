package script.mining.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;

import script.mining.MiningVars;

/**
 * Checks requirements for mining - i.e. do you have a pickaxe?
 * 
 * @author Darren Lin
 *
 */
public class CheckRequirements extends Node
{
	@Override
	public boolean activate()
	{
		return !MiningVars.requirementChecked;
	}
	
	@Override
	public void execute()
	{
		if (Equipment.appearanceContainsOneOf(MiningVars.pickIds) || Equipment.getItem(MiningVars.pickIds) != null
				|| Inventory.contains(MiningVars.pickIds))
		{
			MiningVars.requirementChecked = true;
		}
		else
		{
			requirementNotMet();
		}
	}
	
	private void requirementNotMet()
	{
		System.out.println("You don't have proper requirements to mine.\nDouble-check the requirements again.");
	}
}

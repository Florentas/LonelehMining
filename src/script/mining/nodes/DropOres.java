package script.mining.nodes;

import misc.Actionbar;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

import script.mining.Mining;
import script.mining.MiningVars;

public class DropOres extends Node
{
	@Override
	public boolean activate()
	{
		if (!MiningVars.miningStrategy.equalsIgnoreCase("power mining")) return false;
		
		return MiningVars.requirementChecked;
	}
	
	@Override
	public void execute()
	{
		MiningVars.action = 2;
		
		if (Inventory.isFull())
		{
			for (Item i : Inventory.getItems())
			{
				dropItem(i);
				Task.sleep(100);
			}
		}
		else
		{
			if (Players.getLocal().getAnimation() != 624)
			{
				for (Item i : Inventory.getItems())
				{
					if (Mining.findNewRock() != null)
					{
						break;
					}
					dropItem(i);
				}
			}
		}
	}
	
	private void dropItem(Item i)
	{
		if (i != null)
		{
			if (Actionbar.isOpen() || Actionbar.open())
			{
				if ((i.getDefinition().getName().endsWith("ore") || i.getDefinition().getName().equalsIgnoreCase("coal")) &&
						Actionbar.getSlotWithItem(i.getId()).activate(true)) //MUST MAKE SURE YOU DON'T HAVE IT SET ON '-' OR '='
					return;
			}
			
			if (i.getDefinition().getName().endsWith("ore") || i.getDefinition().getName().equalsIgnoreCase("coal"))
			{
				i.getWidgetChild().interact("Drop", i.getDefinition().getName());
				Task.sleep(50);
			}
		}
	}
}

package script.mining.nodes;


import gui.GUI;
import misc.Utilities;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;

import script.mining.Mining;
import script.mining.MiningVars;

public class MineOres extends Node
{
	@Override
	public boolean activate()
	{
		return MiningVars.requirementChecked &&
				GUI.isFinished &&
				!Inventory.isFull() && Mining.isAtMines()
				&& !Players.getLocal().isInCombat();
	}
	
	@Override
	public void execute()
	{
		MiningVars.action = 4;
		
		if (MiningVars.rockMining != null)
		{
			if (MiningVars.rockMining.isOnScreen() && MiningVars.rockMining.getInstance() != null && MiningVars.rockMining.validate())
			{
				if (Players.getLocal().getAnimation() == 624 || Players.getLocal().isMoving())
				{
					return;
				}
				else
				{
					Task.sleep(1000);
					if (Players.getLocal().getAnimation() == 624 || Players.getLocal().isMoving())
					{
						return;
					}
				}
			}
		}
		
		if (MiningVars.rockHover != null && MiningVars.rockHover.getInstance() != null)
		{
			MiningVars.rockMining = new SceneObject(MiningVars.rockHover.getInstance(), MiningVars.rockHover.getType(), MiningVars.rockHover.getPlane());
		}
		
		if (MiningVars.rockMining != null && MiningVars.rockMining.getInstance() != null && MiningVars.rockMining.validate())
		{
			if (!Utilities.isOnScreen(MiningVars.rockMining))
			{
				Utilities.cameraTurnTo(MiningVars.rockMining);
			}
			
			if (!Utilities.isOnScreen(MiningVars.rockMining) || Calculations.distanceTo(MiningVars.rockMining) > 10)
			{
				Walking.walk(MiningVars.rockMining);
			}
			if (Mining.waitForRockMiningOnScreen(1500) && MiningVars.rockMining.isOnScreen() && MiningVars.rockMining.validate())
			{
				if (Players.getLocal().getAnimation() != 624)
				{
					MiningVars.rockMining.interact("Mine", MiningVars.rockMining.getDefinition().getName());
				}
				Task.sleep(800, 1300);
			}
		}
		
		Task.sleep(1000, 1200);
	}
}

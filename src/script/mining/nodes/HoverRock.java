package script.mining.nodes;

import misc.Utilities;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;

import script.mining.Mining;
import script.mining.MiningVars;


public class HoverRock extends Node
{
	@Override
	public boolean activate()
	{
		return MiningVars.requirementChecked && Inventory.getCount() <= 27 && Mining.isAtMines();
	}
	
	@Override
	public void execute()
	{
		//System.out.println("Hover rocks");
		MiningVars.action = 3;
		
		MiningVars.rockHover = Mining.findNewRock();
		
		if (MiningVars.rockHover == null) return;
		else
		{
			//System.out.println("hover rock gives me: (" + MiningVars.rockHover.getId() + ") - " + MiningVars.rockHover.getDefinition().getName() + " - " + (MiningVars.rockHover.getInstance() == null) + " - " + MiningVars.rockHover.validate());
			
			if (MiningVars.rockHover != null)
			{
				if (!Utilities.isOnScreen(MiningVars.rockHover))
				{
					Utilities.cameraTurnTo(MiningVars.rockHover);
					
					if (!Utilities.isOnScreen(MiningVars.rockHover))
					{
						Camera.setPitch(Random.nextInt(25, 30));
					}
				}
				
				Mining.waitForRockHoverOnScreen(1500);
				
				if (!MiningVars.rockHover.contains(Mouse.getLocation()))
				{
					//System.out.println("hovering over rock: " + MiningVars.rockHover.getDefinition().getName());
					Mouse.move(MiningVars.rockHover.getCentralPoint());
				}
			}
		}
	}
}

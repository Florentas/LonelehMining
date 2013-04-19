package com.loneleh.LonelehMining.script.mining.nodes;


import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import com.loneleh.LonelehMining.gui.GUI;
import com.loneleh.LonelehMining.misc.Condition;
import com.loneleh.LonelehMining.misc.Utilities;
import com.loneleh.LonelehMining.script.mining.Mining;
import com.loneleh.LonelehMining.script.mining.MiningVars;

public class MineOres extends Node
{
	WidgetChild miningWidget;
	
	@Override
	public boolean activate()
	{
		return MiningVars.requirementChecked &&
				GUI.isFinished &&
				Inventory.getCount() <= 27 && Mining.isAtMines()
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
				if (!Utilities.waitFor(new Condition()
				{
					@Override
					public boolean validate()
					{
						return !MiningVars.rockMining.validate();
					}
					
				}, 100))
				{
					return;
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
				miningWidget = Widgets.get(1213,  15);
				if (miningWidget != null && miningWidget.visible() &&
						miningWidget.getBounds().length > 0 && miningWidget.getBounds()[0].intersects(MiningVars.rockMining.getBounds()[0].getBounds2D())) //TODO fix this... array index out of bounds (0) when restarts
				{
					Walking.walk(MiningVars.rockMining);
					Task.sleep(750, 1250);
				}
				
				MiningVars.rockMining.interact("Mine", MiningVars.rockMining.getDefinition().getName());
				
				Task.sleep(100, 300);
			}
		}
		
		Task.sleep(1000, 1200);
	}
}

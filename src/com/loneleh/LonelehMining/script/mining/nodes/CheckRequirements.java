package com.loneleh.LonelehMining.script.mining.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;

import com.loneleh.LonelehMining.script.LonelehMining;
import com.loneleh.LonelehMining.script.mining.MiningVars;

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
		if (Game.isLoggedIn())
		{
			if (Equipment.getAppearanceIds() != null && 
					(Equipment.appearanceContainsOneOf(MiningVars.pickIds) || Equipment.getItem(MiningVars.pickIds) != null
					|| Inventory.contains(MiningVars.pickIds)))
			{
				MiningVars.requirementChecked = true;
				LonelehMining.revoke(this);
			}
			else
			{
				requirementNotMet();
			}
		}
	}
	
	private void requirementNotMet()
	{
		LonelehMining.logger.severe("You don't have proper requirements to mine. Double-check the requirements for your settings again.");
	}
}

package script.mining.nodes;

import misc.Condition;
import misc.Utilities;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;

import script.mining.Mining;
import script.mining.MiningVars;

public class BankOres extends Node
{
	Condition bankOpen = new Condition()
	{
		@Override
		public boolean validate()
		{
			if (!MiningVars.miningStrategy.equalsIgnoreCase("banking")) return false;
				
			return Bank.open() && Bank.isOpen() &&
					Widgets.get(Bank.WIDGET_BANK, Bank.WIDGET_BUTTON_DEPOSIT_INVENTORY) != null &&
					Widgets.get(Bank.WIDGET_BANK, Bank.WIDGET_BUTTON_DEPOSIT_INVENTORY).validate();
		}
	};
	
	Condition isDepositAll = new Condition()
	{
		@Override
		public boolean validate()
		{
			return Bank.depositInventory() || Inventory.getCount() == 0;
		}
	};
	
	@Override
	public boolean activate()
	{
		return MiningVars.requirementChecked && Inventory.isFull() && Mining.isAtBank() && NPCs.getNearest(Bank.BANK_NPC_IDS) != null;
	}

	@Override
	public void execute()
	{
		MiningVars.action = 1;
		
		depositAll(true);
	}
	
	private void depositAll(boolean close)
	{
		if (Inventory.getCount() == 0)
			return;
		
		if (!Bank.getNearest().isOnScreen())
		{
			Utilities.cameraTurnTo(SceneEntities.getNearest(Bank.BANK_BOOTH_IDS));
			Camera.setPitch(Random.nextInt(22, 72));
		}
		
		if (Utilities.waitFor(bankOpen, 15000))
		{
			Task.sleep(250, 500);
			
			Bank.depositInventory();
			
			if (Utilities.waitFor(isDepositAll, 500))
			{
				Task.sleep(250, 500);
				if (close)
				{
					Bank.close();
				}
			}
		}
	}
}

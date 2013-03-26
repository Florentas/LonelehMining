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
import org.powerbot.game.api.wrappers.node.Item;

import script.LonelehMining;
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
	
	Condition isDeposited = new Condition()
	{
		@Override
		public boolean validate()
		{
			return Inventory.getCount() <= 1;
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
		
		depositAll(true, MiningVars.pickIds);
	}
	
	private void depositAll(boolean close, int ...exclude)
	{
		if (Inventory.getCount() == 0)
			return;
		
		if (!Bank.getNearest().isOnScreen())
		{
			Utilities.cameraTurnTo(SceneEntities.getNearest(Bank.BANK_BOOTH_IDS));
			Camera.setPitch(Random.nextInt(22, 72));
		}
		
		if (Utilities.waitFor(bankOpen, 3000))
		{
			Task.sleep(250, 500);
			
			if (!Inventory.contains(exclude))
				Bank.depositInventory();
			else
			{
				for (Item i : Inventory.getAllItems(true))
				{
					boolean isOfExclude = false;
					for (int id : exclude)
						if (i.getId() == id)
							isOfExclude = true;
					if (!isOfExclude)
						if (Inventory.getCount(i.getId()) > 1)
							i.getWidgetChild().interact("Deposit-All", i.getName()); //deposit all
						else
							i.getWidgetChild().click(true);
				}
			}
			
			if (Utilities.waitFor(isDeposited, 500))
			{
				LonelehMining.submitter.submit();
				Task.sleep(250, 500);
				if (close)
				{
					Bank.close();
				}
				
			}
		}
	}
}

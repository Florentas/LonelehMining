package com.loneleh.LonelehMining.script.mining.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

import com.loneleh.LonelehMining.misc.Condition;
import com.loneleh.LonelehMining.misc.Utilities;
import com.loneleh.LonelehMining.script.LonelehMining;
import com.loneleh.LonelehMining.script.mining.Mining;
import com.loneleh.LonelehMining.script.mining.MiningVars;

public class BankOres extends Node
{
	Condition storageOpen = new Condition()
	{
		@Override
		public boolean validate()
		{
			return (Bank.getNearest() != null &&
					(Bank.open() || Bank.isOpen()) &&
					Widgets.get(Bank.WIDGET_BANK, Bank.WIDGET_BUTTON_DEPOSIT_INVENTORY) != null &&
					Widgets.get(Bank.WIDGET_BANK, Bank.WIDGET_BUTTON_DEPOSIT_INVENTORY).validate())
					||
					(DepositBox.getNearest() != null &&
					(DepositBox.open() || DepositBox.isOpen()) &&
					Widgets.get(DepositBox.WIDGET_DEPOSIT_BOX, DepositBox.WIDGET_BUTTON_DEPOSIT_INVENTORY) != null &&
					Widgets.get(DepositBox.WIDGET_DEPOSIT_BOX, DepositBox.WIDGET_BUTTON_DEPOSIT_INVENTORY).validate());
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
		return MiningVars.requirementChecked && Inventory.isFull() && Mining.isAtBank();// && (NPCs.getNearest(Bank.BANK_NPC_IDS) != null);
	}
	
	@Override
	public void execute()
	{
		if (!MiningVars.miningStrategy.equalsIgnoreCase("banking"))
			LonelehMining.revoke(this);
		
		MiningVars.action = 1;
		
		depositAll(true, MiningVars.pickIds);
	}
	
	private void depositAll(boolean close, int ...exclude)
	{
		if (Inventory.getCount() == 0)
			return;
		
		
		if (((Bank.getNearest() != null) && !Bank.getNearest().isOnScreen())
				&& ((DepositBox.getNearest() != null) && !DepositBox.getNearest().isOnScreen()))
		{
			int[] combine= new int[Bank.BANK_BOOTH_IDS.length+DepositBox.DEPOSIT_BOX_IDS.length];  
			System.arraycopy(Bank.BANK_BOOTH_IDS, 0, combine, 0, Bank.BANK_BOOTH_IDS.length); 
			System.arraycopy(DepositBox.DEPOSIT_BOX_IDS, 0, combine, Bank.BANK_BOOTH_IDS.length, DepositBox.DEPOSIT_BOX_IDS.length);
			Utilities.cameraTurnTo(SceneEntities.getNearest(combine));
			Camera.setPitch(Random.nextInt(22, 72));
		}
		
		if (Utilities.waitFor(storageOpen, 5000))
		{
			Task.sleep(250, 500);
			
			if (!Inventory.contains(exclude))
			{
				if (Bank.getNearest() != null)
					Bank.depositInventory();
				else if (DepositBox.getNearest() != null)
					DepositBox.depositInventory();
			}
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
					if (Bank.getNearest() != null)
						Bank.close();
					else if (DepositBox.getNearest() != null)
						DepositBox.close();
				}
			}
		}
	}
}

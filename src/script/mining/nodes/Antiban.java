package script.mining.nodes;

import misc.Functions;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import script.mining.Mining;
import script.mining.MiningLocation;
import script.mining.MiningVars;

public class Antiban extends Node
{
	@Override
	public boolean activate()
	{
		return MiningVars.requirementChecked;
	}
	
	@Override
	public void execute()
	{
		switch (MiningVars.action)
		{
		case 1: //BankOres
			
			break;
		case 2: //DropOres
			
			break;
		case 3: //HoverRock
			if (Functions.chanceOf(70))
			{
				Mouse.move(Mouse.getX()+Random.nextInt(-3, 3), Mouse.getY()+Random.nextInt(-3, 3));
			}
			break;
		case 4: //MineOres
			if (Functions.chanceOf(30))
			{
				Camera.setPitch(Random.nextInt(47, 99));
			}
			break;
		case 5: //WalkToBank
			if (!Functions.closeEnough(MiningLocation.getMinesToBankPath(MiningVars.miningLocation).getEnd(), 20))
			{
				if (Functions.chanceOf(15))
				{
					Thread t = new Thread()
					{
						public void run()
						{
							//System.out.println("orientation: " + (Players.getLocal().getOrientation()-90));
							Camera.setAngle(Players.getLocal().getOrientation()-90+Random.nextInt(-4, 4));
						}
					};
					t.start();
				}
			}
			break;
		case 6: //WalkToMines
			if (!Functions.closeEnough(MiningLocation.getMinesToBankPath(MiningVars.miningLocation).getStart(), 10))
			{
				if (Functions.chanceOf(15))
				{
					Thread t = new Thread()
					{
						public void run()
						{
							Camera.setAngle(Players.getLocal().getOrientation()-90+Random.nextInt(-4, 4));
						}
					};
					t.start();
				}
			}
			break;
		default:
			break;
		}
		
		//System.out.println("action = " + MiningVars.action);
		if (MiningVars.action == 3 || MiningVars.action == 4)
		{
			if ((MiningVars.rockMining == null || !MiningVars.rockMining.validate()) && Mining.findNewRock() == null)
			{
				if (Players.getLocal().isIdle() && Walking.getEnergy() < 90)
				{
					WidgetChild runIcon = Widgets.get(750, 2);
					runIcon.interact("Rest");
				}
			}
		}
	}
}

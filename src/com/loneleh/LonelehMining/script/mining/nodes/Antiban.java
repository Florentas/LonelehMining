package com.loneleh.LonelehMining.script.mining.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import com.loneleh.LonelehMining.misc.Condition;
import com.loneleh.LonelehMining.misc.Functions;
import com.loneleh.LonelehMining.misc.Utilities;
import com.loneleh.LonelehMining.script.mining.Mining;
import com.loneleh.LonelehMining.script.mining.MiningLocation;
import com.loneleh.LonelehMining.script.mining.MiningVars;

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
		case 3: //HoverRock //won't ever get here due to how the Node system works.
			//if i switch MineOres node and HoverRocks node, case 4 will never be true and case 3 will be true instead
			if (Functions.chanceOf(27))
			{
				Mouse.move(Mouse.getX()+Random.nextInt(-3, 3), Mouse.getY()+Random.nextInt(-3, 3));
			}
			break;
		case 4: //MineOres
			if (Functions.chanceOf(2))
			{
				Camera.setPitch(Random.nextInt(47, 99));
			}
			if (MiningVars.rockMining != null && MiningVars.rockMining.validate() &&
					Functions.chanceOf(27))
			{
				Mouse.move(Mouse.getX()+Random.nextInt(-3, 3), Mouse.getY()+Random.nextInt(-3, 3));
			}
			if (MiningVars.rockMining != null && MiningVars.rockMining.validate() &&
					!Utilities.waitFor(new Condition()
					{
						@Override
						public boolean validate()
						{
							return Players.getLocal().getAnimation() == 624;
						}
					}, 2000))
			{
				MiningVars.rockMining.interact("Mine", MiningVars.rockMining.getDefinition().getName());
			}
			if (MiningVars.rockMining != null && MiningVars.rockHover != null &&
					MiningVars.rockMining.getLocation() != MiningVars.rockHover.getLocation() &&
					MiningVars.rockMining.getBounds().length > 0 && MiningVars.rockHover.getBounds().length > 0 &&
					MiningVars.rockMining.getBounds()[0].intersects(MiningVars.rockHover.getBounds()[0].getBounds2D()))
			{
				if (Functions.chanceOf(20))
				{
					Camera.setAngle(Camera.getYaw()+Random.nextInt(-10, 10));
					Camera.setPitch(Camera.getPitch()+Random.nextInt(-5, 5));
				}
			}
			if (MiningVars.rockHover != null && MiningVars.rockHover.getBounds().length == 0)
			{
				Camera.setPitch(Camera.getPitch()+Random.nextInt(20, 40));
			}
			break;
		case 5: //WalkToBank
			if (Players.getLocal().getAnimation() == 16385)
			{
				if (Functions.chanceOf(30))
				{
					Camera.setAngle(Camera.getYaw()+Random.nextInt(-13, 29));
				}
			}
			if (!Functions.closeEnough(MiningLocation.getMinesToBankPath(MiningVars.miningLocation).getEnd(), 20))
			{
				if (Functions.chanceOf(15))
				{
					Thread t = new Thread()
					{
						@Override
						public void run()
						{
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
						@Override
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

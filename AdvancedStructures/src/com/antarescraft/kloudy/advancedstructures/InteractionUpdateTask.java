package com.antarescraft.kloudy.advancedstructures;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.advancedstructures.protocol.PacketManager;
import com.antarescraft.kloudy.wonderhudapi.HUDPosition;
import com.antarescraft.kloudy.wonderhudapi.WonderHUD;

public class InteractionUpdateTask extends BukkitRunnable
{
	public void start()
	{
		runTaskTimer(AdvancedStructures.plugin, 0, 2);
	}
	
	@Override
	public void run()
	{
		for(PlayerStructureInteraction interaction : AdvancedStructures.PlayerStructureInteractions.values())
		{
			if(interaction.needsUpdate())
			{
				PacketManager.updateItemLocation(interaction);
				ArrayList<String> lines = new ArrayList<String>();
				lines.add(interaction.getRotationString());
				WonderHUD.updateHUD(interaction.getPlayer(), HUDPosition.CENTER, lines);
			}
		}
	}
}
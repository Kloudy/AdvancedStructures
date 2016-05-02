package com.antarescraft.kloudy.advancedstructures.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.antarescraft.kloudy.advancedstructures.AdvancedStructures;
import com.antarescraft.kloudy.advancedstructures.PlayerStructureInteraction;

public class OnPlayerItemHeldEvent implements Listener
{
	@EventHandler
	public void playerItemHeld(PlayerItemHeldEvent event)
	{
		Player player = event.getPlayer();
		PlayerStructureInteraction interaction = AdvancedStructures.PlayerStructureInteractions.get(player.getUniqueId());
		if(interaction != null && interaction.getItem() != null)
		{
			interaction.mouseWheel();
		}
	}
}
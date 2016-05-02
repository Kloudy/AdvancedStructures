package com.antarescraft.kloudy.advancedstructures.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.antarescraft.kloudy.advancedstructures.AdvancedStructures;
import com.antarescraft.kloudy.advancedstructures.PlayerStructureInteraction;

public class OnPlayerInteractEntityEvent implements Listener
{
	@EventHandler
	public void playerInteractEntityEvent(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		
		PlayerStructureInteraction interaction = AdvancedStructures.PlayerStructureInteractions.get(player.getUniqueId());
		if(interaction != null)
		{
			System.out.println("clicked entity");
			interaction.rightClick();
		}
	}
}
package com.antarescraft.kloudy.advancedstructures.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.antarescraft.kloudy.advancedstructures.AdvancedStructures;

public class OnPlayerQuitEvent implements Listener
{
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
		AdvancedStructures.PlayerStructureInteractions.remove(player.getUniqueId());
	}
}
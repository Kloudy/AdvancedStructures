package com.antarescraft.kloudy.advancedstructures.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.antarescraft.kloudy.advancedstructures.AdvancedStructures;
import com.antarescraft.kloudy.advancedstructures.PlayerStructureInteraction;

public class OnPlayerInteractEvent implements Listener
{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		
		PlayerStructureInteraction interaction = AdvancedStructures.PlayerStructureInteractions.get(player.getUniqueId());
		if(interaction != null)
		{
			if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
			{
				interaction.leftClick();
			}
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				interaction.rightClick();
			}
		}
	}
}
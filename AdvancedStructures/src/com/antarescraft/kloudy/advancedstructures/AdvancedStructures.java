package com.antarescraft.kloudy.advancedstructures;

import java.util.Hashtable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.antarescraft.kloudy.advancedstructures.events.CommandEvent;
import com.antarescraft.kloudy.advancedstructures.events.OnPlayerInteractEntityEvent;
import com.antarescraft.kloudy.advancedstructures.events.OnPlayerInteractEvent;
import com.antarescraft.kloudy.advancedstructures.events.OnPlayerItemHeldEvent;
import com.antarescraft.kloudy.advancedstructures.events.OnPlayerQuitEvent;
import com.antarescraft.kloudy.advancedstructures.events.OnPlayerSneakEvent;
import com.antarescraft.kloudy.advancedstructures.protocol.PacketManager;

public class AdvancedStructures extends JavaPlugin
{
	public static JavaPlugin plugin;
	public static String serverVersion;
	private static final int startingEntityId = -5000;
	public static Hashtable<String, Structure> Structures;
	public static Hashtable<UUID, PlayerStructureInteraction> PlayerStructureInteractions;
	
	private static Hashtable<UUID, Integer> playerNextEntityId;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		Structures = new Hashtable<String, Structure>();
		PlayerStructureInteractions = new Hashtable<UUID, PlayerStructureInteraction>();
		playerNextEntityId = new Hashtable<UUID, Integer>();
		
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		String[] packageTokens = packageName.split("\\.");
		serverVersion = packageTokens[packageTokens.length-1];
		
		getCommand(CommandEvent.baseCommand).setExecutor(new CommandEvent());
		getServer().getPluginManager().registerEvents(new OnPlayerSneakEvent(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerInteractEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerItemHeldEvent(), this);
		
		//PacketManager.registerPacketListener();
		
		InteractionUpdateTask updateTask = new InteractionUpdateTask();
		updateTask.start();
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public static int getPlayerNextEntityId(Player player)
	{
		int entityId = startingEntityId;
		
		if(!playerNextEntityId.containsKey(player.getUniqueId()))
		{
			playerNextEntityId.put(player.getUniqueId(), entityId);
		}
		else
		{
			entityId = playerNextEntityId.get(player.getUniqueId()) - 1;
			playerNextEntityId.put(player.getUniqueId(), entityId);
		}
		
		System.out.println("Generated id: " + entityId);
		
		return entityId;
	}
}
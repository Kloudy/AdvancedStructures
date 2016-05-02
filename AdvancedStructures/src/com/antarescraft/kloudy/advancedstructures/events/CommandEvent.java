package com.antarescraft.kloudy.advancedstructures.events;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.advancedstructures.AdvancedStructures;
import com.antarescraft.kloudy.advancedstructures.PlayerStructureInteraction;
import com.antarescraft.kloudy.advancedstructures.Structure;
import com.antarescraft.kloudy.advancedstructures.util.CommandHandler;
import com.antarescraft.kloudy.advancedstructures.util.CommandParser;
import com.antarescraft.kloudy.advancedstructures.util.MessageManager;
import com.antarescraft.kloudy.wonderhudapi.HUDPosition;
import com.antarescraft.kloudy.wonderhudapi.WonderHUD;

public class CommandEvent implements CommandExecutor
{
	public static final String baseCommand = "structure";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return CommandParser.parseCommand(this, cmd.getName(), sender, args);
	}
	
	@CommandHandler(argsDescription = "", subcommand = "", description = "Author, Version, Website",
			mustBePlayer = false, numArgs = 0, permission = "structure.admin")
	public void structure(CommandSender sender, String[] args)
	{
		String message = ChatColor.BOLD + "========Advanced Structures========\n";
		message += ChatColor.GRAY + "Author: " + ChatColor.RED + "Kloudy\n";
		message += ChatColor.GRAY + "Version: " + ChatColor.AQUA + AdvancedStructures.plugin.getDescription().getVersion() + "\n";
		message += ChatColor.GREEN + "Website: " + ChatColor.GRAY + "" + ChatColor.UNDERLINE + "playminecraft.net\n";
		message += ChatColor.WHITE + "" + ChatColor.BOLD + "=================================";
		
		sender.sendMessage(message);
	}
	
	@CommandHandler(argsDescription = "", subcommand = "help", description = "Displays command help information",
			mustBePlayer = false, numArgs = 1, permission = "structure.use")
	public void help(CommandSender sender, String[] args)
	{
		MessageManager.pageList(sender, CommandParser.gatherHelpStrings(this), "AdvancedStructures Help");
	}
	
	@CommandHandler(argsDescription = "<name>", subcommand = "create", description = "Create a new structure with the given <name>",
			mustBePlayer = true, numArgs = 2, permission = "structure.use")
	public void create(CommandSender sender, String[] args)
	{
		Player player = (Player)sender;
		
		Structure structure = new Structure(args[1], player.getUniqueId());
		AdvancedStructures.Structures.put(args[1], structure);
		AdvancedStructures.PlayerStructureInteractions.put(player.getUniqueId(), new PlayerStructureInteraction(player, structure));
		System.out.println("Structure" + structure);
		
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇");
		lines.add("▇                                                                         ▇");
		lines.add("▇              " + ChatColor.GOLD + ChatColor.BOLD + "Advanced Structures - Controls" + ChatColor.RESET + "           ▇");
		lines.add("▇                                                                           ▇");
		lines.add("▇   * " + ChatColor.GREEN  + ChatColor.BOLD + "Left click: " + ChatColor.RESET + " Select item in hand / Set item placement    ▇");
		lines.add("▇   * " + ChatColor.GREEN  + ChatColor.BOLD + "Mouse-wheel: " + ChatColor.RESET + " Rotate selected item                        ▇");
		lines.add("▇   * " + ChatColor.GREEN + ChatColor.BOLD + "Right click: " + ChatColor.RESET + " Select axis of rotation                         ▇");
		lines.add("▇   * " + ChatColor.GREEN + ChatColor.BOLD  + "Crouch: " + ChatColor.RESET + "Deselect item                                            ▇");
		lines.add("▇                                                                              ▇");
		lines.add("▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇");
		WonderHUD.spawnHUD(player, HUDPosition.TOP_CENTER, 10, lines);
	}
	
	@CommandHandler(argsDescription = "", subcommand = "finish", description = "Finish editing a structure",
			mustBePlayer = true, numArgs = 1, permission = "structure.use")
	public void finish(CommandSender sender, String[] args)
	{
		Player player = (Player)sender;
		
		AdvancedStructures.PlayerStructureInteractions.remove(player.getUniqueId());
		WonderHUD.removeAllHUDs(player);
	}
	
	@CommandHandler(argsDescription = "<name>", subcommand = "edit", description = "Edit an exising structure",
			mustBePlayer = true, numArgs = 2, permission = "structure.use")
	public void edit(CommandSender sender, String[] args)
	{
		//Player player = (Player)sender;
	}
}
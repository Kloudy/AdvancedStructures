package com.antarescraft.kloudy.advancedstructures;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Structure
{
	private String name;
	private UUID owner;
	private ArrayList<StructureItem> structureItems;
	private boolean beingEdited;
	
	public Structure(String name, UUID owner)
	{
		this.name = name;
		this.owner = owner;
		structureItems = new ArrayList<StructureItem>();
		beingEdited = true;
	}
	
	public String getName()
	{
		return name;
	}
	
	public UUID getOwner()
	{
		return owner;
	}
	
	public boolean isBeingEdited()
	{
		return beingEdited;
	}
	
	public void setBeingEdited(boolean beingEdited)
	{
		this.beingEdited = beingEdited;
	}
	
	public ArrayList<StructureItem> getStructureItems()
	{
		return structureItems;
	}
	
	public void addStructureItem(int entityId, Location location, double pitch, double yaw, ItemStack item)
	{
		structureItems.add(new StructureItem(location, pitch, yaw, item));
	}
	
	public class StructureItem
	{
		private Location location;
		private ItemStack item;
		private double pitch;
		private double yaw;
		
		public StructureItem(Location location, double pitch, double yaw, ItemStack item)
		{
			this.location = location;
			this.pitch = pitch;
			this.yaw = yaw;
			this.item = item;
		}
		
		public Location getLocation()
		{
			return location;
		}
		
		public double getPitch()
		{
			return pitch;
		}
		
		public void setPitch(double pitch)
		{
			this.pitch = pitch;
		}
		
		public double getYaw()
		{
			return yaw;
		}
		
		public void setYaw(double yaw)
		{
			this.yaw = yaw;
		}
		
		public ItemStack getItem()
		{
			return item;
		}
	}
}
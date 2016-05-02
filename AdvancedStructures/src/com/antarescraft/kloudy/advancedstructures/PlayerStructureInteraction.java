package com.antarescraft.kloudy.advancedstructures;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.antarescraft.kloudy.advancedstructures.protocol.PacketManager;
import com.antarescraft.kloudy.wonderhudapi.HUDPosition;
import com.antarescraft.kloudy.wonderhudapi.WonderHUD;

public class PlayerStructureInteraction
{
	private Player player;
	private Structure structure;
	private ItemStack item;
	private Location prevLocation;
	
	private int prevSlot = 0;//used to keep track of which way the user is mouse-wheeling
	private float yaw = 0;
	private float pitch = 90;
	private int entityId; 
	private boolean rotated = false;
	private Axis rotationAxis = Axis.y;
	private int xRotationOffset = 0;
	private int yRotationOffset = 0;
	private int zRotationOffset = 0;
	private boolean click = false;
	
	public PlayerStructureInteraction(Player player, Structure structure)
	{
		this.player = player;
		this.structure = structure;
		this.item = player.getItemInHand();
		entityId = 0;
		prevLocation = player.getLocation();
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Structure getStructure()
	{
		return structure;
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public void setItem(ItemStack item)
	{
		this.item = item;
	}
	
	public int getEntityId()
	{
		return entityId;
	}
	
	public boolean needsUpdate()
	{
		boolean needsUpdate = ((moved() || rotated) && item != null);
		rotated = false;
		
		return needsUpdate;
	}
	
	public void setEntityId(int entityId)
	{
		this.entityId = entityId;
	}
	
	public float getYaw()
	{
		return player.getLocation().getYaw() + 180 + 90;
	}
	
	public float getPitch()
	{
		return player.getLocation().getPitch();
	}
	
	private boolean moved()
	{
		Location loc = player.getLocation();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double yaw = loc.getYaw();
		double pitch = loc.getPitch();
		
		double pX = prevLocation.getX();
		double pY = prevLocation.getY();
		double pZ = prevLocation.getZ();
		double pYaw = prevLocation.getYaw();
		double pPitch = prevLocation.getPitch();
		
		boolean moved = false;
		if(x != pX || y != pY || z != pZ || yaw != pYaw || pitch != pPitch)
		{
			moved = true;
		}
		
		prevLocation = loc.clone();
		
		return moved;
	}
	
	public void leftClick()
	{
		if(click)//user has left clicked to set the item into the structure
		{
			structure.addStructureItem(entityId, prevLocation, pitch, yaw, item.clone());
			resetRotationOffset();
			//WonderHUD.removeHUD(player, HUDPosition.CENTER);
			click = false;
			item = null;
		}
	}
	
	public void rightClick()
	{
		if(!click)
		{
			item = player.getItemInHand();
			if(item != null)
			{
				entityId = AdvancedStructures.getPlayerNextEntityId(player);
				PacketManager.spawnFloatingItem(this);

				ArrayList<String> lines = new ArrayList<String>();
				lines.add(getRotationString());
				//WonderHUD.spawnHUD(player, HUDPosition.CENTER, 1, lines, 4, Math.PI / 800);
				click = true;
				//WonderHUD.spawnHUD(player, HUDPosition.BOTTOM_CENTER, 1, lines);
			}
		}
		
	}
	
	public void mouseWheel()
	{
		item = player.getItemInHand();
		rotated = true;
		
		int currentSlot = player.getInventory().getHeldItemSlot();
		
		if(prevSlot == 0)
		{
			if(currentSlot == 8)//scolling down
			{
				updateRotationOffset(false);
			}
			else
			{
				updateRotationOffset(true);
			}
		}
		else if(prevSlot == 8)
		{
			if(currentSlot == 0)//scrolling up
			{
				updateRotationOffset(true);
			}
			else
			{
				updateRotationOffset(false);
			}
		}
		else
		{
			if(prevSlot < currentSlot)
			{
				updateRotationOffset(true);
			}
			else
			{
				updateRotationOffset(false);
			}
		}
		
		prevSlot = currentSlot;
	}
	
	public String getRotationString()
	{
		String rotationStr = new String();
		if(rotationAxis == Axis.x)
		{
			rotationStr = "Axis of Rotation: x " + Math.round(getYaw() + xRotationOffset) + "°";
		}
		else if(rotationAxis == Axis.y)
		{
			rotationStr = "Axis of Rotation: y " +Math.round( getPitch() + yRotationOffset) + "°";
		}
		else
		{
			rotationStr = "Axis of Rotation: z " + zRotationOffset + "°";
		}
		
		return rotationStr;
	}
	
	public int getXRotationOffset()
	{
		return xRotationOffset;
	}
	
	public int getYRotationOffset()
	{
		return yRotationOffset;
	}
	
	public int getZRotationOffset()
	{
		return zRotationOffset;
	}
	
	private void resetRotationOffset()
	{
		xRotationOffset = 0;
		yRotationOffset = 0;
		zRotationOffset = 0;
	}
	
	private void updateRotationOffset(boolean scrollDirection)//scrollDirection false-down, scrollDirection true-up
	{
		if(scrollDirection)
		{
			if(rotationAxis == Axis.x)
			{
				xRotationOffset += 2;
			}
			else if(rotationAxis == Axis.y)
			{
				yRotationOffset += 2;
			}
			else
			{
				zRotationOffset += 2;
			}
		}
		else
		{
			if(rotationAxis == Axis.x)
			{
				xRotationOffset -= 2;
			}
			else if(rotationAxis == Axis.y)
			{
				yRotationOffset -= 2;
			}
			else
			{
				zRotationOffset -= 2;
			}
		}
	}
	
	public void crouch()
	{
		
	}
	
	private enum Axis
	{
		x,
		y,
		z
	}
}
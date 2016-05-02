package com.antarescraft.kloudy.advancedstructures.protocol;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.advancedstructures.AdvancedStructures;
import com.antarescraft.kloudy.advancedstructures.PlayerStructureInteraction;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

public class PacketManager 
{
	private static final int ENTITY_FLAGS_INDEX = 0;
	private static final int ARMORSTAND_FLAGS_INDEX = 10;
	private static final int CUSTOM_NAME_VISIBLE_INDEX = 3;
	private static final int ARMORSTAND_RIGHT_ARM_INDEX = 14;
	
	public static void registerPacketListener()
	{
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(new PacketAdapter(AdvancedStructures.plugin,
				PacketType.Play.Client.ARM_ANIMATION)
		{
			@Override
			public void onPacketReceiving(PacketEvent event)
			{
				if(event.getPacketType() == PacketType.Play.Client.ARM_ANIMATION)//capture left click
				{
					Player player = event.getPlayer();
					PlayerStructureInteraction interaction = AdvancedStructures.PlayerStructureInteractions.get(player.getUniqueId());
					if(interaction != null)
					{
						System.out.println("Left click handler");
						interaction.leftClick();
					}
				}
			}
			
			@Override
			public void onPacketSending(PacketEvent event)
			{
				
			}
		});
	}
	
	public static void spawnFloatingItem(PlayerStructureInteraction interaction)
	{
		try
		{
			Player player = interaction.getPlayer();
			int entityId = interaction.getEntityId();
			
			WrapperPlayServerSpawnEntityLiving spawnPacket = new WrapperPlayServerSpawnEntityLiving();
			spawnPacket.setEntityID(entityId);
			spawnPacket.setType(EntityType.ARMOR_STAND);
			
			double x = player.getLocation().getX() + (player.getLocation().getDirection().getX() * 2);
			double y = player.getLocation().getY() + (player.getLocation().getDirection().getX() * 2);
			double z = player.getLocation().getZ() + (player.getLocation().getDirection().getZ() * 2);
			
			spawnPacket.setX(x);
			spawnPacket.setY(y);
			spawnPacket.setZ(z);
			spawnPacket.setYaw(interaction.getYaw());
			
			byte entityFlags = 32;//00100000 - Invisible flag is the 6th bit
			//byte armorStandFlags = 1;//00000001 - Small armorStand is first bit
			byte armorStandFlags = 0x4;
			
			Class<?> c = Class.forName("net.minecraft.server." + AdvancedStructures.serverVersion + ".Vector3f");
			Object vector3fObj = c.getConstructor(float.class, float.class, float.class).newInstance(-90f, -90f, 0f);
			
			WrappedDataWatcher metadata = spawnPacket.getMetadata();
			metadata.setObject(ENTITY_FLAGS_INDEX, entityFlags);
			metadata.setObject(ARMORSTAND_FLAGS_INDEX, armorStandFlags);
			metadata.setObject(CUSTOM_NAME_VISIBLE_INDEX, (byte)0);
			metadata.setObject(ARMORSTAND_RIGHT_ARM_INDEX, vector3fObj);
			spawnPacket.setMetadata(metadata);
			spawnPacket.sendPacket(player);

			//int slot = (interaction.getItem().getType().isBlock()) ? 4 : 0;//block goes on head, item in hand
			WrapperPlayServerEntityEquipment equipmentPacket = new WrapperPlayServerEntityEquipment();
			equipmentPacket.setEntityID(entityId);
			equipmentPacket.setSlot(0);
			equipmentPacket.setItem(interaction.getItem());
			equipmentPacket.sendPacket(player);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void updateItemLocation(PlayerStructureInteraction interaction)
	{
		Player player = interaction.getPlayer();
		int entityId = interaction.getEntityId();
		
		double x = player.getLocation().getX() + (2 * player.getLocation().getDirection().getX());
		double y = player.getLocation().getY() + (2 * player.getLocation().getDirection().getY());
		double z = player.getLocation().getZ() + (2 * player.getLocation().getDirection().getZ());
		
		//teleport present ArmorStand
		WrapperPlayServerEntityTeleport teleportPacket = new WrapperPlayServerEntityTeleport();
		teleportPacket.setEntityID(entityId);
		teleportPacket.setX(x);
		teleportPacket.setY(y);
		teleportPacket.setZ(z);
		teleportPacket.setYaw(interaction.getYaw() + interaction.getXRotationOffset());
		teleportPacket.sendPacket(player);
		
		try {
			WrapperPlayServerEntityMetadata entityMetadataPacket = new WrapperPlayServerEntityMetadata();
			entityMetadataPacket.setEntityID(entityId);
			List<WrappedWatchableObject> metadata = entityMetadataPacket.getMetadata();
			
			byte entityFlags = 32;//00100000 - Invisible flag is the 6th bit
			byte armorStandFlags = 0x4;//00000000 - All ArmorStand flags false
			
			Class<?> c = Class.forName("net.minecraft.server." + AdvancedStructures.serverVersion + ".Vector3f");
			Object vector3fObj = c.getConstructor(float.class, float.class, float.class).newInstance(-90f, -90f, interaction.getYRotationOffset());
			
			metadata.add(new WrappedWatchableObject(ENTITY_FLAGS_INDEX, entityFlags));
			metadata.add(new WrappedWatchableObject(ARMORSTAND_FLAGS_INDEX, armorStandFlags));
			metadata.add(new WrappedWatchableObject(CUSTOM_NAME_VISIBLE_INDEX, (byte)0));
			metadata.add(new WrappedWatchableObject(ARMORSTAND_RIGHT_ARM_INDEX, vector3fObj));
			
			entityMetadataPacket.setMetadata(metadata);
			entityMetadataPacket.sendPacket(player);
		}
		catch(Exception e){}
	}
}
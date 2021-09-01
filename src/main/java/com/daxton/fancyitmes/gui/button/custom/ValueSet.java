package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class ValueSet implements GuiAction {

	final GUI gui;
	final Player player;
	final FileConfiguration config;
	final String key;

	public ValueSet(Player player, GUI gui, FileConfiguration config, String key){
		this.gui = gui;
		this.player = player;
		this.config = config;
		this.key = key;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID uuid = player.getUniqueId();
			//編輯物品用值
			String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
			editKey[3] = key;
			ManagerItems.player_ItemEditArray.put(uuid, editKey);

			gui.setGuiChatAction(new ChatValueSet(gui));
			gui.close();
			gui.setChat(true);
		}
	}



}

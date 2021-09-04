package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiChatAction;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class ValueSet implements GuiAction, GuiChatAction {

	final GUI gui;
	final Player player;
	final FileConfiguration config;
	final String key;
	final int page;

	public ValueSet(Player player, GUI gui, FileConfiguration config, String key, int page){
		this.gui = gui;
		this.player = player;
		this.config = config;
		this.key = key;
		this.page = page;
	}

	public void execute(Player player, String value){
		UUID uuid = player.getUniqueId();
		//物品編輯
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		itemConfig.set(itemID+".CustomValue."+key, value);
		CustomKeyList.valueList(player, gui, config, page);
		gui.open(gui);
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){

			gui.setGuiChatAction(this);
			gui.close();
			gui.setChat(true);
		}
		if(clickType == ClickType.RIGHT){
			//物品編輯
			UUID uuid = player.getUniqueId();
			String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
			String itemType = editKey[0];
			String itemID = editKey[1];
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
			itemConfig.set(itemID+".CustomValue."+key, null);
			CustomKeyList.valueList(player, gui, config, page);
		}
	}



}

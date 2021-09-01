package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiChatAction;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatValueSet implements GuiChatAction {

	final GUI gui;
	public ChatValueSet(GUI gui){
		this.gui = gui;
	}

	public void execute(Player player, String value){
		UUID uuid = player.getUniqueId();
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String oType = editKey[2];
		String itemType = editKey[0];
		String itemID = editKey[1];
		String key = editKey[3];

		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		if(oType.equalsIgnoreCase("CustomValue")){
			itemConfig.set(itemID+".CustomValue."+key, value);
			GUI gui = ManagerItems.gui_Map.get(uuid);
			ValueList.valueList(player, gui, itemConfig);
			gui.open(gui);
		}

	}

}

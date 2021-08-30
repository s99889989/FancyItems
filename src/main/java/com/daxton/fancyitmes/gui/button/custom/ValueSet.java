package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.FancyItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

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
			FancyItems.fancyItems.getLogger().info("設定值"+key);
			gui.setGuiChatAction(new ChatValueSet(gui));
			gui.close();
			gui.setChat(true);
		}
	}

}

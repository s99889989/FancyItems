package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class NextCustom implements GuiAction {

	final GUI gui;
	final Player player;
	final FileConfiguration config;
	int page;

	public NextCustom(Player player, GUI gui, FileConfiguration config, int page){
		this.gui = gui;
		this.player = player;
		this.config = config;
		this.page = page;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			page += 1;
			CustomKeyList.valueList(player, gui, config, page);
		}
	}

}

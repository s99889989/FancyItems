package com.daxton.fancyitmes.gui.button.top;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.task.Reload;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class LoadMenu implements GuiAction {

	final Player player;
	final GUI gui;

	public LoadMenu(Player player, GUI gui){
		this.player = player;
		this.gui = gui;

	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			FileConfig.reload();
		}
	}

}

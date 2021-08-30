package com.daxton.fancyitmes.gui.button.top;

import com.daxton.fancycore.FancyCore;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.gui.MainMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class ToMain implements GuiAction {

	final Player player;
	//回到主頁
	public ToMain(Player player){
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			MainMenu.open(player);
		}
	}

}

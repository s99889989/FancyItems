package com.daxton.fancyitmes.gui.button.placemodel;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class PlaceModelMain implements GuiAction {

	final GUI gui;
	final Player player;

	public PlaceModelMain(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){

		}
	}

}

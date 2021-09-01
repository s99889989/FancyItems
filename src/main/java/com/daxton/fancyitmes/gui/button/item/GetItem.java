package com.daxton.fancyitmes.gui.button.item;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class GetItem implements GuiAction{

	final Player player;
	final ItemStack itemStack;
	final GUI gui;
	final String itemType;
	final String itemID;
	//獲取物品
	public GetItem(Player player, ItemStack itemStack, GUI gui, String itemType, String itemID){
		this.player = player;
		this.itemStack = itemStack;
		this.gui = gui;
		this.itemType = itemType;
		this.itemID = itemID;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			player.getInventory().addItem(itemStack);
		}
		if(clickType == ClickType.RIGHT){
			EditItem.set(player, gui, itemType, itemID);
		}
	}


}

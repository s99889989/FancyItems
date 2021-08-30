package com.daxton.fancyitmes.gui.button.item;

import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class GetItem implements GuiAction{

	final Player player;
	final ItemStack itemStack;
	//獲取物品
	public GetItem(Player player, ItemStack itemStack){
		this.player = player;
		this.itemStack = itemStack;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			player.getInventory().addItem(itemStack);
		}
	}


}

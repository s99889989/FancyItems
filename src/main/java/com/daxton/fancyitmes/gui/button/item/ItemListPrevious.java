package com.daxton.fancyitmes.gui.button.item;

import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.gui.button.typelist.ItemType;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class ItemListPrevious implements GuiAction {

	final ItemType itemType;
	int page;

	public ItemListPrevious(ItemType itemType, int page){
		this.itemType = itemType;
		this.page = page;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		itemType.ppp(page-1);
	}

}

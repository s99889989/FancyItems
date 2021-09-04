package com.daxton.fancyitmes.gui.button.item;

import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.gui.button.main.ItemType;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class ItemListNext implements GuiAction {

	final ItemType itemType;
	int page;

	public ItemListNext(ItemType itemType, int page){
		this.itemType = itemType;
		this.page = page;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			itemType.ppp(page+1);
		}
	}

}

package com.daxton.fancyitmes.gui.button.item.edit;

import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class ItemTypListPrevious implements GuiAction {

	final ChatEdit chatEdit;
	int page;

	public ItemTypListPrevious(ChatEdit chatEdit, int page){
		this.chatEdit = chatEdit;
		this.page = page;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			chatEdit.ppp(page-1);
		}
	}

}

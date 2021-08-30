package com.daxton.fancyitmes.gui.button.item.edit;

import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class ItemTypListNext implements GuiAction {

	final ChatEdit chatEdit;
	int page;

	public ItemTypListNext(ChatEdit chatEdit, int page){
		this.chatEdit = chatEdit;
		this.page = page;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			chatEdit.ppp(page+1);
		}
	}

}

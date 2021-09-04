package com.daxton.fancyitmes.gui.button.main;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.gui.MainMenu;
import com.daxton.fancyitmes.gui.other.ItemTypeList;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class TypeNextPage implements GuiAction {

	final Player player;
	final GUI gui;

	//物品類型下一頁
	public TypeNextPage(Player player, GUI gui){
		this.player = player;
		this.gui = gui;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID uuid = player.getUniqueId();
			int page = ManagerItems.type_Page.get(uuid);
			ManagerItems.type_Page.put(uuid, page+1);

			gui.clearButtonFrom(10, 54);
			ItemTypeList.setItemType(player, gui);

		}
	}

}

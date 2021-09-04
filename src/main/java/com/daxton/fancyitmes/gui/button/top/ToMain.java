package com.daxton.fancyitmes.gui.button.top;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.gui.MainMenu;
import com.daxton.fancyitmes.gui.other.ItemTypeList;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class ToMain implements GuiAction {

	final Player player;
	//回到主頁
	public ToMain(Player player){
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID uuid = player.getUniqueId();
			GUI gui = ManagerItems.gui_Map.get(uuid);
			gui.removeButton(1, 5);
			gui.clearButtonFrom(10, 54);
			//物品類別列表
			ItemTypeList.setItemType(player, gui);
		}
	}

}

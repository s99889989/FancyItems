package com.daxton.fancyitmes.gui.button.attributes;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.List;
import java.util.UUID;

public class DefineAttr implements GuiAction {

	final Player player;
	final GUI gui;
	final EditAttr editAttr;
	final int place;

	public DefineAttr(Player player, GUI gui, EditAttr editAttr, int place){
		this.player = player;
		this.gui = gui;
		this.editAttr = editAttr;
		this.place = place;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID uuid = player.getUniqueId();
			//編輯物品用值
			String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
			String itemType = editKey[0];
			String itemID = editKey[1];
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

			List<String> attrList = itemConfig.getStringList(itemID+".Attributes");
			attrList.set(place, editAttr.body+":"+editAttr.name+":"+editAttr.operation+":"+editAttr.amount);

			itemConfig.set(itemID+".Attributes", attrList);

			AttrEditMain.attrList(player, gui);
		}
	}

}

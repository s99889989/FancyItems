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

public class AddAttr implements GuiAction {

	final GUI gui;
	final Player player;

	public AddAttr(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
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
			attrList.add("HAND:GENERIC_ATTACK_DAMAGE:ADD_NUMBER:1");

			itemConfig.set(itemID+".Attributes", attrList);

			AttrEditMain.attrList(player, gui);
		}
	}

}

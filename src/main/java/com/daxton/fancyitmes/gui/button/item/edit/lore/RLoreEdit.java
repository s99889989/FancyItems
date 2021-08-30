package com.daxton.fancyitmes.gui.button.item.edit.lore;

import com.daxton.fancycore.api.character.conversion.StringConversion;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.EditItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.List;
import java.util.UUID;

public class RLoreEdit implements GuiAction {

	final Player player;
	final GUI gui;
	final String itemType;
	final String itemID;
	final String editString;
	final int key;

	public RLoreEdit(Player player, GUI gui, String itemType, String itemID, String editString, int key){
		this.player = player;
		this.gui = gui;
		this.itemType = itemType;
		this.itemID = itemID;
		this.editString = editString;
		this.key = key;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID uuid = player.getUniqueId();
			gui.close();
			player.sendTitle(" ", "請輸入要增加的Lore", 1, 40, 1);
			ManagerItems.player_Chat_Select.put(uuid, "RLore");
			ManagerItems.player_ItemEditString.put(uuid, editString+"."+itemType+"."+itemID+"."+key);
		}
		if(clickType == ClickType.RIGHT){
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
			List<String> loreList = itemConfig.getStringList(itemID+".Lore");

			loreList.remove(key);

			itemConfig.set(itemID+".Lore", loreList);
			LoreEdit.right(player, gui, itemType, itemID);
		}
	}

	//編輯Lore
	public static void editLore(Player player, String value){
		UUID uuid = player.getUniqueId();
		String itemString = ManagerItems.player_ItemEditString.get(uuid);
		if(itemString == null || itemString.split("\\.").length !=4){
			return;
		}

		String[] kkk = itemString.split("\\.");
		String key = kkk[0];
		String itemType = kkk[1];
		String itemID = kkk[2];
		int p = Integer.parseInt(kkk[3]);
		ManagerItems.player_ItemEditString.put(uuid, "");

		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		List<String> loreList = itemConfig.getStringList(itemID+".Lore");

		if(key.equalsIgnoreCase("LoreFront")){
			loreList.add(0, StringConversion.getColorString(value));
		}
		if(key.equalsIgnoreCase("LoreEdit")){
			loreList.set(p, StringConversion.getColorString(value));
		}
		if(key.equalsIgnoreCase("LoreAdd")){
			loreList.add(StringConversion.getColorString(value));
		}

		itemConfig.set(itemID+".Lore", loreList);

		GUI gui = ManagerItems.gui_Map.get(uuid);
		LoreEdit.right(player, gui, itemType, itemID);
		gui.open(gui);
	}

}

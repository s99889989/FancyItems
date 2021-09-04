package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomList {

	public static List<String> getCustomList(Player player){
		List<String> customList = new ArrayList<>();
		UUID uuid = player.getUniqueId();
		//編輯物品用值
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
		if(itemConfig.contains(itemID+".CustomValue")){
			for(String key : itemConfig.getConfigurationSection(itemID+".CustomValue").getKeys(false)){
				String value = itemConfig.getString(itemID+".CustomValue."+key);

				customList.add("§f"+key+":"+value);
			}
		}

		return customList;
	}

}

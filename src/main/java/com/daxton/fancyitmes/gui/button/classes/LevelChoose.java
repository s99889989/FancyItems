package com.daxton.fancyitmes.gui.button.classes;

import com.daxton.fancyclasses.config.FileConfig;
import com.daxton.fancycore.api.config.SearchConfigMap;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class LevelChoose implements GuiAction {

	final GUI gui;
	final Player player;

	public LevelChoose(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if (Bukkit.getServer().getPluginManager().getPlugin("FancyClasses") != null || Bukkit.getPluginManager().isPluginEnabled("FancyClasses")){
			if(clickType == ClickType.LEFT){
				gui.clearButtonFrom(10, 54);
				Integer[] ignore = new Integer[]{18, 19, 27, 28, 36, 37};
				List<String> levelList = SearchConfigMap.fileNameList(FileConfig.config_Map, "level/", true);
				for(String levelName : levelList){

					//等級
					ItemStack classItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Classes.LevelList");

					Map<String, String> classMap = new HashMap<>();
					classMap.put("{type}", levelName);
					GuiEditItem.replaceName(classItem, classMap);

					Map<String, String> classBooleanMap = new HashMap<>();
					String nowLevel = getLevel(player, levelName);
					classBooleanMap.put("{level}", nowLevel);
					GuiEditItem.replaceLore(classItem, classBooleanMap);

					GuiButton classButton = GuiButton.ButtonBuilder.getInstance().
						setItemStack(classItem).
						setGuiAction(new LevelChange(player, gui, levelName, Integer.parseInt(nowLevel))).
						build();

					gui.addButton(classButton, 11, 44, ignore);


				}


			}
		}

	}

	public static String getLevel(Player player, String levelName){
		String b = "0";
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = com.daxton.fancyitmes.config.FileConfig.config_Map.get("item/"+itemType+".yml");
		if(itemConfig.contains(itemID+".Classes.Level."+levelName)){
			return itemConfig.getString(itemID+".Classes.Level."+levelName);
		}

		return b;
	}

}

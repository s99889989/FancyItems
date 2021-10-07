package com.daxton.fancyitmes.gui.button.classes;

import com.daxton.fancyclasses.config.FileConfig;
import com.daxton.fancycore.api.config.SearchConfigMap;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.FancyItems;
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

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class ClassesChoose implements GuiAction {

	final GUI gui;
	final Player player;

	public ClassesChoose(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if (Bukkit.getServer().getPluginManager().getPlugin("FancyClasses") != null || Bukkit.getPluginManager().isPluginEnabled("FancyClasses")){
			if(clickType == ClickType.LEFT){
				gui.clearButtonFrom(10, 54);
				Integer[] ignore = new Integer[]{18, 19, 27, 28, 36, 37};
				List<String> allClassList = SearchConfigMap.fileNameList(FileConfig.config_Map, "class/", false);
				for(String classString : allClassList){

					FileConfiguration classConfig = FileConfig.config_Map.get("class/"+classString);
					String className = classConfig.getString("Class_Name");
					//FancyItems.fancyItems.getLogger().info(className);

					//職業
					ItemStack classItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Classes.ClassList");

					Map<String, String> classMap = new HashMap<>();
					classMap.put("{class}", className);
					GuiEditItem.replaceName(classItem, classMap);

					Map<String, String> classBooleanMap = new HashMap<>();
					String b = contentClass(player, className);
					classBooleanMap.put("{boolean}", b);
					GuiEditItem.replaceLore(classItem, classBooleanMap);

					GuiButton classButton = GuiButton.ButtonBuilder.getInstance().
						setItemStack(classItem).
						setGuiAction(new ClassesSelect(player, gui, className, Boolean.parseBoolean(b))).
						build();

					gui.addButton(classButton, 11, 44, ignore);


				}


			}
		}
	}


	public static String contentClass(Player player, String className){
		String b = "false";
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = com.daxton.fancyitmes.config.FileConfig.config_Map.get("item/"+itemType+".yml");

		List<String> classesList = itemConfig.getStringList(itemID+".Classes.Class");
		if(classesList.contains(className)){
			b = "true";
		}

		return b;
	}

}

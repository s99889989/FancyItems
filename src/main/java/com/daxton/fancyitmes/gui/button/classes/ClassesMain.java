package com.daxton.fancyitmes.gui.button.classes;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.cd.CDLeft;
import com.daxton.fancyitmes.gui.button.cd.CDRight;
import com.daxton.fancyitmes.gui.select.SelectItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class ClassesMain implements GuiAction {

	final GUI gui;
	final Player player;

	public ClassesMain(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			gui.clearButtonFrom(10, 54);

			//職業
			ItemStack classItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Classes.Class");
			Map<String, String> classMap = new HashMap<>();
			classMap.put("{class}", getClassesString(player));
			GuiEditItem.replaceLore(classItem, classMap);
			GuiButton classButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(classItem).
				setGuiAction(new ClassesChoose(player, gui)).
				build();
			gui.setButton(classButton, 2, 2);

			//等級
			ItemStack levelItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Classes.Level");
			Map<String, String> levelMap = new HashMap<>();
			levelMap.put("{level}", getLevelString(player));
			GuiEditItem.replaceLore(levelItem, levelMap);
			GuiButton levelButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(levelItem).
				setGuiAction(new LevelChoose(player, gui)).
				build();
			gui.setButton(levelButton, 2, 3);
		}
	}

	//把職業列表轉成字串
	public static String getClassesString(Player player){
		StringBuilder classesString = new StringBuilder();

		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		List<String> classesList = itemConfig.getStringList(itemID+".Classes.Class");
		for(int i = 0 ;i < classesList.size() ; i++){
			if(i != 0){
				classesString.append(",");
			}
			String cl = classesList.get(i);
			classesString.append(cl);
		}
		return classesString.toString();
	}

	//把等級列表轉成字串
	public static String getLevelString(Player player){
		StringBuilder levelString = new StringBuilder();

		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		if(itemConfig.contains(itemID+".Classes.Level")){
			List<String> levelList = new ArrayList<>(itemConfig.getConfigurationSection(itemID+".Classes.Level").getKeys(false));
			for(int i = 0 ;i < levelList.size() ; i++){
				if(i != 0){
					levelString.append(",");
				}
				String key = levelList.get(i);
				int value = itemConfig.getInt(itemID+".Classes.Level."+key);

				levelString.append(key).append(".").append(value);
			}
		}

		return levelString.toString();
	}

}

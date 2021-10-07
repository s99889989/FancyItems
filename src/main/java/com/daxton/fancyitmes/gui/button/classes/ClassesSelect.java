package com.daxton.fancyitmes.gui.button.classes;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class ClassesSelect implements GuiAction {

	final GUI gui;
	final Player player;
	final String className;
	boolean b;

	public ClassesSelect(Player player, GUI gui, String className, boolean b){
		this.gui = gui;
		this.player = player;
		this.className = className;
		this.b = b;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		int place = slot+1;
		if(clickType == ClickType.LEFT){
			b = !b;
			//職業
			ItemStack classItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Classes.ClassList");

			Map<String, String> classMap = new HashMap<>();
			classMap.put("{class}", className);
			GuiEditItem.replaceName(classItem, classMap);

			Map<String, String> classBooleanMap = new HashMap<>();
			classBooleanMap.put("{boolean}", String.valueOf(b));
			GuiEditItem.replaceLore(classItem, classBooleanMap);

			GuiButton classButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(classItem).
				setGuiAction(this).
				build();

			gui.setButton(classButton, place);
			set();
		}
	}

	public void set(){

		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = com.daxton.fancyitmes.config.FileConfig.config_Map.get("item/"+itemType+".yml");

		List<String> classesList = itemConfig.getStringList(itemID+".Classes.Class");

		if(b){
			classesList.add(className);
		}else {
			classesList.remove(className);
		}

		itemConfig.set(itemID+".Classes.Class", classesList);
	}

}

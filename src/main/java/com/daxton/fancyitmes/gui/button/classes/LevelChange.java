package com.daxton.fancyitmes.gui.button.classes;

import com.daxton.fancycore.api.character.conversion.StringConversion;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.button.GuiChatAction;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancycore.api.judgment.NumberJudgment;
import com.daxton.fancyitmes.gui.select.SelectItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class LevelChange implements GuiAction , GuiChatAction {

	final GUI gui;
	final Player player;
	final String levelName;
	int nowLevel;
	int place;

	public LevelChange(Player player, GUI gui, String levelName, int nowLevel){
		this.gui = gui;
		this.player = player;
		this.levelName = levelName;
		this.nowLevel = nowLevel;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		place = slot+1;
		if(clickType == ClickType.LEFT){
			nowLevel ++;
			setLevel();
			display();
		}
		if(clickType == ClickType.RIGHT){
			if(nowLevel > 0){
				nowLevel--;
				setLevel();
				display();
			}
		}
		if(clickType == ClickType.MIDDLE){
			gui.setGuiChatAction(this);
			gui.close();
			player.sendTitle("" ,languageConfig.getString("Gui.EditItem.CD.Main.Message"), 1, 40 , 1);
			gui.setChat(true);
		}
	}

	public void execute(Player player, String value){
		if(NumberJudgment.isNumber(value)){
			nowLevel = StringConversion.getInt(0, value);
			setLevel();
			display();
		}
		gui.open(gui);
	}

	public void setLevel(){
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = com.daxton.fancyitmes.config.FileConfig.config_Map.get("item/"+itemType+".yml");

		if(nowLevel > 0){
			itemConfig.set(itemID+".Classes.Level."+levelName, nowLevel);
		}else {
			if(itemConfig.contains(itemID+".Classes.Level."+levelName)){
				itemConfig.set(itemID+".Classes.Level."+levelName, null);
			}
		}


	}

	public void display(){
		//等級
		ItemStack classItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Classes.LevelList");

		Map<String, String> classMap = new HashMap<>();
		classMap.put("{type}", levelName);
		GuiEditItem.replaceName(classItem, classMap);

		Map<String, String> classBooleanMap = new HashMap<>();
		String nowLevelString = String.valueOf(nowLevel);
		classBooleanMap.put("{level}", nowLevelString);
		GuiEditItem.replaceLore(classItem, classBooleanMap);

		GuiButton classButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(classItem).
			setGuiAction(this).
			build();

		gui.setButton(classButton, place);
	}

}

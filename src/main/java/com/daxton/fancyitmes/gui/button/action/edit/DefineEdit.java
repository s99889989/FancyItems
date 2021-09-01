package com.daxton.fancyitmes.gui.button.action.edit;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.action.EditAction;
import com.daxton.fancyitmes.gui.button.action.MainAction;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.List;
import java.util.UUID;

public class DefineEdit implements GuiAction {

	final GUI gui;
	final Player player;
	final EditAction editAction;
	final int place;

	public DefineEdit(Player player, GUI gui, EditAction editAction, int place){
		this.gui = gui;
		this.player = player;
		this.editAction = editAction;
		this.place = place;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			setAction();
		}
	}

	public void setAction(){
		UUID uuid = player.getUniqueId();
		//編輯物品用值
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		List<String> actionList = itemConfig.getStringList(itemID+".Action");

		String name = editAction.name;
		String action = editAction.actionKey;
		Boolean needTarget = Boolean.parseBoolean(editAction.needTarget);
		int count = Integer.parseInt(editAction.count);
		int countPeriod = Integer.parseInt(editAction.countPeriod);
		String mark = editAction.mark;
		boolean stop = Boolean.parseBoolean(editAction.stop);
		String trigger = editAction.trigger;

		String putString = name+":Action[";
		putString += "Action="+action;
		putString += ";NeedTarget="+needTarget;
		if(count > 1){
			putString += ";Count="+count;
			putString += ";CountPeriod="+countPeriod;
		}

		if(mark.equalsIgnoreCase("null")){
			putString += ";Mark="+mark;
		}

		putString += ";Stop="+stop;

		putString += "] "+trigger;

		actionList.set(place, putString);

		itemConfig.set(itemID+".Action", actionList);

		MainAction.mainAction(player, gui);


	}

}

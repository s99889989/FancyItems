package com.daxton.fancyitmes.gui.button.action.edit;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.button.GuiChatAction;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.gui.button.action.EditAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class CountEdit implements GuiAction, GuiChatAction {

	final GUI gui;
	final Player player;
	final EditAction editAction;
	final int place;

	public CountEdit(Player player, GUI gui, EditAction editAction, int place){
		this.gui = gui;
		this.player = player;
		this.editAction = editAction;
		this.place = place;
	}

	public void execute(Player player, String value){

		try {
			int v = Integer.parseInt(value);
			if(v <= 0){
				v = 1;
			}
			value = v+"";
		}catch (NumberFormatException exception){
			value = "1";
		}

		GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Count", "{count}", value)).
			setGuiAction(this).
			build();
		gui.setButton(nameButton, 4, 2);

		editAction.count = value;

		gui.open(gui);
	}



	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			gui.setGuiChatAction(this);
			gui.setChat(true);
			gui.close();
		}
	}

}

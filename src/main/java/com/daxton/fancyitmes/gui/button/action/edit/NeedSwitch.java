package com.daxton.fancyitmes.gui.button.action.edit;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.action.EditAction;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class NeedSwitch implements GuiAction {

	final GUI gui;
	final Player player;
	final EditAction editAction;

	public NeedSwitch(Player player, GUI gui, EditAction editAction){
		this.gui = gui;
		this.player = player;
		this.editAction = editAction;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){

			editAction.needTarget = !Boolean.parseBoolean(editAction.needTarget)+"";

			GuiButton unbreakableButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.NeedTarget", "{need}", editAction.needTarget)).
				setGuiAction(this).
				build();
			gui.setButton(unbreakableButton, 3, 5);


		}
	}

}

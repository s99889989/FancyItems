package com.daxton.fancyitmes.gui.button.action.edit;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.gui.button.action.EditAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class StopSwitch implements GuiAction {

	final GUI gui;
	final Player player;
	final EditAction editAction;

	public StopSwitch(Player player, GUI gui, EditAction editAction){
		this.gui = gui;
		this.player = player;
		this.editAction = editAction;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			boolean b = Boolean.parseBoolean(editAction.stop);
			editAction.stop = !b+"";

			GuiButton unbreakableButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Stop", "{stop}", editAction.stop)).
				setGuiAction(this).
				build();
			gui.setButton(unbreakableButton, 3, 7);


		}
	}

}

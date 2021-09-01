package com.daxton.fancyitmes.gui.button.attributes;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class OperationSwitch implements GuiAction {

	final GUI gui;
	final Player player;
	final EditAttr editAttr;
	int place;
	String[] operationArray = new String[]{"ADD_NUMBER", "ADD_SCALAR", "MULTIPLY_SCALAR_1"};

	public OperationSwitch(Player player, GUI gui, EditAttr editAttr){
		this.gui = gui;
		this.player = player;
		this.editAttr = editAttr;
		this.place = ArrayUtils.indexOf(operationArray, editAttr.operation);
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){

			place++;

			if(place >= 3){
				place = 0;
			}
			//FancyItems.fancyItems.getLogger().info("增加"+place);
			editAttr.operation = operationArray[place];
			//名稱
			GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Operation", "{operation}", editAttr.operation)).
				setGuiAction(this).
				build();
			gui.setButton(nameButton, 4, 4);

		}
		if(clickType == ClickType.RIGHT){
			//FancyItems.fancyItems.getLogger().info("減少");
			place--;
			if(place < 0){
				place = 2;
			}
			editAttr.name = operationArray[place];
			//名稱
			GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Operation", "{operation}", editAttr.operation)).
				setGuiAction(this).
				build();
			gui.setButton(nameButton, 4, 4);
		}
	}

}

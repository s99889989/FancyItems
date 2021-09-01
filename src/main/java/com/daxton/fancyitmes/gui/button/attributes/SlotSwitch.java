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

public class SlotSwitch implements GuiAction {

	final GUI gui;
	final Player player;
	final EditAttr editAttr;
	int place;
	String[] slotArray = new String[]{"HAND", "OFF_HAND", "FEET", "LEGS", "CHEST", "HEAD", "ALL"};

	public SlotSwitch(Player player, GUI gui, EditAttr editAttr){
		this.gui = gui;
		this.player = player;
		this.editAttr = editAttr;
		this.place = ArrayUtils.indexOf(slotArray, editAttr.body);
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){

			place++;

			if(place >= 7){
				place = 0;
			}
			//FancyItems.fancyItems.getLogger().info("增加"+place);
			editAttr.body = slotArray[place];
			//名稱
			GuiButton slotButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Slot", "{slot}", editAttr.body)).
				setGuiAction(this).
				build();
			gui.setButton(slotButton, 4, 3);

		}
		if(clickType == ClickType.RIGHT){
			//FancyItems.fancyItems.getLogger().info("減少");
			place--;
			if(place < 0){
				place = 6;
			}
			editAttr.body = slotArray[place];
			//名稱
			GuiButton slotButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Slot", "{slot}", editAttr.body)).
				setGuiAction(this).
				build();
			gui.setButton(slotButton, 4, 3);
		}
	}

}

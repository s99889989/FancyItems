package com.daxton.fancyitmes.gui.button.attributes;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.FancyItems;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class NameSwitch implements GuiAction {

	final GUI gui;
	final Player player;
	final EditAttr editAttr;
	int place;
	String[] nameArray = new String[]{"GENERIC_MAX_HEALTH", "GENERIC_KNOCKBACK_RESISTANCE", "GENERIC_MOVEMENT_SPEED", "GENERIC_ATTACK_DAMAGE", "GENERIC_ATTACK_SPEED", "GENERIC_ARMOR", "GENERIC_ARMOR_TOUGHNESS", "GENERIC_LUCK"};

	public NameSwitch(Player player, GUI gui, EditAttr editAttr){
		this.gui = gui;
		this.player = player;
		this.editAttr = editAttr;
		this.place = ArrayUtils.indexOf(nameArray, editAttr.name);
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){

			place++;

			if(place >= 8){
				place = 0;
			}
			//FancyItems.fancyItems.getLogger().info("增加"+place);
			editAttr.name = nameArray[place];
			//名稱
			GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Name", "{name}", editAttr.name)).
				setGuiAction(this).
				build();
			gui.setButton(nameButton, 4, 2);

		}
		if(clickType == ClickType.RIGHT){
			//FancyItems.fancyItems.getLogger().info("減少");
			place--;
			if(place < 0){
				place = 7;
			}
			editAttr.name = nameArray[place];
			//名稱
			GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Name", "{name}", editAttr.name)).
				setGuiAction(this).
				build();
			gui.setButton(nameButton, 4, 2);
		}
	}

}

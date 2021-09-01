package com.daxton.fancyitmes.gui.button.attributes.test;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.gui.button.attributes.AttrEditMain;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class SelectKey implements GuiAction {

	final GUI gui;
	final Player player;
	final AttrEditMain attrEdit;
	final String key;

	public SelectKey(Player player, GUI gui, AttrEditMain attrEdit, String key){
		this.gui = gui;
		this.player = player;
		this.attrEdit = attrEdit;
		this.key = key;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			attrEdit.key = key;

			//部位
			GuiButton healthButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Health", "{}", "")).
				setGuiAction(new SetValue(player, gui)).build();
			gui.setButton(healthButton, 4, 2);
			//加成類型
			GuiButton knock_BackButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Knock_Back", "{}", "")).
				setGuiAction(new SetValue(player, gui)).build();
			gui.setButton(knock_BackButton, 4, 3);
			//值
			GuiButton moveButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Move", "{}", "")).
				setGuiAction(new SetValue(player, gui)).build();
			gui.setButton(moveButton, 4, 4);

		}
	}
}

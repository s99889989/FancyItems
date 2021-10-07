package com.daxton.fancyitmes.gui.button.cd;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.button.GuiChatAction;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.gui.select.SelectItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class CDLeft implements GuiAction ,GuiChatAction {

	final GUI gui;
	final Player player;

	public CDLeft(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			SelectItem.addCD(player, "LeftClick", 1);
			left();
		}
		if(clickType == ClickType.RIGHT){
			SelectItem.addCD(player, "LeftClick", -1);
			left();
		}
		if(clickType == ClickType.MIDDLE){
			gui.setGuiChatAction(this);
			gui.close();
			player.sendTitle("" ,languageConfig.getString("Gui.EditItem.CD.Main.Message"), 1, 40 , 1);
			gui.setChat(true);
		}
	}

	public void execute(Player player, String value){
		SelectItem.setCD(player, "LeftClick", value);
		left();
		gui.open(gui);
	}

	public void left(){
		ItemStack cdLeftItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CD.Left");
		Map<String, String> cdLeftMap = new HashMap<>();
		cdLeftMap.put("{left}", SelectItem.getCD(player, "LeftClick"));
		GuiEditItem.replaceLore(cdLeftItem, cdLeftMap);
		GuiButton cdLeftButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(cdLeftItem).
			setGuiAction(this).
			build();
		gui.setButton(cdLeftButton, 2, 2);
	}

}

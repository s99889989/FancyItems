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

public class CDRight implements GuiAction  , GuiChatAction {

	final GUI gui;
	final Player player;

	public CDRight(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			SelectItem.addCD(player, "RightClick", 1);
			right();
		}
		if(clickType == ClickType.RIGHT){
			SelectItem.addCD(player, "RightClick", -1);
			right();
		}
		if(clickType == ClickType.MIDDLE){
			gui.setGuiChatAction(this);
			gui.close();
			player.sendTitle("" ,languageConfig.getString("Gui.EditItem.CD.Main.Message"), 1, 40 , 1);
			gui.setChat(true);
		}
	}

	public void execute(Player player, String value){
		SelectItem.setCD(player, "RightClick", value);
		right();
		gui.open(gui);
	}

	public void right(){
		ItemStack cdRightItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CD.Right");
		Map<String, String> cdRightMap = new HashMap<>();
		cdRightMap.put("{right}", SelectItem.getCD(player, "RightClick"));
		GuiEditItem.replaceLore(cdRightItem, cdRightMap);
		GuiButton cdRightButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(cdRightItem).
			setGuiAction(this).
			build();
		gui.setButton(cdRightButton, 2, 3);
	}

}

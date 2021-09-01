package com.daxton.fancyitmes.gui.button.attributes;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.List;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class EditAttr implements GuiAction {

	final Player player;
	final GUI gui;
	final int place;
	String name;
	String body;
	String operation;
	String amount;
	double add = 1;

	public EditAttr(Player player, GUI gui, int place, String name, String slot, String operation, String amount){
		this.player = player;
		this.gui = gui;
		this.place = place;
		this.name = name;
		this.body = slot;
		this.operation = operation;
		this.amount = amount;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){

			UUID uuid = player.getUniqueId();
			//編輯物品用值
			String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
			String itemType = editKey[0];
			String itemID = editKey[1];
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

			//名稱
			GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Name", "{name}", name)).
				setGuiAction(new NameSwitch(player, gui, this)).
				build();
			gui.setButton(nameButton, 4, 2);
			//部位
			GuiButton slotButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Slot", "{slot}", body)).
				setGuiAction(new SlotSwitch(player, gui, this)).
				build();
			gui.setButton(slotButton, 4, 3);
			//加成類型
			GuiButton operationButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Operation", "{operation}", operation))
				.setGuiAction(new OperationSwitch(player, gui, this)).
				build();
			gui.setButton(operationButton, 4, 4);
			//量
			GuiButton amountButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Amount", "{amount}", amount)).
				setGuiAction(new AmountSwitch(player, gui, this)).
				build();
			gui.setButton(amountButton, 4, 5);
			//確定
			GuiButton defineButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Define")).
				setGuiAction(new DefineAttr(player, gui, this, place)).
				build();
			gui.setButton(defineButton, 4, 6);

		}
		if(clickType == ClickType.RIGHT){
			UUID uuid = player.getUniqueId();
			//編輯物品用值
			String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
			String itemType = editKey[0];
			String itemID = editKey[1];
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

			List<String> attrList = itemConfig.getStringList(itemID+".Attributes");
			attrList.remove(place);

			itemConfig.set(itemID+".Attributes", attrList);

			AttrEditMain.attrList(player, gui);
		}
	}

}

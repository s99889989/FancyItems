package com.daxton.fancyitmes.gui.button.action;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancycore.other.taskaction.MapGetKey;
import com.daxton.fancycore.other.taskaction.StringToMap;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.action.edit.*;
import com.daxton.fancyitmes.gui.button.attributes.NameSwitch;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class EditAction implements GuiAction {

	final GUI gui;
	final Player player;
	final int place;
	public String name;
	public String actionKey;
	public String needTarget;
	public String count;
	public String countPeriod;
	public String mark;
	public String stop;
	public String trigger;


	public EditAction(Player player, GUI gui, int place){
		this.gui = gui;
		this.player = player;
		this.place = place;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID uuid = player.getUniqueId();
			//編輯物品用值
			String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
			String itemType = editKey[0];
			String itemID = editKey[1];
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

			List<String> actionList = itemConfig.getStringList(itemID+".Action");
			String actionString = actionList.get(place);
			String[] attr = actionString.split(":");
			if(attr.length == 2){
				Map<String, String> action_Map = StringToMap.toActionMap(attr[1]);
				MapGetKey actionMapHandle = new MapGetKey(action_Map, player, null);
				name = attr[0];
				actionKey = actionMapHandle.getString(new String[]{"a", "action"},"null");
				needTarget = actionMapHandle.getString(new String[]{"nt", "NeedTarget"},"false");
				count = actionMapHandle.getString(new String[]{"c", "Count"},"1");
				countPeriod = actionMapHandle.getString(new String[]{"cp", "CountPeriod"},"1");
				mark = actionMapHandle.getString(new String[]{"m", "Mark"},"null");
				stop = actionMapHandle.getString(new String[]{"s", "stop"},"false");
				trigger = actionMapHandle.getString(new String[]{"triggerkey"},"null");

				//顯示名稱
				GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Name", "{name}", name)).
					setGuiAction(new NameEdit(player, gui, this, place)).
					build();
				gui.setButton(nameButton, 3, 2);
				//動作
				GuiButton actionButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Action", "{action}", actionKey)).
					setGuiAction(new ActionEdit(player, gui, this, place)).
					build();
				gui.setButton(actionButton, 3, 3);
				//觸發
				GuiButton triggerButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Trigger", "{trigger}", trigger)).
					setGuiAction(new TriggerEdit(player, gui, this, place)).
					build();
				gui.setButton(triggerButton, 3, 4);
				//需要目標
				GuiButton needTargetButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.NeedTarget", "{need}", needTarget)).
					setGuiAction(new NeedSwitch(player, gui, this)).
					build();
				gui.setButton(needTargetButton, 3, 5);
				//標記
				GuiButton markButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Mark", "{mark}", mark)).
					setGuiAction(new MarkEdit(player, gui, this, place)).
					build();
				gui.setButton(markButton, 3, 6);
				//停止動作
				GuiButton stopButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Stop", "{stop}", stop)).
					setGuiAction(new StopSwitch(player, gui, this)).
					build();
				gui.setButton(stopButton, 3, 7);
				//執行次數
				GuiButton countButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Count", "{count}", count)).
					setGuiAction(new CountEdit(player, gui, this, place)).
					build();
				gui.setButton(countButton, 4, 2);
				//執行間隔
				GuiButton countPeriodButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.CountPeriod", "{period}", countPeriod)).
					setGuiAction(new PeriodEdit(player, gui, this, place)).
					build();
				gui.setButton(countPeriodButton, 4, 3);
				//確定修改
				GuiButton defineButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Define")).
					setGuiAction(new DefineEdit(player, gui, this, place)).
					build();
				gui.setButton(defineButton, 4, 4);

			}


		}
	}

}

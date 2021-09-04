package com.daxton.fancyitmes.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.gui.button.top.CloseMenu;
import com.daxton.fancyitmes.gui.button.top.LoadMenu;
import com.daxton.fancyitmes.gui.button.top.SaveMenu;
import com.daxton.fancyitmes.gui.button.top.ToMain;
import com.daxton.fancyitmes.gui.other.ItemTypeList;
import com.daxton.fancyitmes.manager.ManagerItems;

import org.bukkit.entity.Player;

import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class MainMenu {
    //打開物品編輯器
    public static void open(Player player){
        UUID uuid = player.getUniqueId();
        if(ManagerItems.gui_Map.get(uuid) == null){
            GUI gui = GUI.GUIBuilder.getInstance().setPlayer(player).setSize(54).setTitle(languageConfig.getString("Title")).build();
            ManagerItems.type_Page.put(uuid, 0);
            ManagerItems.player_Chat_Select.put(uuid, "");
            //主選單
            GuiButton mainButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.ToMain")).
                setGuiAction(new ToMain(player)).build();
            gui.setButton(mainButton, 1, 1);
            //儲存
            GuiButton saveButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.Save")).
                setGuiAction(new SaveMenu(player, gui)).build();
            gui.setButton(saveButton, 1, 3);
            //讀取
            GuiButton loadButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.Load")).
                setGuiAction(new LoadMenu(player, gui)).build();
            gui.setButton(loadButton, 1, 7);
            //關閉
            GuiButton closeButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.Close")).
                setGuiAction(new CloseMenu(gui)).build();
            gui.setButton(closeButton, 1, 9);
            //物品類別列表
            ItemTypeList.setItemType(player, gui);

            gui.setMove(false);
            ManagerItems.gui_Map.put(uuid, gui);
            gui.open(gui);
        }else {
            GUI gui = ManagerItems.gui_Map.get(uuid);
            gui.removeButton(1, 5);
            gui.clearButtonFrom(10, 54);
            //物品類別列表
            ItemTypeList.setItemType(player, gui);

            gui.open(gui);
        }

    }

}

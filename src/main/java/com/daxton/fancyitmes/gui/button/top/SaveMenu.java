package com.daxton.fancyitmes.gui.button.top;

import com.daxton.fancycore.api.config.ConfigSave;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class SaveMenu implements GuiAction {

    final Player player;
    final GUI gui;


    public SaveMenu(Player player, GUI gui){
        this.player = player;
        this.gui = gui;
    }
    //儲存修改設定
    public void execute(ClickType clickType, InventoryAction action, int slot){
        if(clickType == ClickType.LEFT){
            ConfigSave.filter(FancyItems.fancyItems, FileConfig.config_Map, "item/");
        }
    }

}

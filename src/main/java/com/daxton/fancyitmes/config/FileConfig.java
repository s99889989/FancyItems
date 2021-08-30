package com.daxton.fancyitmes.config;

import com.daxton.fancycore.api.config.ConfigCreate;
import com.daxton.fancycore.api.config.ConfigLoad;
import com.daxton.fancyitmes.FancyItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileConfig {
    //設定檔地圖
    public static Map<String, FileConfiguration> config_Map = new HashMap();
    //語言設定檔
    public static FileConfiguration languageConfig;

    public static void execute(){

        FancyItems fancyItems = FancyItems.fancyItems;

        //建立設定檔
        ConfigCreate.execute(fancyItems);

        File file = new File(FancyItems.fancyItems.getDataFolder(),"ItemMenu.yml");
        FileConfiguration itemMesnu = YamlConfiguration.loadConfiguration(file);
        if(itemMesnu.getConfigurationSection("Items.Type") != null){
            itemMesnu.getConfigurationSection("Items.Type").getKeys(false).forEach(s -> {
                File file2 = new File(FancyItems.fancyItems.getDataFolder(),"item/"+s+".yml");
                if(!file2.exists()){
                    try {
                        file2.createNewFile();
                    }catch (IOException exception){
                        //
                    }
                }
            });
        }

        //讀取設定檔
        config_Map = ConfigLoad.execute(FancyItems.fancyItems);

        //設置語言設定檔
        FileConfiguration resourcePackConfig = FileConfig.config_Map.get("config.yml");
        String nowLanguage = resourcePackConfig.getString("Language");
        languageConfig = FileConfig.config_Map.get("Language/"+nowLanguage+".yml");
        if(languageConfig == null){
            languageConfig = FileConfig.config_Map.get("Language/English.yml");
        }

        FileConfiguration itemMenuConfig = FileConfig.config_Map.get("ItemMenu.yml");
        itemMenuConfig.getConfigurationSection("ItemType").getKeys(false).forEach(itemType -> {
            File itemFile = new File(fancyItems.getDataFolder(),"item/"+itemType+".yml");
            if(!itemFile.exists()){
                try {
                    itemFile.createNewFile();
                }catch (IOException exception){
                    exception.printStackTrace();
                }
            }

        });


    }

    //重新讀取設定檔
    public static void reload(){

        FancyItems fancyItems = FancyItems.fancyItems;

        config_Map = ConfigLoad.execute(FancyItems.fancyItems);

        //設置語言設定檔
        FileConfiguration resourcePackConfig = FileConfig.config_Map.get("config.yml");
        String nowLanguage = resourcePackConfig.getString("Language");
        languageConfig = FileConfig.config_Map.get("Language/"+nowLanguage+".yml");
        if(languageConfig == null){
            languageConfig = FileConfig.config_Map.get("Language/English.yml");
        }

        FileConfiguration itemMenuConfig = FileConfig.config_Map.get("ItemMenu.yml");
        itemMenuConfig.getConfigurationSection("ItemType").getKeys(false).forEach(itemType -> {
            File itemFile = new File(fancyItems.getDataFolder(),"item/"+itemType+".yml");
            if(!itemFile.exists()){
                try {
                    itemFile.createNewFile();
                }catch (IOException exception){
                    exception.printStackTrace();
                }
            }

        });

    }

}

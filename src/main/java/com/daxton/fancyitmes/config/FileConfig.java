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

    public static Map<String, FileConfiguration> config_Map = new HashMap();

    public static void execute(){
        //建立設定檔
        ConfigCreate.execute(FancyItems.fancyItems);

        File file = new File(FancyItems.fancyItems.getDataFolder(),"ItemMenu.yml");
        FileConfiguration itemMesnu = YamlConfiguration.loadConfiguration(file);
        if(itemMesnu.getConfigurationSection("Items.Type") != null){
            itemMesnu.getConfigurationSection("Items.Type").getKeys(false).forEach(s -> {
                FancyItems.fancyItems.getLogger().info(s);
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
    }

}

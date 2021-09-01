package com.daxton.fancyitmes.task;

import com.daxton.fancycore.api.config.SearchConfigMap;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;

public class Start {

    //只在開服時執行的任務
    public static void execute(){
        SearchConfigMap.fileNameList(FileConfig.config_Map, "custom-value/", false).forEach(s -> {
            FileConfiguration config = FileConfig.config_Map.get("custom-value/"+s);
            config.getConfigurationSection("").getKeys(false).forEach(key->{
                String value = config.getString(key+".name");
                String base = config.getString(key+".base");
                if(base == null){
                    base = "0";
                }
                ManagerItems.custom_Value_Default.put(key, base);
                if(value == null){
                    value = "";
                }
                ManagerItems.custom_Value.put(key, value);

            });
        });
    }

}

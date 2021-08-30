package com.daxton.fancyitmes;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

import com.daxton.fancyitmes.config.FileConfig;
import org.bukkit.Bukkit;

public class DependPlugins {

    public static boolean depend(){

        FancyItems fancyItems = FancyItems.fancyItems;

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyCore") != null && Bukkit.getPluginManager().isPluginEnabled("FancyCore")){
            //設定檔
            FileConfig.execute();
            fancyItems.getLogger().info(languageConfig.getString("LogMessage.LoadFancyCore"));
        }else {
            fancyItems.getLogger().info("§4*** FancyCore is not installed or not enabled. ***");
            fancyItems.getLogger().info("§4*** FancyItems will be disabled. ***");
            return false;
        }

        return true;
    }

}

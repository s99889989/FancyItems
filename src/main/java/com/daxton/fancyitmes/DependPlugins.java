package com.daxton.fancyitmes;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DependPlugins {

    public static boolean depend(){

        FancyItems fancyItems = FancyItems.fancyItems;

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyCore") != null && Bukkit.getPluginManager().isPluginEnabled("FancyCore")){
            fancyItems.getLogger().info(ChatColor.GREEN+"Loaded FancyCore");
        }else {
            fancyItems.getLogger().severe("*** FancyCore is not installed or not enabled. ***");
            fancyItems.getLogger().severe("*** FancyItemsy will be disabled. ***");
            fancyItems.getLogger().severe("*** FancyCore未安裝或未啟用。 ***");
            fancyItems.getLogger().severe("*** FancyItems將被卸載。 ***");
            return false;
        }

        return true;
    }

}

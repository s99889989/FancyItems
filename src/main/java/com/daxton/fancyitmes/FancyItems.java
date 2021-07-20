package com.daxton.fancyitmes;

import com.daxton.fancyitmes.command.MainCommand;
import com.daxton.fancyitmes.command.TabCommand;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FancyItems extends JavaPlugin{

    public static FancyItems fancyItems;

    @Override
    public void onEnable() {
        fancyItems = this;
        //前置插件
        if(!DependPlugins.depend()){
            fancyItems.setEnabled(false);
            fancyItems.onDisable();
            return;
        }
        //設定檔
        FileConfig.execute();
        //指令
        Bukkit.getPluginCommand("fancyitems").setExecutor(new MainCommand());
        Bukkit.getPluginCommand("fancyitems").setTabCompleter(new TabCommand());
        //監聽
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), fancyItems);

    }

    @Override
    public void onDisable() {

    }

}

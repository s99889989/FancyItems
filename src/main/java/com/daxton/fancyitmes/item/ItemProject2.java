package com.daxton.fancyitmes.item;

import com.daxton.fancycore.api.item.CItem;
import com.daxton.fancycore.manager.OtherManager;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemProject2 {

    //設定物品自訂屬性
    public static void setCustomAttrs(CItem cItem, FileConfiguration itemConfig, String itemID){

        if(!itemConfig.contains(itemID+".CustomValue")){
            return;
        }

        Set<String> itemCustomAttrs = itemConfig.getConfigurationSection(itemID + ".CustomValue").getKeys(false);
        List<String> itemLore = new ArrayList<>();

        int i = 1;
        for(String key : itemCustomAttrs){

            String value = itemConfig.getString(itemID+".CustomValue."+key);
            String lowKey = key.toLowerCase();
            String showKey = OtherManager.custom_Value.get(lowKey);
            if(showKey != null && !showKey.isEmpty()){
                itemLore.add(showKey+" : "+value);
            }

            try {//Str
                cItem.setCustomAttrs("custom"+i+"/"+key, value);
            }catch (Exception exception){
               //
            }
            i++;
        }
        if(!itemLore.isEmpty()){
            cItem.setLore(itemLore, true);
        }

    }

    //設定物品的左鍵CD
    public static void setCoodDownLeftClick(CItem cItem, FileConfiguration itemConfig, String itemID){

        try {
            String coolDownString = itemConfig.getString(itemID+".CoolDown.LeftClick");
            if(coolDownString != null)
                cItem.setCoolDownLeftClick(coolDownString);
        }catch (Exception exception){
            //
        }
    }
    //設定物品的右鍵CD
    public static void setCoolDownRightClick(CItem cItem, FileConfiguration itemConfig, String itemID){

        try {
            String coolDownString = itemConfig.getString(itemID+".CoolDown.RightClick");
            if(coolDownString != null)
                cItem.setCoolDownRightClick(coolDownString);
        }catch (Exception exception){
            //
        }

    }

    public static void setItemFlags(CItem cItem, FileConfiguration itemConfig, String itemID){
        //設定物品隱藏無法破壞
        cItem.setItemFlags("HIDE_UNBREAKABLE", itemConfig.getBoolean(itemID+".HideItemFlags"));
        //設定物品隱藏藥水效果
        cItem.setItemFlags("HIDE_POTION_EFFECTS", itemConfig.getBoolean(itemID+".HideItemFlags"));
        //設定物品隱藏可放置的方塊
        cItem.setItemFlags("HIDE_PLACED_ON", itemConfig.getBoolean(itemID+".HideItemFlags"));
        //設定物品隱藏附魔
        cItem.setItemFlags("HIDE_ENCHANTS", itemConfig.getBoolean(itemID+".HideItemFlags"));
        //設定物品隱藏染料
        cItem.setItemFlags("HIDE_DYE", itemConfig.getBoolean(itemID+".HideItemFlags"));
        //設定物品隱藏可破壞的方塊
        cItem.setItemFlags("HIDE_DESTROYS", itemConfig.getBoolean(itemID+".HideItemFlags"));
        //設定物品隱藏屬性
        cItem.setItemFlags("HIDE_ATTRIBUTES", itemConfig.getBoolean(itemID+".HideItemFlags"));


//        cItem.setItemFlags("HIDE_UNBREAKABLE", itemConfig.getBoolean(itemID+".ItemFlags.HideUnbreakable"));
//        cItem.setItemFlags("HIDE_POTION_EFFECTS", itemConfig.getBoolean(itemID+".ItemFlags.HidePotionEffects"));
//        cItem.setItemFlags("HIDE_PLACED_ON", itemConfig.getBoolean(itemID+".ItemFlags.HidePlacedOn"));
//        cItem.setItemFlags("HIDE_ENCHANTS", itemConfig.getBoolean(itemID+".ItemFlags.HideEnchants"));
//        cItem.setItemFlags("HIDE_DYE", itemConfig.getBoolean(itemID+".ItemFlags.HideDye"));
//        cItem.setItemFlags("HIDE_DESTROYS", itemConfig.getBoolean(itemID+".ItemFlags.HideDestroys"));
//        cItem.setItemFlags("HIDE_ATTRIBUTES", itemConfig.getBoolean(itemID+".ItemFlags.HideAttributes"));
    }

    //設定物品原生屬性
    public static void setAttributes(CItem cItem, FileConfiguration itemConfig, String itemID){

        List<String> attrList = itemConfig.getStringList(itemID+".Attributes");
        attrList.forEach(s -> {
            String[] strings = s.split(":");
            if(strings.length == 4){
                String equipmentSlot = strings[0];
                String inherit = strings[1];
                String operation = strings[2];
                try {
                    double attrAmount = Double.parseDouble(strings[3]);
                    if(inherit != null && operation != null && attrAmount != 0 && equipmentSlot != null){
                        cItem.setAttributes(equipmentSlot, inherit, operation, attrAmount);
                    }
                }catch (Exception exception){
                    //
                }
            }
        });


    }
    //設定物品附魔
    public static void setEnchantments(CItem cItem, FileConfiguration itemConfig, String itemID){
        List<String> itemEnchantment = itemConfig.getStringList(itemID+".Enchantments");
        itemEnchantment.forEach(s -> {
            String[] strings = s.split(":");
            if(strings.length == 2){
                //FancyItems.fancyItems.getLogger().info("測試: "+strings[0]+" : "+strings[1]);
                try {
                    cItem.setEnchantments(strings[0], Integer.parseInt(strings[1]));
                }catch (NumberFormatException exception){
                    //
                }
            }
        });
    }




}

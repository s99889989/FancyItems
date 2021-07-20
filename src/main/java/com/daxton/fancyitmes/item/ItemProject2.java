package com.daxton.fancyitmes.item;



import com.daxton.fancycore.api.item.CItem;
import com.daxton.fancyitmes.config.FileConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ItemProject2 {

    //設定物品自訂屬性
    public static void setCustomAttrs(CItem cItem, FileConfiguration itemConfig, String itemID){
        List<String> itemCustomAttrs = itemConfig.getStringList(itemID+".CustomAttrs");

        List<String> itemLore = new ArrayList<>();

        for(String s : itemCustomAttrs){
            String[] strings = s.split(":");
            if(strings.length == 2){
                try {
                    if(FileConfig.config_Map.get("CustomAttributes.yml") != null){
                        FileConfiguration caConfig = FileConfig.config_Map.get("CustomAttributes.yml");
                        if(caConfig.getString("CustomAttributes."+strings[0].trim()) != null){
                            String ll = strings[0];
                            ll = ll.replace(strings[0].trim(), Objects.requireNonNull(caConfig.getString("CustomAttributes." + strings[0].trim())));
                            s = ll+":"+strings[1];
                        }
                    }
                }catch (Exception exception){
                    //
                }
                itemLore.add(s);

                String left = strings[0].trim();
                String right = strings[1].trim();
                try {
                    cItem.setCustomAttrs(left, right);
                }catch (Exception exception){
                    //
                }
            }
        }
        cItem.setLore(itemLore);
    }

    //設定物品的左鍵CD
    public static void setCoolDownLeftClick(CItem cItem, FileConfiguration itemConfig, String itemID){

        try {
            String coolDownString = itemConfig.getString(itemID+".CoolDown.LeftClick");
            if(coolDownString != null)
                cItem.setCoolDownLeftClick(Integer.parseInt(coolDownString));
        }catch (Exception exception){
            //
        }
    }
    //設定物品的右鍵CD
    public static void setCoolDownRightClick(CItem cItem, FileConfiguration itemConfig, String itemID){

        try {
            String coolDownString = itemConfig.getString(itemID+".CoolDown.RightClick");
            if(coolDownString != null)
                cItem.setCoolDownRightClick(Integer.parseInt(coolDownString));
        }catch (Exception exception){
            //
        }

    }

    public static void setItemFlags(CItem cItem, FileConfiguration itemConfig, String itemID){
        //設定物品隱藏無法破壞
        cItem.setItemFlags("HIDE_UNBREAKABLE", itemConfig.getBoolean(itemID+".ItemFlags.HideUnbreakable"));
        //設定物品隱藏藥水效果
        cItem.setItemFlags("HIDE_POTION_EFFECTS", itemConfig.getBoolean(itemID+".ItemFlags.HidePotionEffects"));
        //設定物品隱藏可放置的方塊
        cItem.setItemFlags("HIDE_PLACED_ON", itemConfig.getBoolean(itemID+".ItemFlags.HidePlacedOn"));
        //設定物品隱藏附魔
        cItem.setItemFlags("HIDE_ENCHANTS", itemConfig.getBoolean(itemID+".ItemFlags.HideEnchants"));
        //設定物品隱藏染料
        cItem.setItemFlags("HIDE_DYE", itemConfig.getBoolean(itemID+".ItemFlags.HideDye"));
        //設定物品隱藏可破壞的方塊
        cItem.setItemFlags("HIDE_DESTROYS", itemConfig.getBoolean(itemID+".ItemFlags.HideDestroys"));
        //設定物品隱藏屬性
        cItem.setItemFlags("HIDE_ATTRIBUTES", itemConfig.getBoolean(itemID+".ItemFlags.HideAttributes"));
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
                try {
                    cItem.setEnchantments(strings[0], Integer.parseInt(strings[1]));
                }catch (NumberFormatException exception){
                    //
                }
            }
        });
    }




}

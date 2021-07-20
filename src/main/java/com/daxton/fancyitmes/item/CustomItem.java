package com.daxton.fancyitmes.item;

import com.daxton.fancycore.api.item.CItem;
import com.daxton.fancyitmes.config.FileConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class CustomItem {

    public static ItemStack valueOf(String itemType, String itemID, int amount){
        ItemStack newItemStack = new ItemStack(Material.STONE, amount);
        FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
        if(itemConfig.contains(itemID+".Material")){
            //設定物品材質
            newItemStack = ItemProject.setMaterial(itemConfig, itemID, amount);

            //設定物品顯示名稱
            if(itemConfig.contains(itemID+".DisplayName")){
                ItemProject.setDisplayName(null, null, itemConfig, itemID, newItemStack);
            }
            //設定物品Lore
            if(itemConfig.contains(itemID+".Lore")){
                ItemProject.setLore(null, null, itemConfig, itemID, newItemStack);
            }
            //設定物品損壞Data
            if(itemConfig.contains(itemID+".Data")){
                ItemProject.setData(itemConfig, itemID, newItemStack);
            }
            //設定物品CustomModelData
            if(itemConfig.contains(itemID+".CustomModelData")){
                ItemProject.setCustomModelData(itemConfig, itemID, newItemStack);
            }
            //設定物品附魔
            if(itemConfig.contains(itemID+".Enchantments")){
                ItemProject.setEnchantments(itemConfig, itemID, newItemStack);
            }
            //設定物品原生屬性
            if(itemConfig.contains(itemID+".Attributes")){
                ItemProject.setAttributes(itemConfig, itemID, newItemStack);
            }
            //設定物品不會損壞
            if(itemConfig.contains(itemID+".Unbreakable")){
                ItemProject.setUnbreakable(itemConfig, itemID, newItemStack);
            }
            //設定物品隱藏屬性
            if(itemConfig.contains(itemID+".ItemFlags.HideAttributes")){
                ItemProject.setItemFlagsHideAttributes(itemConfig, itemID, newItemStack);
            }
            //設定物品隱藏可破壞的方塊
            if(itemConfig.contains(itemID+".ItemFlags.HideDestroys")){
                ItemProject.setItemFlagsHideDestroys(itemConfig, itemID, newItemStack);
            }
            //設定物品隱藏染料
            if(itemConfig.contains(itemID+".ItemFlags.HideDye")){
                ItemProject.setItemFlagsHideDye(itemConfig, itemID, newItemStack);
            }
            //設定物品隱藏附魔
            if(itemConfig.contains(itemID+".ItemFlags.HideEnchants")){
                ItemProject.setItemFlagsHideEnchants(itemConfig, itemID, newItemStack);
            }
            //設定物品隱藏可放置的方塊
            if(itemConfig.contains(itemID+".ItemFlags.HidePlacedOn")){
                ItemProject.setItemFlagsHidePlacedOn(itemConfig, itemID, newItemStack);
            }
            //設定物品隱藏藥水效果
            if(itemConfig.contains(itemID+".ItemFlags.HidePotionEffects")){
                ItemProject.setItemFlagsHidePotionEffects(itemConfig, itemID, newItemStack);
            }
            //設定物品隱藏無法破壞
            if(itemConfig.contains(itemID+".ItemFlags.HideUnbreakable")){
                ItemProject.setItemFlagsHideUnbreakable(itemConfig, itemID, newItemStack);
            }
            //設定物品的動作
            if(itemConfig.contains(itemID+".Action")){
                ItemProject.setAction(itemConfig, itemID, newItemStack);
            }
            //設定物品的頭值
            if(itemConfig.contains(itemID+".HeadValue")){
                ItemProject.setHeadValue(null, null, itemConfig, itemID, newItemStack);
            }
            //設定物品的右鍵CD
            if(itemConfig.contains(itemID+".CoolDown.RightClick")){
                ItemProject.setCoolDownRightClick(itemConfig, itemID, newItemStack);
            }
            //設定物品的左鍵CD
            if(itemConfig.contains(itemID+".CoolDown.LeftClick")){
                ItemProject.setCoolDownLeftClick(itemConfig, itemID, newItemStack);
            }
            //設定物品禁止攻擊
            if(itemConfig.contains(itemID+".DisableAttack")){
                ItemProject.setDisableAttack(itemConfig, itemID, newItemStack);
            }
            //設定物品的自訂屬性
            if(itemConfig.contains(itemID+".CustomAttrs")){
                ItemProject.setCustomAttrs(itemConfig, itemID, newItemStack);
            }
            //設置物品ID
            ItemProject.setID(itemConfig, itemType, itemID, newItemStack);

        }
        return newItemStack;
    }

    public static ItemStack valueOf2(String itemType, String itemID, int amount){
        if(FileConfig.config_Map.get("item/"+itemType+".yml") == null){
            return new ItemStack(Material.STONE, amount);
        }

        FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
        //設定物品材質
        CItem cItem = new CItem(itemConfig.getString(itemID+".Material"));
        //設定物品堆疊數量
        cItem.setAmount(amount);
        //設定物品顯示名稱
        cItem.setDisplayName(itemConfig.getString(itemID+".DisplayName"));
        //設定物品Lore
        cItem.setLore(itemConfig.getStringList(itemID+".Lore"));
        //設定物品損壞Data
        cItem.setData(itemConfig.getInt(itemID+".Data"));
        //設定物品CustomModelData
        cItem.setCustomModelData(itemConfig.getInt(itemID+".CustomModelData"));
        //設定物品附魔
        ItemProject2.setEnchantments(cItem, itemConfig, itemID);
        //設定物品原生屬性
        ItemProject2.setAttributes(cItem, itemConfig, itemID);
        //設定物品不會損壞
        cItem.setUnbreakable(itemConfig.getBoolean(itemID+".Unbreakable"));
        //設定物品Flags
        ItemProject2.setItemFlags(cItem, itemConfig, itemID);
        //設定物品的動作
        cItem.setAction(itemConfig.getStringList(itemID+".Action"));
        //設定物的頭值
        cItem.setHeadValue(itemConfig.getString(itemID+".HeadValue"));
        //設定物品的右鍵CD
        ItemProject2.setCoolDownRightClick(cItem, itemConfig, itemID);
        //設定物品的左鍵CD
        ItemProject2.setCoolDownLeftClick(cItem, itemConfig, itemID);
        //設定物品禁止攻擊
        cItem.setDisableAttack(itemConfig.getBoolean(itemID+".DisableAttack"));
        //設定物品自訂屬性
        ItemProject2.setCustomAttrs(cItem, itemConfig, itemID);
        //設定物品的ID
        cItem.setID(itemType+"."+itemID);

        return cItem.getItemStack();
    }

}

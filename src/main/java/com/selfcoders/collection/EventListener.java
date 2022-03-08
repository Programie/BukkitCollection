package com.selfcoders.collection;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getItemInMainHand();
        if (itemStack.getType().getMaxDurability() > 0) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        BlockState blockData = block.getState();
        if (!(blockData instanceof Container)) {
            return;
        }

        Container container = (Container) blockData;

        Inventory inventory = container.getInventory();

        ItemStack foundStack = null;
        for (ItemStack stack : inventory.getContents()) {
            if (stack != null) {
                foundStack = stack;
                break;
            }
        }

        if (foundStack == null || foundStack.getType() == Material.AIR || foundStack.getAmount() == 0) {
            return;
        }

        int amount;
        if (player.isSneaking()) {
            amount = foundStack.getAmount();
        } else {
            amount = 1;
        }

        ItemStack moveStack = foundStack.clone();
        moveStack.setAmount(amount);

        moveStackToInventory(moveStack, inventory, playerInventory);
    }

    static void moveStackToInventory(ItemStack itemStack, Inventory sourceInventory, Inventory targetInventory) {
        int movedItems = 0;

        ItemStack removeStack = itemStack.clone();

        int amount = itemStack.getAmount();
        int remainingAmount = 0;

        Map<Integer, ItemStack> remainingItems = targetInventory.addItem(itemStack);

        if (!remainingItems.isEmpty()) {
            itemStack = remainingItems.get(0);
            remainingAmount = itemStack.getAmount();
        }

        movedItems += (amount - remainingAmount);

        if (movedItems > 0) {
            removeStack.setAmount(movedItems);
            sourceInventory.removeItem(removeStack);
        }
    }
}
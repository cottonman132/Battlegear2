package mods.battlegear2.items;

import mods.battlegear2.api.PlayerEventChild;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import cpw.mods.fml.relauncher.Side;

public class OneHandedWeapon extends ItemWeapon {

    public OneHandedWeapon(ToolMaterial material, String named) {
        super(material, named);
    }

    @Override
    public boolean allowOffhand(ItemStack mainhand, ItemStack offhand) {
        return true;
    }

    @Override
    public boolean isOffhandHandDual(ItemStack off) {
        return true;
    }

    @Override
    public boolean sheatheOnBack(ItemStack item) {
        return false;
    }

    @Override
    public boolean offhandAttackEntity(PlayerEventChild.OffhandAttackEvent event, ItemStack mainhandItem,
            ItemStack offhandItem) {
        return true;
    }

    @Override
    public boolean offhandClickAir(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem) {
        return true;
    }

    @Override
    public boolean offhandClickBlock(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem) {
        return true;
    }

    @Override
    public void performPassiveEffects(Side effectiveSide, ItemStack mainhandItem, ItemStack offhandItem) {}

    @Override
    public int getItemEnchantability() {
        return this.getMaterial().getEnchantability();
    }
}

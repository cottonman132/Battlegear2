package mods.battlegear2.api.core;

import mods.battlegear2.api.shield.IShield;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Interface added to {@link EntityPlayer} to support offhand management
 * 
 * @author GotoLink
 */
public interface IBattlePlayer {

    /**
     * A copied animation for the offhand, similar to {@link EntityPlayer#swingItem()}
     */
    public void swingOffhandItem();

    /**
     * The partial render progress for the offhand swing animation
     */
    public float getOffhandSwingProgress(float frame);

    /**
     * Hotswap the {@link EntityPlayer} current item to offhand, behaves like
     * {@link EntityPlayer#attackTargetEntityWithCurrentItem(Entity)}
     * 
     * @param target to attack
     */
    public void attackTargetEntityWithCurrentOffhandItem(Entity target);

    /**
     * Checks {@link IBattlegearInventoryPlayer#isInBattleMode()}, to see if current item is offset in the battle slots
     * range
     * 
     * @return true if player has pressed the bound key to activate dual-wielding, resulting in current item offset
     */
    public boolean isInBattleMode();

    /**
     * Helper for {@link IShield} usage
     * 
     * @return true if a {@link IShield} is being used in offhand
     */
    public boolean isBlockingWithShield();

    /**
     * Helper for {@link IShield} usage, sets the flag according to argument if {@link IShield} is being held in offhand
     * 
     * @param block new value for the shield block flag
     */
    public void setBlockingWithShield(boolean block);

    /**
     * Getter for the special timer field
     * 
     * @return the field value
     */
    public int getSpecialActionTimer();

    /**
     * Setter for the special timer field
     * 
     * @param time new value to set
     */
    public void setSpecialActionTimer(int time);
}

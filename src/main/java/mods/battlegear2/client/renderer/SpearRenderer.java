package mods.battlegear2.client.renderer;

import mods.battlegear2.client.utils.BattlegearRenderHelper;
import mods.battlegear2.items.ItemSpear;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

/**
 * User: nerd-boy Date: 25/06/13 Time: 5:39 PM TODO: Add discription
 */
public class SpearRenderer implements IItemRenderer {

    private RenderItem itemRenderer;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return item != null && item.getItem() instanceof ItemSpear
                && (type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED
                        || type == ItemRenderType.EQUIPPED_FIRST_PERSON);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        if (itemRenderer == null) {
            itemRenderer = new RenderItem();
        }
        GL11.glPushMatrix();

        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            IIcon icon = item.getIconIndex();
            if (type == ItemRenderType.EQUIPPED) {
                GL11.glTranslatef(-0.5F, -0.5F, 0);
                GL11.glScalef(2, 2, 1);
                icon = ((ItemSpear) item.getItem()).bigIcon;
            }
            Tessellator tessellator = Tessellator.instance;
            ItemRenderer.renderItemIn2D(
                    tessellator,
                    icon.getMaxU(),
                    icon.getMinV(),
                    icon.getMinU(),
                    icon.getMaxV(),
                    icon.getIconWidth(),
                    icon.getIconHeight(),
                    1F / 16F);
            if (item.hasEffect(0)) {
                BattlegearRenderHelper.renderEnchantmentEffects(tessellator);
            }

        } else if (type == ItemRenderType.INVENTORY) {

            GL11.glColor4f(1F, 1F, 1F, 1F);
            // GL11.glRotatef(90, 0, 0, 1);
            // MOJANG derp fixes:
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            // GL11.glEnable(GL11.GL_BLEND);
            itemRenderer.renderIcon(0, 0, item.getIconIndex(), 16, 16);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            if (item.hasEffect(0)) itemRenderer.renderEffect(Minecraft.getMinecraft().getTextureManager(), 0, 0);
        }
        GL11.glPopMatrix();
    }
}

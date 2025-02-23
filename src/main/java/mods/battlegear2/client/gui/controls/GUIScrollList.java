package mods.battlegear2.client.gui.controls;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GUIScrollList {

    protected final int listWidth;
    protected final int top;
    protected final int bottom;
    private final int right;
    protected final int left;
    protected final int slotHeight;
    private int scrollUpActionId;
    private int scrollDownActionId;
    protected int mouseX;
    protected int mouseY;
    private float initialMouseClickY = -2.0F;
    private float scrollFactor;
    private float scrollDistance;
    private int selectedIndex = -1;
    private long lastClickTime = 0L;
    private boolean field_25123_p = true;
    private boolean field_27262_q;
    private int field_27261_r;
    public boolean drawList = true;

    public GUIScrollList(int width, int top, int bottom, int left, int entryHeight) {
        this.listWidth = width;
        this.top = top;
        this.bottom = bottom;
        this.slotHeight = entryHeight;
        this.left = left;
        this.right = width + this.left;
    }

    public void func_27258_a(boolean p_27258_1_) {
        this.field_25123_p = p_27258_1_;
    }

    protected void func_27259_a(boolean p_27259_1_, int p_27259_2_) {
        this.field_27262_q = p_27259_1_;
        this.field_27261_r = p_27259_2_;

        if (!p_27259_1_) {
            this.field_27261_r = 0;
        }
    }

    protected abstract int getSize();

    protected abstract void elementClicked(int index, boolean doubleClick);

    protected abstract boolean isSelected(int index);

    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.field_27261_r;
    }

    protected abstract void drawBackground();

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5);

    protected void func_27260_a(int p_27260_1_, int p_27260_2_, Tessellator p_27260_3_) {}

    protected void func_27257_b(int p_27257_1_, int p_27257_2_) {}

    public int func_27256_c(int p_27256_1_, int p_27256_2_) {
        int var3 = this.left + 1;
        int var4 = this.left + this.listWidth - 7;
        int var5 = p_27256_2_ - this.top - this.field_27261_r + (int) this.scrollDistance - 4;
        int var6 = var5 / this.slotHeight;
        return p_27256_1_ >= var3 && p_27256_1_ <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize() ? var6 : -1;
    }

    public void registerScrollButtons(List p_22240_1_, int p_22240_2_, int p_22240_3_) {
        this.scrollUpActionId = p_22240_2_;
        this.scrollDownActionId = p_22240_3_;
    }

    private void applyScrollLimits() {
        int var1 = this.getContentHeight() - (this.bottom - this.top - 4);

        if (var1 < 0) {
            var1 /= 2;
        }

        if (this.scrollDistance < 0.0F) {
            this.scrollDistance = 0.0F;
        }

        if (this.scrollDistance > (float) var1) {
            this.scrollDistance = (float) var1;
        }
    }

    public void actionPerformed(GuiButton button) {
        if (button.enabled && drawList) {
            if (button.id == this.scrollUpActionId) {
                this.scrollDistance -= (float) (this.slotHeight * 2 / 3);
                this.initialMouseClickY = -2.0F;
                this.applyScrollLimits();
            } else if (button.id == this.scrollDownActionId) {
                this.scrollDistance += (float) (this.slotHeight * 2 / 3);
                this.initialMouseClickY = -2.0F;
                this.applyScrollLimits();
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float p_22243_3_) {
        if (drawList) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.drawBackground();
            int listLength = this.getSize();
            int scrollBarXStart = this.left + this.listWidth - 6;
            int scrollBarXEnd = scrollBarXStart + 6;
            int boxLeft = this.left;
            int boxRight = scrollBarXStart - 1;
            int var10;
            int var11;
            int var13;
            int var19;

            if (Mouse.isButtonDown(0)) {
                if (this.initialMouseClickY == -1.0F) {
                    boolean var7 = true;

                    if (mouseY >= this.top && mouseY <= this.bottom) {
                        var10 = mouseY - this.top - this.field_27261_r + (int) this.scrollDistance - 4;
                        var11 = var10 / this.slotHeight;

                        if (mouseX >= boxLeft && mouseX <= boxRight && var11 >= 0 && var10 >= 0 && var11 < listLength) {
                            boolean var12 = var11 == this.selectedIndex
                                    && System.currentTimeMillis() - this.lastClickTime < 250L;
                            this.elementClicked(var11, var12);
                            this.selectedIndex = var11;
                            this.lastClickTime = System.currentTimeMillis();
                        } else if (mouseX >= boxLeft && mouseX <= boxRight && var10 < 0) {
                            var7 = false;
                        }

                        if (mouseX >= scrollBarXStart && mouseX <= scrollBarXEnd) {
                            this.scrollFactor = -1.0F;
                            var19 = this.getContentHeight() - (this.bottom - this.top - 4);

                            if (var19 < 1) {
                                var19 = 1;
                            }

                            var13 = (int) ((float) ((this.bottom - this.top) * (this.bottom - this.top))
                                    / (float) this.getContentHeight());

                            if (var13 < 32) {
                                var13 = 32;
                            }

                            if (var13 > this.bottom - this.top - 8) {
                                var13 = this.bottom - this.top - 8;
                            }

                            this.scrollFactor /= (float) (this.bottom - this.top - var13) / (float) var19;
                        } else {
                            this.scrollFactor = 1.0F;
                        }

                        if (var7) {
                            this.initialMouseClickY = (float) mouseY;
                        } else {
                            this.initialMouseClickY = -2.0F;
                        }
                    } else {
                        this.initialMouseClickY = -2.0F;
                    }
                } else if (this.initialMouseClickY >= 0.0F) {
                    this.scrollDistance -= ((float) mouseY - this.initialMouseClickY) * this.scrollFactor;
                    this.initialMouseClickY = (float) mouseY;
                }
            } else {
                while (Mouse.next()) {
                    int var16 = Mouse.getEventDWheel();

                    if (var16 != 0) {
                        if (var16 > 0) {
                            var16 = -1;
                        } else if (var16 < 0) {
                            var16 = 1;
                        }

                        this.scrollDistance += (float) (var16 * this.slotHeight / 2);
                    }
                }

                this.initialMouseClickY = -1.0F;
            }

            this.applyScrollLimits();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_FOG);
            Tessellator var18 = Tessellator.instance;

            var10 = this.top + 4 - (int) this.scrollDistance;

            if (this.field_27262_q) {
                this.func_27260_a(boxRight, var10, var18);
            }

            int var14;

            for (var11 = 0; var11 < listLength; ++var11) {
                var19 = var10 + var11 * this.slotHeight + this.field_27261_r;
                var13 = this.slotHeight - 4;

                if (var19 <= this.bottom && var19 + var13 >= this.top) {
                    if (this.field_25123_p && this.isSelected(var11)) {
                        var14 = boxLeft;
                        int var15 = boxRight;
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                        var18.startDrawingQuads();
                        var18.setColorOpaque_I(8421504);
                        var18.addVertexWithUV((double) var14, (double) (var19 + var13 + 2), 0.0D, 0.0D, 1.0D);
                        var18.addVertexWithUV((double) var15, (double) (var19 + var13 + 2), 0.0D, 1.0D, 1.0D);
                        var18.addVertexWithUV((double) var15, (double) (var19 - 2), 0.0D, 1.0D, 0.0D);
                        var18.addVertexWithUV((double) var14, (double) (var19 - 2), 0.0D, 0.0D, 0.0D);
                        var18.setColorOpaque_I(0);
                        var18.addVertexWithUV((double) (var14 + 1), (double) (var19 + var13 + 1), 0.0D, 0.0D, 1.0D);
                        var18.addVertexWithUV((double) (var15 - 1), (double) (var19 + var13 + 1), 0.0D, 1.0D, 1.0D);
                        var18.addVertexWithUV((double) (var15 - 1), (double) (var19 - 1), 0.0D, 1.0D, 0.0D);
                        var18.addVertexWithUV((double) (var14 + 1), (double) (var19 - 1), 0.0D, 0.0D, 0.0D);
                        var18.draw();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                    }

                    this.drawSlot(var11, boxRight, var19, var13, var18);
                }
            }

            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            var19 = this.getContentHeight() - (this.bottom - this.top - 4);

            if (var19 > 0) {
                var13 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();

                if (var13 < 32) {
                    var13 = 32;
                }

                if (var13 > this.bottom - this.top - 8) {
                    var13 = this.bottom - this.top - 8;
                }

                var14 = (int) this.scrollDistance * (this.bottom - this.top - var13) / var19 + this.top;

                if (var14 < this.top) {
                    var14 = this.top;
                }

                var18.startDrawingQuads();
                var18.setColorRGBA_I(0, 255);
                var18.addVertexWithUV((double) scrollBarXStart, (double) this.bottom, 0.0D, 0.0D, 1.0D);
                var18.addVertexWithUV((double) scrollBarXEnd, (double) this.bottom, 0.0D, 1.0D, 1.0D);
                var18.addVertexWithUV((double) scrollBarXEnd, (double) this.top, 0.0D, 1.0D, 0.0D);
                var18.addVertexWithUV((double) scrollBarXStart, (double) this.top, 0.0D, 0.0D, 0.0D);
                var18.draw();
                var18.startDrawingQuads();
                var18.setColorRGBA_I(8421504, 255);
                var18.addVertexWithUV((double) scrollBarXStart, (double) (var14 + var13), 0.0D, 0.0D, 1.0D);
                var18.addVertexWithUV((double) scrollBarXEnd, (double) (var14 + var13), 0.0D, 1.0D, 1.0D);
                var18.addVertexWithUV((double) scrollBarXEnd, (double) var14, 0.0D, 1.0D, 0.0D);
                var18.addVertexWithUV((double) scrollBarXStart, (double) var14, 0.0D, 0.0D, 0.0D);
                var18.draw();
                var18.startDrawingQuads();
                var18.setColorRGBA_I(12632256, 255);
                var18.addVertexWithUV((double) scrollBarXStart, (double) (var14 + var13 - 1), 0.0D, 0.0D, 1.0D);
                var18.addVertexWithUV((double) (scrollBarXEnd - 1), (double) (var14 + var13 - 1), 0.0D, 1.0D, 1.0D);
                var18.addVertexWithUV((double) (scrollBarXEnd - 1), (double) var14, 0.0D, 1.0D, 0.0D);
                var18.addVertexWithUV((double) scrollBarXStart, (double) var14, 0.0D, 0.0D, 0.0D);
                var18.draw();
            }

            this.func_27257_b(mouseX, mouseY);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(Tessellator tessellator, int x, int y, int width, int height, int zLevel) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) zLevel, 0F, 1F);
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) zLevel, 1F, 1F);
        tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) zLevel, 1F, 0F);
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel, 0F, 0F);
        tessellator.draw();
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color. Args: x1, y1, x2, y2, color
     */
    public static void drawRect(int x1, int y1, int x2, int y2, int col) {
        int j1;

        if (x1 < x2) {
            j1 = x1;
            x1 = x2;
            x2 = j1;
        }

        if (y1 < y2) {
            j1 = y1;
            y1 = y2;
            y2 = j1;
        }

        float f = (float) (col >> 24 & 255) / 255.0F;
        float f1 = (float) (col >> 16 & 255) / 255.0F;
        float f2 = (float) (col >> 8 & 255) / 255.0F;
        float f3 = (float) (col & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(f1, f2, f3, f);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double) x1, (double) y2, 0.0D);
        tessellator.addVertex((double) x2, (double) y2, 0.0D);
        tessellator.addVertex((double) x2, (double) y1, 0.0D);
        tessellator.addVertex((double) x1, (double) y1, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}

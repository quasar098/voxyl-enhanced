package com.quasar.voxylenhanced.statsviewer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;

public class VoxylLevelhead {
    public static HashMap<Integer, Color> getColorMap() {
        HashMap<Integer, Color> cmap = new HashMap<>();
        cmap.put(0, new Color(0.66f, 0.66f, 0.66f)); // 0-99  NONE
        cmap.put(1, new Color(1f, 1f, 1f)); // 100-199  IRON
        cmap.put(2, new Color(1f, 0.66f, 0f)); // 200-299  GOLD
        cmap.put(3, new Color(0.33f, 1f, 1f)); // 300-399  DIAMOND
        cmap.put(4, new Color(0f, 0.66f, 0f)); // 400-499  EMERALD
        cmap.put(5, new Color(0f, 0.66f, 0.66f)); // 500-599  SAPPHIRE
        cmap.put(6, new Color(0.66f, 0f, 0f)); // 600-699  RUBY
        cmap.put(7, new Color(1f, 0.33f, 1f)); // 700-799  CRYSTAL
        cmap.put(8, new Color(0.33f, 0.33f, 1f)); // 800-899  OPAL
        cmap.put(9, new Color(0.66f, 0f, 0.66f)); // 800-899  AMETHYST
        return cmap;
    }

    @SuppressWarnings("unused")
    public static void renderTag(EntityLivingBase entityPlayer, double x, double y, double z, int star) {
        if (star == -1) {
            return;
        }
        // stole this code from https://github.com/Sk1erLLC/TNTTime/blob/master/src/main/java/club/sk1er/mods/tnttimer/TNTTime.java
        final int fuseTimer = 28;
        double distance = entityPlayer.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer);

        String starText = "["+ star + "✫]";
        if (entityPlayer.getName().isEmpty()) {
            return;
        }
        if (entityPlayer.hasCustomName() && entityPlayer.getCustomNameTag().isEmpty()) {
            return;
        }
        if (entityPlayer.isSneaking()) {
            return;
        }
        if (entityPlayer.isInvisible()) {
            return;
        }
        if (entityPlayer.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
            return;
        }
        if (distance > 2048D) {
            return;
        }
        float number = (fuseTimer) / 20F;
        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
        GlStateManager.pushMatrix();
        float offset = 0f;
        if (entityPlayer.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) < 10D) {
            if (((EntityPlayer) entityPlayer).getWorldScoreboard().getObjectiveInDisplaySlot(2) != null) {
                offset = 0.25f;
            }
        }
        GlStateManager.translate((float) x + 0.0F, (float) y + entityPlayer.height + 0.5F + offset, (float) z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);

        int xMultiplier = 1; // nametag x rotations should flip in front-facing 3rd person

        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
            xMultiplier = -1;
        }

        float scale = 0.02666667f;
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX * xMultiplier, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int stringWidth = fontrenderer.getStringWidth(starText) >> 1;
        Color color = getColorMap().getOrDefault((star/100)%10, getColorMap().get(0));
        // https://hypixel.net/attachments/1602743904489-png.2046478/
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(-stringWidth - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(-stringWidth - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(stringWidth + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(stringWidth + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontrenderer.drawString(starText, -fontrenderer.getStringWidth(starText) >> 1, 0, color.getRGB());
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}

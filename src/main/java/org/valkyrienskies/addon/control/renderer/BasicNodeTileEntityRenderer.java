package org.valkyrienskies.addon.control.renderer;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import org.valkyrienskies.addon.control.nodenetwork.VSNode_TileEntity;
import org.valkyrienskies.addon.control.tileentity.TileEntityNetworkRelay;

public class BasicNodeTileEntityRenderer extends TileEntitySpecialRenderer<TileEntityNetworkRelay> {

    @Override
    public void render(TileEntityNetworkRelay te, double x, double y, double z, float partialTick,
        int destroyStage, float alpha) {
        GlStateManager.disableBlend();
        VSNode_TileEntity tileNode = te.getNode();
        if (tileNode != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(.5D, -1D, .5D);
            // GL11.glTranslated(0, y, 0);

            for (BlockPos otherPos : tileNode.getLinkedNodesPos()) {
                TileEntity otherTile = getWorld().getTileEntity(otherPos);
                if (otherTile instanceof TileEntityNetworkRelay) {
                    // Don't render the same connection twice.
                    if (otherTile.getPos()
                        .compareTo(te.getPos()) < 0) {
                        continue;
                    }
                }
                // render wire between these two blockPos
                GL11.glPushMatrix();
                // GlStateManager.resetColor();

                double startX = te.getPos()
                    .getX();
                double startY = te.getPos()
                    .getY();
                double startZ = te.getPos()
                    .getZ();

                double endX = (startX * 2) - otherPos.getX();
                double endY = (startY * 2) - otherPos.getY() - 1.5;
                double endZ = (startZ * 2) - otherPos.getZ();

                renderWire(x, y, z, startX, startY, startZ, endX, endY, endZ);

                // GL11.glEnd();
                GL11.glPopMatrix();
            }

            GlStateManager.resetColor();
            // bindTexture(new ResourceLocation("textures/entity/lead_knot.png"));
            // GlStateManager.scale(-1.0F, -1.0F, 1.0F);

            // ModelLeashKnot knotRenderer = new ModelLeashKnot();
            // knotRenderer.knotRenderer.render(0.0625F);
            // BlockPos originPos = te.getPos();

            GL11.glPopMatrix();
        }
    }

    private void renderWire(double x, double y, double z, double entity1x, double entity1y,
        double entity1z,
        double entity2x, double entity2y, double entity2z) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        float wireR = .6f;
        float wireG = .25f;
        float wireB = .02f;
        float wireAlpha = 1.0f;
        // Vec3d vec = new Vec3d(x,y,z);
        // if (vec.lengthSquared() < .01D) {
        // System.out.println("REE");
        // }
        // y = .5D;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        double d0 = 0;// this.interpolateValue(entity.prevRotationYaw, entity.rotationYaw,
        // partialTicks * 0.5F) * 0.01745329238474369D;
        double d1 = 0;// this.interpolateValue(entity.prevRotationPitch, entity.rotationPitch,
        // partialTicks * 0.5F) * 0.01745329238474369D;
        double d2 = Math.cos(d0);
        double d3 = Math.sin(d0);
        double d4 = Math.sin(d1);

        // if (entity instanceof EntityHanging)
        // {
        d4 = -1.0D;
        // }

        double fakeYaw = 0;// Math.PI / 6D;

        double d6 = entity1x;
        double d7 = entity1y;
        double d8 = entity1z;
        double d9 = fakeYaw;

        d2 = 0;// fakeWidth;;
        d3 = 0;// fakeWidth;
        double d10 = entity2x + d2;
        double d11 = entity2y;
        double d12 = entity2z + d3;
        x = x + d2;
        z = z + d3;
        double d13 = ((float) (d6 - d10));
        double d14 = ((float) (d7 - d11));
        double d15 = ((float) (d8 - d12));
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        int i = 24;
        double d16 = 0.025D;
        bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);

        for (int j = 0; j <= 24; ++j) {
            float f3 = j / 24.0F;
            bufferbuilder.pos(x + d13 * f3 + 0.0D,
                y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F),
                z + d15 * f3).color(wireR, wireG, wireB, wireAlpha).endVertex();
            bufferbuilder
                .pos(x + d13 * f3 + 0.025D,
                    y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F) + 0.025D,
                    z + d15 * f3)
                .color(wireR, wireG, wireB, wireAlpha).endVertex();
        }

        tessellator.draw();
        bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);

        for (int k = 0; k <= 24; ++k) {
            float f4 = 204F / 255F;// .282F;//0.5F;
            float f5 = 122F / 255F;// .176F;//0.4F;
            float f6 = 0;// 0.078F;//0.3F;

            // f4 *= 2.2F;
            // f5 *= 2.2F;
            // f6 *= 2.2F;

            if (k % 2 == 0) {
                f4 *= 0.7F;
                f5 *= 0.7F;
                f6 *= 0.7F;
            }

            float f7 = k / 24.0F;
            bufferbuilder.pos(x + d13 * f7 + 0.0D,
                y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F) + 0.025D,
                z + d15 * f7)
                .color(wireR, wireG, wireB, wireAlpha).endVertex();
            bufferbuilder.pos(x + d13 * f7 + 0.025D,
                y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F),
                z + d15 * f7 + 0.025D).color(wireR, wireG, wireB, wireAlpha).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

    private double interpolateValue(double start, double end, double pct) {
        return start + (end - start) * pct;
    }

}

package tekcays_addon.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

public class TKCYACoverBehavior extends CoverBehavior {
    public TKCYACoverBehavior(ICoverable coverHolder, EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
    }

    @Override
    public boolean canAttach() {
        return false;
    }

    public EnumActionResult onAxeClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        return EnumActionResult.PASS;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {

    }
}

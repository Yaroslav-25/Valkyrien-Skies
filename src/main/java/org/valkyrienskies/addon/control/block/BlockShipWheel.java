package org.valkyrienskies.addon.control.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

@MethodsReturnNonnullByDefault
public class BlockShipWheel extends Block {

    private static final PropertyInteger modelId = PropertyInteger.create("modelid", 0, 15);

    public BlockShipWheel(Material materialIn) {
        super(materialIn);
        setDefaultState(getStateFromMeta(0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, modelId);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(modelId, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(modelId);
    }

}

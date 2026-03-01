package net.qbismx.dollcode.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DollLowerBlock extends Block {

    public static final MapCodec<DollLowerBlock> CODEC = simpleCodec(DollLowerBlock::new);
    // 下半身
    protected static final VoxelShape LOWER_BODY;
    protected static final VoxelShape LOWER_RIGHT_ARM;
    protected static final VoxelShape LOWER_LEFT_ARM;
    protected static final VoxelShape RIGHT_LEG;
    protected static final VoxelShape LEFT_LEG;
    // 下半身パーツの合成
    protected static final VoxelShape LOWER_SHAPE;

    @Override
    protected MapCodec<? extends DollLowerBlock> codec() { return CODEC;}

    public DollLowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return LOWER_SHAPE;
    }

    static {
        LOWER_BODY = Block.box((double)4.4F, (double)10.8F, (double)6.2F, (double)11.6F, (double)16.0F, (double)9.8F); // 計算済
        LOWER_RIGHT_ARM = Block.box((double)0.8F, (double)10.8F, (double)6.2F, (double)4.4F, (double)16.0F, (double)9.8F); // 計算済
        LOWER_LEFT_ARM = Block.box((double)11.6F, (double)10.8F, (double)6.2F, (double)15.2F, (double)16.0F, (double)9.8F); // 計算済
        RIGHT_LEG = Block.box((double)4.4F, (double)0.0F, (double)6.2F, (double)8.0F, (double)10.8F, (double)9.8F); // 計算済
        LEFT_LEG = Block.box((double)8.0F, (double)0.0F, (double)6.2F, (double)11.6F, (double)10.8F, (double)9.8F); // 計算済
        LOWER_SHAPE = Shapes.or(LOWER_BODY, LOWER_RIGHT_ARM, LOWER_LEFT_ARM, RIGHT_LEG, LEFT_LEG);
    }
}

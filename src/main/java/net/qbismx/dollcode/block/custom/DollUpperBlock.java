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

public class DollUpperBlock extends Block {

    public static final MapCodec<DollUpperBlock> CODEC = simpleCodec(DollUpperBlock::new);
    // 上半身, デフォルトでは南の方を見ている
    protected static final VoxelShape HEAD;
    protected static final VoxelShape UPPER_BODY;
    protected static final VoxelShape UPPER_RIGHT_ARM;
    protected static final VoxelShape UPPER_LEFT_ARM;
    // 上半身パーツの合成
    protected static final VoxelShape UPPER_SHAPE;

    @Override
    protected MapCodec<? extends DollUpperBlock> codec() { return CODEC;}

    public DollUpperBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return UPPER_SHAPE;
    }

    static {
        HEAD = Block.box((double)4.4F, (double)5.6F, (double)4.4F, (double)11.6F, (double)12.8F, (double)11.6F); // 計算済
        UPPER_BODY = Block.box((double)4.4F, (double)0.0F, (double)6.2F, (double)11.6F, (double)5.6F, (double)9.8F); // 計算済
        UPPER_RIGHT_ARM = Block.box((double)0.8F, (double)0.0F, (double)6.2F, (double)4.4F, (double)5.6F, (double)9.8F); // 計算済
        UPPER_LEFT_ARM = Block.box((double)11.6F, (double)0.0F, (double)6.2F, (double)15.2F, (double)5.6F, (double)9.8F); // 計算済
        UPPER_SHAPE = Shapes.or(HEAD, UPPER_BODY, UPPER_RIGHT_ARM, UPPER_LEFT_ARM);
    }

}

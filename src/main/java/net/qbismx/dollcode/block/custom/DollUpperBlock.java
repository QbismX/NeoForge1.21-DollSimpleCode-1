package net.qbismx.dollcode.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DollUpperBlock extends Block {

    public static final MapCodec<DollUpperBlock> CODEC = simpleCodec(DollUpperBlock::new);
    // 上半身, デフォルトでは南の方を見ている
    protected static final VoxelShape HEAD;
    protected static final VoxelShape UPPER_BODY_SN; // 南北
    protected static final VoxelShape UPPER_BODY_EW; // 東西
    // 向き
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    protected MapCodec<? extends DollUpperBlock> codec() { return CODEC;}

    public DollUpperBlock(Properties properties) {
        super(properties);
        // 向きに関する性質を加える
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        if (state.getValue(FACING).getAxis() == Direction.Axis.Z) {
            return Shapes.or(HEAD, UPPER_BODY_SN); // 北か南
        } else {
            return Shapes.or(HEAD, UPPER_BODY_EW); // 西か東
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        level.destroyBlock(pos.below(), true);
        return super.playerWillDestroy(level, pos, state, player);
    }

    // 構造物生成時用?
    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    // 構造物生成時用?
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        // state.rotate(mirror.getRotation(state.getValue(FACING)));　これは推奨されていない。
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    // 状態の定義
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING); // 向き
    }

    static {
        HEAD = Block.box((double)4.25F, (double)6.5F, (double)4.25F, (double)11.75F, (double)14F, (double)11.75F); // 計算済
        UPPER_BODY_SN = Block.box((double)0.5F, (double)0.0F, (double)6.125F, (double)15.5F, (double)6.5F, (double)9.875F); // 計算済
        UPPER_BODY_EW = Block.box((double)6.125F, (double)0.0F, (double)0.5F, (double)9.875F, (double)6.5F, (double)15.5F);
    }

}

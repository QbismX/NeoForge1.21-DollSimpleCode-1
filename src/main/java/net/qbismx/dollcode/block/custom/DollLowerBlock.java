package net.qbismx.dollcode.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.qbismx.dollcode.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class DollLowerBlock extends Block {
    // 1.8メートルとしたときに小さくなる。公式身長の1.875かもしれません。高さがちょうど30になるため。(2ブロックを32としたとき)
    public static final MapCodec<DollLowerBlock> CODEC = simpleCodec(DollLowerBlock::new);
    // 下半身(南北)
    protected static final VoxelShape LOWER_BODY_SN;
    protected static final VoxelShape LEG_SN;
    // 下半身(東西)
    protected static final VoxelShape LOWER_BODY_EW;
    protected static final VoxelShape LEG_EW;
    // 向き
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    protected MapCodec<? extends DollLowerBlock> codec() { return CODEC;}

    public DollLowerBlock(Properties properties) {
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
            return Shapes.or(LOWER_BODY_SN, LEG_SN); // 北か南
        } else {
            return Shapes.or(LOWER_BODY_EW, LEG_EW); // 西か東
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        level.setBlock(pos.above(), ModBlocks.DOLL_UPPER_BLOCK.get().defaultBlockState().setValue(FACING, state.getValue(FACING)), 3);
    }

    // 設置前の確認
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        // 2ブロックで構成したいので、置く場所の上にブロックがあったり、置く場所が最大高度であってはならない。
        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)) {
            // 設置時にブロックの向きに関する値をセットする
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        }

        return null;
    }

    // 破壊の連動
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        level.destroyBlock(pos.above(), false);
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
        LOWER_BODY_SN = Block.box((double)0.5F, (double)11.25F, (double)6.125F, (double)15.5F, (double)16.0F, (double)9.875F);
        LEG_SN = Block.box((double)4.25F, (double)0.0F, (double)6.125F, (double)11.75F, (double)11.25F, (double)9.875F);
        LOWER_BODY_EW = Block.box((double)6.125F, (double)11.25F, (double)0.5F, (double)9.875F, (double)16.0F, (double)15.5F);
        LEG_EW = Block.box((double)6.125F, (double)0.0F, (double)4.25F, (double)9.875F, (double)11.25F, (double)11.75F);
    }
}

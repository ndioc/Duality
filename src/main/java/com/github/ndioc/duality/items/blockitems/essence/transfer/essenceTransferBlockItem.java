package com.github.ndioc.duality.items.blockitems.essence.transfer;

import com.github.ndioc.duality.blockentities.blockentitytypes;
import com.github.ndioc.duality.main;
import com.github.ndioc.duality.mechanics.essence.essenceBlockConstants;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class essenceTransferBlockItem extends BlockItem {
  public essenceTransferBlockItem(Block block, Settings settings) {
    super(block, settings);
    this.block = block;
    setConstants();
  }

  private void setConstants () {
    int[][] AiA = essenceBlockConstants.fetchConstants(block.getTranslationKey());
    if (AiA != null) {
      maxSources = AiA[0][4];
      maxDestinations = AiA[0][5];
    }
  }

  private Block block;

  private int maxSources;
  private int maxDestinations;

  BlockPos[] sources = new BlockPos[maxSources];
  BlockPos[] destinations = new BlockPos[maxDestinations];

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {

    PlayerEntity player = context.getPlayer();
    World world = context.getWorld();

    if (player != null && world != null) {
      if (player.isSneaking()) {
        BlockEntity entity = world.getBlockEntity(context.getBlockPos());
        if (entity != null && entity.getType() == blockentitytypes.ESSENCE_CONTAINER) {
          main.LOGGER.info("IfIfIf Gate Works :)");
          return ActionResult.PASS;
        }
      }
    }



    return super.useOnBlock(context);
  }
}

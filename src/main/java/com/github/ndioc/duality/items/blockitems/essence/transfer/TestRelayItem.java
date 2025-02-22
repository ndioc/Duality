package com.github.ndioc.duality.items.blockitems.essence.transfer;

import com.github.ndioc.duality.main;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import static com.github.ndioc.duality.blocks.blocks.TEST_RELAY;

public class TestRelayItem extends BlockItem {
  public TestRelayItem(Block block , Settings settings) {
    super(block, settings);
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    main.LOGGER.info("blockitem testing");
    return super.useOnBlock(context);
  }
}

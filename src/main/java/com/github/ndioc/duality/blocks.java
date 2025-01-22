package com.github.ndioc.duality;

import com.github.ndioc.duality.block.wispwoodlog.WispwoodLog;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.ndioc.duality.utilities.registerblock;

public class blocks {

  public static void initialize(){
  }

  public static final Block WISPWOOD_LOG = registerblock(
      "wispwood_log", true,
      new WispwoodLog(FabricBlockSettings.create()
          .sounds(BlockSoundGroup.WOOD)
          .hardness(6f)
          .requiresTool()
          .burnable()));

}

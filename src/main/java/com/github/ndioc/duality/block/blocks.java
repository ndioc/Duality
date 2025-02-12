package com.github.ndioc.duality.block;

import com.github.ndioc.duality.block.wispwood.WispwoodLog;
import com.github.ndioc.duality.block.wispwood.WispwoodVein;
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

  public static final Block WISPWOOD_VEIN = registerblock(
      "wispwood_vein", false,
      new WispwoodVein(FabricBlockSettings.create()
          ));

}

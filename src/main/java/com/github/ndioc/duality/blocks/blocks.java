package com.github.ndioc.duality.blocks;

import com.github.ndioc.duality.blocks.essence.storage.*;
import com.github.ndioc.duality.blocks.essence.transfer.*;
import com.github.ndioc.duality.blocks.natural.wispwood.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.ndioc.duality.utilities.*;

public class blocks {

  public static void initialize(){
  }

  public static final Block WISPWOOD_LOG = registerblock(
      "wispwood_log", true,
      new WispwoodLog(FabricBlockSettings.create()
          .sounds(BlockSoundGroup.WOOD)
          .hardness(6f)
          .requiresTool()
          .burnable()
          .nonOpaque()));

  public static final Block TEST_ORB = registerblock(
      "test_orb", true,
      new TestOrb(FabricBlockSettings.create()
          .nonOpaque()));

  public static final Block CREATIVE_ORB = registerblock(
      "creative_orb", true,
      new CreativeOrb(FabricBlockSettings.create()
          .nonOpaque()));

  public static final Block TEST_RELAY = registerblock(
      "test_relay",
      false,
      new TestRelay(FabricBlockSettings.create()
          .nonOpaque()));

}

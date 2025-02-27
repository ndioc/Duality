package com.github.ndioc.duality.client.models.blockentities;

import com.github.ndioc.duality.main;
import com.github.ndioc.duality.util.objects.rendering.*;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.*;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SelectionOutlineModel implements UnbakedModel, BakedModel, FabricBakedModel {

  private Mesh mesh;

  private QuadData[] quadsUP = new QuadData[1];
  private QuadData[] quadsDOWN = new QuadData[1];
  private QuadData[] quadsEAST = new QuadData[1];
  private QuadData[] quadsWEST = new QuadData[1];
  private QuadData[] quadsNORTH = new QuadData[1];
  private QuadData[] quadsSOUTH = new QuadData[1];

  // | UNBAKED MODEL START |

  @Override
  public Collection<Identifier> getModelDependencies() {
    return List.of();
  }

  @Override
  public void setParents(Function<Identifier, UnbakedModel> modelLoader) {
  }

  @Override
  public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {

    Renderer renderer = RendererAccess.INSTANCE.getRenderer();
    MeshBuilder meshBuilder = renderer.meshBuilder();
    QuadEmitter quadEmitter = meshBuilder.getEmitter();
    createQuadData();

    for (Direction direction : Direction.values()) {
      switch (direction) {
        case UP:
          for (QuadData quadData : quadsUP) {
            SquareData squD = quadData.getSquareData();
            ColorData colD = quadData.getColorData();
            SpriteData sprD = quadData.getSpriteData();

            quadEmitter.square(direction, squD.getLeft(), squD.getBottom(), squD.getRight(), squD.getTop(), squD.getDepth());
            quadEmitter.pos(squD.getVertexIndex(), squD.getX(), squD.getY(), squD.getZ());
            quadEmitter.color(colD.getRed(), colD.getGreen(), colD.getBlue(), colD.getAlpha());
            quadEmitter.spriteBake(sprD.getSprite(), sprD.getBakeFlags());
            quadEmitter.uv(squD.getVertexIndex(), sprD.getU(), sprD.getV());
            quadEmitter.emit();
          }
          break;
        case DOWN:
          for (QuadData quadData : quadsDOWN) {
            SquareData squD = quadData.getSquareData();
            ColorData colD = quadData.getColorData();
            SpriteData sprD = quadData.getSpriteData();

            quadEmitter.square(direction, squD.getLeft(), squD.getBottom(), squD.getRight(), squD.getTop(), squD.getDepth());
            quadEmitter.pos(squD.getVertexIndex(), squD.getX(), squD.getY(), squD.getZ());
            quadEmitter.color(colD.getRed(), colD.getGreen(), colD.getBlue(), colD.getAlpha());
            quadEmitter.spriteBake(sprD.getSprite(), sprD.getBakeFlags());
            quadEmitter.uv(squD.getVertexIndex(), sprD.getU(), sprD.getV());
            quadEmitter.emit();
          }
          break;
        case EAST:
          for (QuadData quadData : quadsEAST) {
            SquareData squD = quadData.getSquareData();
            ColorData colD = quadData.getColorData();
            SpriteData sprD = quadData.getSpriteData();

            quadEmitter.square(direction, squD.getLeft(), squD.getBottom(), squD.getRight(), squD.getTop(), squD.getDepth());
            quadEmitter.pos(squD.getVertexIndex(), squD.getX(), squD.getY(), squD.getZ());
            quadEmitter.color(colD.getRed(), colD.getGreen(), colD.getBlue(), colD.getAlpha());
            quadEmitter.spriteBake(sprD.getSprite(), sprD.getBakeFlags());
            quadEmitter.uv(squD.getVertexIndex(), sprD.getU(), sprD.getV());
            quadEmitter.emit();
          }
          break;
        case WEST:
          for (QuadData quadData : quadsWEST) {
            SquareData squD = quadData.getSquareData();
            ColorData colD = quadData.getColorData();
            SpriteData sprD = quadData.getSpriteData();

            quadEmitter.square(direction, squD.getLeft(), squD.getBottom(), squD.getRight(), squD.getTop(), squD.getDepth());
            quadEmitter.pos(squD.getVertexIndex(), squD.getX(), squD.getY(), squD.getZ());
            quadEmitter.color(colD.getRed(), colD.getGreen(), colD.getBlue(), colD.getAlpha());
            quadEmitter.spriteBake(sprD.getSprite(), sprD.getBakeFlags());
            quadEmitter.uv(squD.getVertexIndex(), sprD.getU(), sprD.getV());
            quadEmitter.emit();
          }
          break;
        case NORTH:
          for (QuadData quadData : quadsNORTH) {
            SquareData squD = quadData.getSquareData();
            ColorData colD = quadData.getColorData();
            SpriteData sprD = quadData.getSpriteData();

            quadEmitter.square(direction, squD.getLeft(), squD.getBottom(), squD.getRight(), squD.getTop(), squD.getDepth());
            quadEmitter.pos(squD.getVertexIndex(), squD.getX(), squD.getY(), squD.getZ());
            quadEmitter.color(colD.getRed(), colD.getGreen(), colD.getBlue(), colD.getAlpha());
            quadEmitter.spriteBake(sprD.getSprite(), sprD.getBakeFlags());
            quadEmitter.uv(squD.getVertexIndex(), sprD.getU(), sprD.getV());
            quadEmitter.emit();
          }
          break;
        case SOUTH:
          for (QuadData quadData : quadsSOUTH) {
            SquareData squD = quadData.getSquareData();
            ColorData colD = quadData.getColorData();
            SpriteData sprD = quadData.getSpriteData();

            quadEmitter.square(direction, squD.getLeft(), squD.getBottom(), squD.getRight(), squD.getTop(), squD.getDepth());
            quadEmitter.pos(squD.getVertexIndex(), squD.getX(), squD.getY(), squD.getZ());
            quadEmitter.color(colD.getRed(), colD.getGreen(), colD.getBlue(), colD.getAlpha());
            quadEmitter.spriteBake(sprD.getSprite(), sprD.getBakeFlags());
            quadEmitter.uv(squD.getVertexIndex(), sprD.getU(), sprD.getV());
            quadEmitter.emit();
          }
          break;
        default:
          main.LOGGER.warn("SelectionOutlineModel Switch used default case");
        quadEmitter.square(direction, 0f, 0f, 1f, 1f, 0f);
        quadEmitter.color(255, 255, 255, 255);
        quadEmitter.emit();
      }
    }
    mesh = meshBuilder.build();

    return this;
  }

  // | UNBAKED MODEL END | BAKED MODEL START |

  @Override
  public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
    return List.of();
  }

  @Override
  public boolean useAmbientOcclusion() {
    return true;
  }

  @Override
  public boolean hasDepth() {
    return false;
  }

  @Override
  public boolean isSideLit() {
    return false;
  }

  @Override
  public boolean isBuiltin() {
    return false;
  }

  @Override
  public Sprite getParticleSprite() {
    return null;
  }

  @Override
  public ModelTransformation getTransformation() {
    return null;
  }

  @Override
  public ModelOverrideList getOverrides() {
    return null;
  }

  // | BAKED MODEL END | FABRIC MODEL START |


  @Override
  public boolean isVanillaAdapter() {
    return false;
  }

  @Override
  public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
    mesh.outputTo(context.getEmitter());
  }

  @Override
  public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {

  }

  // | FABRIC MODEL END |

  private void createQuadData() {

  }

  public void registerQuadData(QuadData quadData, Direction[] directions) {
    for (Direction dir : directions) {
      if (dir == Direction.UP) {

      }
      if (dir == Direction.DOWN) {

      }
      if (dir == Direction.EAST) {

      }
      if (dir == Direction.WEST) {

      }
      if (dir == Direction.NORTH) {

      }
      if (dir == Direction.SOUTH) {

      }
    }
  }
}

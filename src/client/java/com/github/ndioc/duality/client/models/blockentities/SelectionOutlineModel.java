package com.github.ndioc.duality.client.models.blockentities;

import com.github.ndioc.duality.client.util.objects.rendering.*;
import com.github.ndioc.duality.main;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.*;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
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

import static com.github.ndioc.duality.main.MOD_ID;

public class SelectionOutlineModel implements UnbakedModel, BakedModel, FabricBakedModel {

  private Mesh mesh;
  private Sprite sprite;

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
    sprite = textureGetter.apply(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier(MOD_ID, "selection_outline")));
    createQuadData();

    for (Direction direction : Direction.values()) {
      switch (direction) {
        case UP:
          for (QuadData quadData : quadsUP) {
            readQuadData(quadData, quadEmitter, direction);
            quadEmitter.emit();
          }
          break;
        case DOWN:
          for (QuadData quadData : quadsDOWN) {
            readQuadData(quadData, quadEmitter, direction);
            quadEmitter.emit();
          }
          break;
        case EAST:
          for (QuadData quadData : quadsEAST) {
            readQuadData(quadData, quadEmitter, direction);
            quadEmitter.emit();
          }
          break;
        case WEST:
          for (QuadData quadData : quadsWEST) {
            readQuadData(quadData, quadEmitter, direction);
            quadEmitter.emit();
          }
          break;
        case NORTH:
          for (QuadData quadData : quadsNORTH) {
            readQuadData(quadData, quadEmitter, direction);
            quadEmitter.emit();
          }
          break;
        case SOUTH:
          for (QuadData quadData : quadsSOUTH) {
            readQuadData(quadData, quadEmitter, direction);
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
    return sprite;
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

  private void readQuadData(QuadData quadData, QuadEmitter quadEmitter, Direction direction) {
    SquareData squD = quadData.getSquareData();
    ColorData colD = quadData.getColorData();
    SpriteData sprD = quadData.getSpriteData();

    quadEmitter.square(direction, squD.getLeft(), squD.getBottom(), squD.getRight(), squD.getTop(), squD.getDepth());
    if (squD.getX() != Float.MIN_VALUE || squD.getY() != Float.MIN_VALUE || squD.getZ() != Float.MIN_VALUE) {
      quadEmitter.pos(squD.getVertexIndex(), squD.getX(), squD.getY(), squD.getZ());
    }
    quadEmitter.color(colD.getRed(), colD.getGreen(), colD.getBlue(), colD.getAlpha());
    quadEmitter.spriteBake(sprD.getSprite(), sprD.getBakeFlags());
    quadEmitter.uv(squD.getVertexIndex(), sprD.getU(), sprD.getV());
  }
// LEARN HOW TO CREATE A SPRITE IN THE CODE THEN IT MIGHT WORK
  private void createQuadData() {
    registerQuadData(new QuadData(new SquareData(0f, 0f, 1f, 1f, 0f, 0, Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE), new ColorData(255, 255, 255, 255), new SpriteData(sprite, 0, 0f, 0f)), new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH});
  }

  public void registerQuadData(QuadData quadData, Direction[] directions) {
    for (Direction dir : directions) {
      if (dir == Direction.UP) {
        if (quadsUP[0] == null && quadsUP.length == 1) {
          quadsUP[0] = quadData;
        }
        else {
          QuadData[] arraytoread = quadsUP;
          QuadData[] arraytowrite = new QuadData[arraytoread.length + 1];
          System.arraycopy(arraytoread, 0, arraytowrite, 0, arraytoread.length);
          arraytowrite[arraytowrite.length - 1] = quadData;
          quadsUP = arraytowrite;
        }
      }
      if (dir == Direction.DOWN) {
        if (quadsDOWN[0] == null && quadsDOWN.length == 1) {
          quadsDOWN[0] = quadData;
        }
        else {
          QuadData[] arraytoread = quadsDOWN;
          QuadData[] arraytowrite = new QuadData[arraytoread.length + 1];
          System.arraycopy(arraytoread, 0, arraytowrite, 0, arraytoread.length);
          arraytowrite[arraytowrite.length - 1] = quadData;
          quadsDOWN = arraytowrite;
        }
      }
      if (dir == Direction.EAST) {
        if (quadsEAST[0] == null && quadsEAST.length == 1) {
          quadsEAST[0] = quadData;
        }
        else {
          QuadData[] arraytoread = quadsEAST;
          QuadData[] arraytowrite = new QuadData[arraytoread.length + 1];
          System.arraycopy(arraytoread, 0, arraytowrite, 0, arraytoread.length);
          arraytowrite[arraytowrite.length - 1] = quadData;
          quadsEAST = arraytowrite;
        }
      }
      if (dir == Direction.WEST) {
        if (quadsWEST[0] == null && quadsWEST.length == 1) {
          quadsWEST[0] = quadData;
        }
        else {
          QuadData[] arraytoread = quadsWEST;
          QuadData[] arraytowrite = new QuadData[arraytoread.length + 1];
          System.arraycopy(arraytoread, 0, arraytowrite, 0, arraytoread.length);
          arraytowrite[arraytowrite.length - 1] = quadData;
          quadsWEST = arraytowrite;
        }
      }
      if (dir == Direction.NORTH) {
        if (quadsNORTH[0] == null && quadsNORTH.length == 1) {
          quadsNORTH[0] = quadData;
        }
        else {
          QuadData[] arraytoread = quadsNORTH;
          QuadData[] arraytowrite = new QuadData[arraytoread.length + 1];
          System.arraycopy(arraytoread, 0, arraytowrite, 0, arraytoread.length);
          arraytowrite[arraytowrite.length - 1] = quadData;
          quadsNORTH = arraytowrite;
        }
      }
      if (dir == Direction.SOUTH) {
        if (quadsSOUTH[0] == null && quadsSOUTH.length == 1) {
          quadsSOUTH[0] = quadData;
        }
        else {
          QuadData[] arraytoread = quadsSOUTH;
          QuadData[] arraytowrite = new QuadData[arraytoread.length + 1];
          System.arraycopy(arraytoread, 0, arraytowrite, 0, arraytoread.length);
          arraytowrite[arraytowrite.length - 1] = quadData;
          quadsSOUTH = arraytowrite;
        }
      }
    }
  }
}

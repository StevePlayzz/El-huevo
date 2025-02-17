package gg.cookiejar.el_huevo.core.mixin;

import gg.cookiejar.el_huevo.common.misc.ElHuevoSpawner;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Sarinsa, Steven
 */
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements WorldGenLevel {
    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l) {
        super(writableLevelData, resourceKey, holder, supplier, bl, bl2, l);
    }

    @ModifyVariable(method = "<init>", at = @At(value = "FIELD", target = "net/minecraft/server/level/ServerLevel.customSpawners:Ljava/util/List;", shift = At.Shift.BEFORE), index = 12, argsOnly = true)
    private List<CustomSpawner> changeSpawnersList(List<CustomSpawner> spawnerList) {
        List<CustomSpawner> newList = new ArrayList<>();
        newList.add(new ElHuevoSpawner());
        newList.addAll(spawnerList);
        spawnerList = newList;
        return spawnerList;
    }
}
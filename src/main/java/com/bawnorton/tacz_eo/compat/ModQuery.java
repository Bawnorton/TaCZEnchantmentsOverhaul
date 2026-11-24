package com.bawnorton.tacz_eo.compat;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;

public final class ModQuery {
    private static final Object2BooleanMap<String> modAbsenceCache = new Object2BooleanOpenHashMap<>();

    public static boolean isModAbsent(String id) {
        if (modAbsenceCache.containsKey(id)) {
            return modAbsenceCache.getBoolean(id);
        }

        boolean absent = isModAbsentInternal(id);
        modAbsenceCache.put(id, absent);
        return absent;
    }

    private static boolean isModAbsentInternal(String id) {
        ModList modList = ModList.get();
        if (modList != null) {
            return !modList.isLoaded(id);
        }
        LoadingModList loadingModList = LoadingModList.get();
        if (loadingModList == null) {
            return true;
        }

        for (ModInfo modInfo : loadingModList.getMods()) {
            if (modInfo.getModId().equals(id)) {
                return false;
            }
        }
        return true;
    }
}

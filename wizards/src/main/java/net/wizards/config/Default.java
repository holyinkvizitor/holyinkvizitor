package net.wizards.config;

import net.fabric_extras.structure_pool.api.StructurePoolConfig;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.loot.LootConfig;
import net.wizards.item.Armors;
import net.wizards.item.Weapons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Default {
    public final static ItemConfig itemConfig;
    public final static StructurePoolConfig villageConfig;
    static {
        itemConfig = new ItemConfig();
        for (var weapon: Weapons.entries) {
            itemConfig.weapons.put(weapon.name(), weapon.defaults());
        }
        for (var armorSet: Armors.entries) {
            itemConfig.armor_sets.put(armorSet.name(), armorSet.defaults());
        }

        villageConfig = new StructurePoolConfig();
        var limit = 1;
        villageConfig.entries.addAll(List.of(
                new StructurePoolConfig.Entry("minecraft:village/desert/houses", new ArrayList<>(Arrays.asList(
                        new StructurePoolConfig.Entry.Structure("wizards:village/desert/wizard_tower", 1, limit),
                        new StructurePoolConfig.Entry.Structure("wizards:village/desert/wizard_tower_2", 3, limit))
                )),
                new StructurePoolConfig.Entry("minecraft:village/savanna/houses", "wizards:village/savanna/wizard_tower", 3, limit),

                new StructurePoolConfig.Entry("minecraft:village/plains/houses", "wizards:village/plains/wizard_tower", 3, limit),

                new StructurePoolConfig.Entry("minecraft:village/taiga/houses", "wizards:village/taiga/wizard_tower", 3, limit),

                new StructurePoolConfig.Entry("minecraft:village/snowy/houses", new ArrayList<>(Arrays.asList(
                        new StructurePoolConfig.Entry.Structure("wizards:village/snowy/wizard_tower", 1, limit),
                        new StructurePoolConfig.Entry.Structure("wizards:village/snowy/wizard_tower_2", 3, limit))
                ))
        ));
    }

    @SafeVarargs
    private static <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }
}

package net.archers.item;

import net.archers.ArchersMod;
import net.archers.item.weapon.CustomWeaponItem;
import net.fabric_extras.ranged_weapon.api.CustomBow;
import net.fabric_extras.ranged_weapon.api.CustomCrossbow;
import net.fabric_extras.ranged_weapon.api.CustomRangedWeapon;
import net.fabric_extras.ranged_weapon.api.RangedConfig;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.weapon.Weapon;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

public class Weapons {
    public static final ArrayList<RangedEntry> rangedEntries = new ArrayList<>();
    public static final ArrayList<Weapon.Entry> meleeEntries = new ArrayList<>();
    public record RangedEntry(Identifier id, Item item, RangedConfig defaults) { }

    private static Supplier<Ingredient> ingredient(String idString) {
        return ingredient(idString, Items.DIAMOND);
    }

    private static Supplier<Ingredient> ingredient(String idString, Item fallback) {
        var id = new Identifier(idString);
        return () -> {
            var item = Registries.ITEM.get(id);
            var ingredient = item != null ? item : fallback;
            return Ingredient.ofItems(ingredient);
        };
    }

    /**
     * MELEE WEAPONS
     */

    private static Weapon.Entry addMelee(String requiredMod, String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        var entry = new Weapon.Entry(ArchersMod.ID, name, material, item, defaults, null);
        if (entry.isRequiredModInstalled()) {
            // entries.add(entry);
            meleeEntries.add(entry);
        }
        return entry;
    }

    private static Weapon.Entry spear(String name, Weapon.CustomMaterial material, float damage) {
        return spear(null, name, material, damage);
    }

    private static Weapon.Entry spear(String requiredMod, String name, Weapon.CustomMaterial material, float damage) {
        var settings = new Item.Settings();
        var item = new CustomWeaponItem(material, settings);
        return addMelee(requiredMod, name, material, item, new ItemConfig.Weapon(damage, -3F));
    }
    
    public static final Weapon.Entry flint_spear = spear("flint_spear",
            Weapon.CustomMaterial.matching(ToolMaterials.STONE, () -> Ingredient.ofItems(Items.FLINT)), 4F);
    public static final Weapon.Entry iron_spear = spear("iron_spear",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.IRON_INGOT)), 5F);
    public static final Weapon.Entry golden_spear = spear("golden_spear",
            Weapon.CustomMaterial.matching(ToolMaterials.GOLD, () -> Ingredient.ofItems(Items.GOLD_INGOT)), 3F);
    public static final Weapon.Entry diamond_spear = spear("diamond_spear",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.DIAMOND)), 6F);
    public static final Weapon.Entry netherite_spear = spear("netherite_spear",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)), 7F);

    /**
     * RANGED WEAPONS
     */

    private static RangedEntry addRanged(Identifier id, Item item, RangedConfig defaults) {
        var entry = new RangedEntry(id, item, defaults);
        rangedEntries.add(entry);
        return entry;
    }

    private static RangedEntry bow(String name, int durability, Supplier<Ingredient> repairIngredientSupplier, RangedConfig defaults) {
        var settings = new FabricItemSettings().maxDamage(durability);
        var item = new CustomBow(settings, repairIngredientSupplier);
        ((CustomRangedWeapon)item).configure(defaults);
        return addRanged(new Identifier(ArchersMod.ID, name), item, defaults);
    }

    private static RangedEntry crossbow(String name, int durability, Supplier<Ingredient> repairIngredientSupplier, RangedConfig defaults) {
        var settings = new FabricItemSettings().maxDamage(durability);
        var item = new CustomCrossbow(settings, repairIngredientSupplier);
        ((CustomRangedWeapon)item).configure(defaults);
        return addRanged(new Identifier(ArchersMod.ID, name), item, defaults);
    }

    private static final int durabilityTier0 = 384;
    private static final int durabilityTier1 = 465; // Matches Vanilla Crossbow durability
    private static final int durabilityTier2 = ToolMaterials.DIAMOND.getDurability();
    private static final int durabilityTier3 = ToolMaterials.NETHERITE.getDurability();

    private static final int pullTime_shortBow = 16;
    private static final int pullTime_longBow = 30;
    private static final int pullTime_rapidCrossbow = 20;
    private static final int pullTime_heavyCrossbow = 35;

    /**
     * DPS Tiers
     * T0: 6 DPS (from Vanilla Bow, 6 / 1)
     * T1: 7.2 DPS (from Vanilla CrossBow, 9 / 1.25)
     *  T0 -> T1  20% increase
     * T2: 8.4 DPS
     * T3: 9.6 DPS
     *
     * Target Item Damage = DPS * Pull Time (for example:  7.2 * (25/20) )
     */

    /**
     * BOWS
     */

    public static RangedEntry composite_longbow = bow("composite_longbow", durabilityTier1,
            () -> Ingredient.ofItems(Items.BONE),
            new RangedConfig(pullTime_longBow, 8, 0));

    public static RangedEntry mechanic_shortbow = bow("mechanic_shortbow", durabilityTier2,
            () -> Ingredient.ofItems(Items.REDSTONE),
            new RangedConfig(pullTime_shortBow, 8F, 0));

    public static RangedEntry royal_longbow = bow("royal_longbow", durabilityTier2,
            () -> Ingredient.ofItems(Items.GOLD_INGOT),
            new RangedConfig(pullTime_longBow, 10, 0));

    public static RangedEntry netherite_shortbow = bow("netherite_shortbow", durabilityTier3,
            () -> Ingredient.ofItems(Items.NETHERITE_INGOT),
            new RangedConfig(pullTime_shortBow, 9, 0));

    public static RangedEntry netherite_longbow = bow("netherite_longbow", durabilityTier3,
            () -> Ingredient.ofItems(Items.NETHERITE_INGOT),
            new RangedConfig(pullTime_longBow, 12, 0));


    /**
     * CROSSBOWS
     */

    public static RangedEntry rapid_crossbow = crossbow("rapid_crossbow", durabilityTier2,
            () -> Ingredient.ofItems(Items.REDSTONE),
            new RangedConfig(pullTime_rapidCrossbow, 8.5F, 0));

    public static RangedEntry heavy_crossbow = crossbow("heavy_crossbow", durabilityTier2,
            () -> Ingredient.ofItems(Items.DIAMOND),
            new RangedConfig(pullTime_heavyCrossbow, 13, 0));

    public static RangedEntry netherite_rapid_crossbow = crossbow("netherite_rapid_crossbow", durabilityTier3,
            () -> Ingredient.ofItems(Items.NETHERITE_INGOT),
            new RangedConfig(pullTime_rapidCrossbow, 9.5F, 0));

    public static RangedEntry netherite_heavy_crossbow = crossbow("netherite_heavy_crossbow", durabilityTier3,
            () -> Ingredient.ofItems(Items.NETHERITE_INGOT),
            new RangedConfig(pullTime_heavyCrossbow, 15, 0));


    public static void register(Map<String, RangedConfig> rangedConfig, Map<String, ItemConfig.Weapon> meleeConfig) {
        if (FabricLoader.getInstance().isModLoaded("betterend")) {
            spear("aeternium_spear",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, ingredient("betterend:aeternium_ingot")), 8F);
            bow("crystal_shortbow", durabilityTier3,
                    ingredient("betterend:crystal_shards"),
                    new RangedConfig(pullTime_shortBow, 10F, 0));
            bow("crystal_longbow", durabilityTier3,
                    ingredient("betterend:crystal_shards"),
                    new RangedConfig(pullTime_longBow, 13.5F, 0));
        }
        if (FabricLoader.getInstance().isModLoaded("betternether")) {
            spear("ruby_spear",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, ingredient("betternether:nether_ruby")), 8F);
            crossbow("ruby_rapid_crossbow", durabilityTier3,
                    ingredient("betternether:nether_ruby"),
                    new RangedConfig(pullTime_rapidCrossbow, 10.5F, 0));
            crossbow("ruby_heavy_crossbow", durabilityTier3,
                    ingredient("betternether:nether_ruby"),
                    new RangedConfig(pullTime_heavyCrossbow, 17, 0));
        }

        Weapon.register(meleeConfig, meleeEntries, Group.KEY);
        for (var entry: rangedEntries) {
            var config = rangedConfig.get(entry.id.toString());
            if (config == null) {
                config = entry.defaults;
                rangedConfig.put(entry.id.toString(), config);
            }
            ((CustomRangedWeapon)entry.item).configure(config);
            Registry.register(Registries.ITEM, entry.id, entry.item);
        }
        ItemGroupEvents.modifyEntriesEvent(Group.KEY).register((content) -> {
            for (var entry: rangedEntries) {
                content.add(entry.item);
            }
        });
    }
}

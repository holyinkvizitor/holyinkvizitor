package net.wizards.item;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.weapon.StaffItem;
import net.spell_engine.api.item.weapon.Weapon;
import net.spell_power.api.SpellSchools;
import net.wizards.WizardsMod;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

public class Weapons {
    public static final ArrayList<Weapon.Entry> entries = new ArrayList<>();

    private static Weapon.Entry entry(String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        return entry(null, name, material, item, defaults);
    }

    private static Weapon.Entry entry(String requiredMod, String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        var entry = new Weapon.Entry(WizardsMod.ID, name, material, item, defaults, null);
        if (entry.isRequiredModInstalled()) {
            entries.add(entry);
        }
        return entry;
    }

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

    // MARK: Wands

    private static final float wandAttackDamage = 2;
    private static final float wandAttackSpeed = -2.4F;
    private static Weapon.Entry wand(String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        var item = new StaffItem(material, settings);
        return entry(name, material, item, new ItemConfig.Weapon(wandAttackDamage, wandAttackSpeed));
    }

    public static final Weapon.Entry noviceWand = wand("wand_novice",
            Weapon.CustomMaterial.matching(ToolMaterials.WOOD, () -> Ingredient.ofItems(Items.STICK)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 1));
    public static final Weapon.Entry arcaneWand = wand("wand_arcane",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.GOLD_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 2));
    public static final Weapon.Entry fireWand = wand("wand_fire",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.GOLD_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 2));
    public static final Weapon.Entry frostWand = wand("wand_frost",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.IRON_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 2));

    public static final Weapon.Entry netheriteArcaneWand = wand("wand_netherite_arcane",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 3));
    public static final Weapon.Entry netheriteFireWand = wand("wand_netherite_fire",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 3));
    public static final Weapon.Entry netheriteFrostWand = wand("wand_netherite_frost",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 3));


    // MARK: Staves

    private static final float staffAttackDamage = 4;
    private static final float staffAttackSpeed = -3F;

    private static Weapon.Entry staff(String name, Weapon.CustomMaterial material) {
        return staff(null, name, material);
    }

    private static Weapon.Entry staff(String requiredMod, String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        var item = new StaffItem(material, settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(staffAttackDamage, staffAttackSpeed));
    }

    public static final Weapon.Entry wizardStaff = staff("staff_wizard",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.STICK)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 3))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 3))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 3));
    public static final Weapon.Entry arcaneStaff = staff("staff_arcane",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.GOLD_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4));
    public static final Weapon.Entry fireStaff = staff("staff_fire",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.GOLD_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 4));
    public static final Weapon.Entry frostStaff = staff("staff_frost",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.IRON_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 4));

    public static final Weapon.Entry netheriteArcaneStaff = staff("staff_netherite_arcane",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 5));
    public static final Weapon.Entry netheriteFireStaff = staff("staff_netherite_fire",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 5));
    public static final Weapon.Entry netheriteFrostStaff = staff("staff_netherite_frost",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 5));

    // MARK: Register

    public static void register(Map<String, ItemConfig.Weapon> configs) {
        if (FabricLoader.getInstance().isModLoaded("betternether")) {
            staff("betternether", "staff_ruby_fire",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, ingredient("betternether:nether_ruby")))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 6));
        }
        if (FabricLoader.getInstance().isModLoaded("betterend")) {
            staff("betterend", "staff_crystal_arcane",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, ingredient("betterend:aeternium_ingot")))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 6));
            staff("betterend", "staff_smaragdant_frost",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, ingredient("betterend:aeternium_ingot")))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 6));
        }

        Weapon.register(configs, entries, Group.KEY);
    }
}

package net.levelz.customChange;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.levelz.screen.SkillScreen;
import net.levelz.screen.widget.LevelzTab;
import net.libz.api.InventoryTab;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MagicTab extends LevelzTab {
    public MagicTab(Text title, Identifier texture, int preferedPos, Class<?>... screenClasses) {
        super(title, texture, preferedPos, screenClasses);
    }
    @Override
    public boolean canClick(Class<?> screenClass, MinecraftClient client) {
        if (screenClass.equals(MagicInfoScreen.class) || screenClass.equals(MagicListScreen.class)) {
            return true;
        }
        return super.canClick(screenClass, client);
    }

    @Override
    public void onClick(MinecraftClient client) {
        client.setScreen(new MagicScreen());
    }
}

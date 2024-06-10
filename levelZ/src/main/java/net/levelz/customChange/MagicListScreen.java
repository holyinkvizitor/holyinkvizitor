package net.levelz.customChange;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.levelz.screen.SkillListScreen;

@Environment(EnvType.CLIENT)
public class MagicListScreen extends SkillListScreen {
    public MagicListScreen(String title) {
        super(title);
    }
}

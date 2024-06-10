package net.levelz.customChange;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.levelz.screen.SkillInfoScreen;

@Environment(EnvType.CLIENT)
public class MagicInfoScreen extends SkillInfoScreen {
    public MagicInfoScreen(String title) {
        super(title);
    }
}

package io.github.some_example_name.model.user;



import io.github.some_example_name.model.enums.SkillType;

import java.util.HashMap;
import java.util.Map;

public class SkillSet {
    private Map<SkillType, Integer> points;

    public SkillSet() {
    }

    public void gainSkill(SkillType type, int value) {
        points.put(type, points.get(type) + value);
    }

    public int getLevel(SkillType type) {
        return 0;
    }
}



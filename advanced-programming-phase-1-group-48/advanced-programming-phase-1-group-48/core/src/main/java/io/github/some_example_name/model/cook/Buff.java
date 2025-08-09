package io.github.some_example_name.model.cook;

public class Buff {
    public enum Type { ENERGY_BOOST, SKILL_ENERGY_REDUCTION }

    private Type type;
    private int amount;
    private int durationInHours;
    private long appliedAtTime; // باید totalHours باشه
    private final String targetSkill;

    public Buff(Type type, int amount, int durationInHours, long appliedAtTime) {
        this(type, amount, durationInHours, appliedAtTime, null);
    }

    public Buff(Type type, int amount, int durationInHours, long appliedAtTime, String targetSkill) {
        this.type = type;
        this.amount = amount;
        this.durationInHours = durationInHours;
        this.appliedAtTime = appliedAtTime;
        this.targetSkill = targetSkill;
    }

    public boolean isExpired(long currentTime) {
        return currentTime >= appliedAtTime + durationInHours;
    }

    public int getRemainingHours(long currentTime) {
        long remaining = (appliedAtTime + durationInHours) - currentTime;
        return (int) Math.max(0, remaining);
    }

    public static Buff parseBuff(String buffStr, long startTotalHours) {
        if (buffStr == null) return null;

        try {
            int hoursIndex = buffStr.lastIndexOf(" (");
            String durationPart = buffStr.substring(hoursIndex + 2, buffStr.length() - 8);
            int duration = Integer.parseInt(durationPart.trim());

            String effectPart = buffStr.substring(0, hoursIndex).trim();

            Buff.Type type;
            int amount;
            String targetSkill = null;

            if (effectPart.toLowerCase().startsWith("max energy")) {
                String[] parts = effectPart.split("\\+");
                amount = Integer.parseInt(parts[1].trim());
                type = Buff.Type.ENERGY_BOOST;
            } else {
                type = Buff.Type.SKILL_ENERGY_REDUCTION;
                targetSkill = effectPart;
                amount = 1;
            }

            // دقت کن: اینجا startTotalHours باید کل ساعت‌های بازی باشه
            return new Buff(type, amount, duration, startTotalHours, targetSkill);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Type getType() { return type; }
    public int getAmount() { return amount; }
    public int getDurationInHours() { return durationInHours; }
    public long getAppliedAtTime() { return appliedAtTime; }
    public String getTargetSkill() { return targetSkill; }
}

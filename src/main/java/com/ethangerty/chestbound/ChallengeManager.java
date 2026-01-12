package com.ethangerty.chestbound;

import com.ethangerty.records.Challengers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ChallengeManager {

    private final Map<String, Challengers> challenges = new ConcurrentHashMap<>();
    private final Map<String, BukkitTask> expiryTasks = new ConcurrentHashMap<>();

    private final JavaPlugin plugin;
    public ChallengeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }



    private String key(UUID challenger, UUID target) {
        return challenger + "->" + target;
    }


    public Challengers get(UUID challenger, UUID target) {
        Challengers c = challenges.get(key(challenger, target));
        if (c == null) return null;

        if (c.isExpired()) {
            remove(challenger, target);
            return null;
        }
        return c;
    }


    public void createOrReplace(UUID challenger, UUID target, long durationMillis) {
        long expiresAt = System.currentTimeMillis() + durationMillis;
        String k = key(challenger, target);

        // If an existing expiry task exists, cancel it
        BukkitTask oldTask = expiryTasks.remove(k);
        if (oldTask != null) oldTask.cancel();

        Challengers c = new Challengers(challenger, target, expiresAt);
        challenges.put(k, c);

        // Auto-expire after duration
        long ticks = Math.max(1, durationMillis / 50L); // 50ms per tick
        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            challenges.remove(k);
            expiryTasks.remove(k);
        }, ticks);

        expiryTasks.put(k, task);
    }


    public void remove(UUID challenger, UUID target) {
        String k = key(challenger, target);

        challenges.remove(k);

        BukkitTask task = expiryTasks.remove(k);
        if (task != null) task.cancel();
    }

    public void clearAll() {
        challenges.clear();
        expiryTasks.values().forEach(BukkitTask::cancel);
        expiryTasks.clear();
    }
}
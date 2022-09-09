package cybersoft.java18.gamedoanso.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSession {
    private static int startId;
    private static Random random = null;
    private final String id;
    private final int target;
    private List<Gues> gues;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long totalTime;
    private boolean isActive;
    private boolean isCompleted;
    private String username;

    public GameSession(String username) {
        this.id = "GAME" + String.format("%05d", getRandom());
        this.username = username;
        this.target = getRandom();
        this.startTime = LocalDateTime.now();
        this.gues = new ArrayList<>();
    }

    public GameSession(String id, int target, LocalDateTime startTime, LocalDateTime endTime, boolean isCompleted, boolean isActive, String username) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.target = target;
        this.isActive = isActive;
        this.isCompleted = isCompleted;
        this.username = username;
        this.gues = new ArrayList<>();
        this.totalTime = calculateTotalTime();
    }


    private int getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random.nextInt(1000 - 1) + 1;
    }

    public int getTarget() {
        return target;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public String getUsername() {
        return username;
    }

    public List<Gues> getGues() {
        return gues;
    }

    public void setGues(List<Gues> gues) {
        this.gues = gues;
    }

    public String getId() {
        return id;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "id='" + id + '\'' +
                ", target=" + target +
                ", gues=" + gues +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalTime=" + totalTime +
                ", isActive=" + isActive +
                ", isCompleted=" + isCompleted +
                ", username='" + username + '\'' +
                '}';
    }

    public long getTotalTime() {
        return totalTime;
    }

    public long calculateTotalTime() {
        if (this.getEndTime() != null) {
            Duration totalTime = Duration.between(this.startTime, this.endTime);
            return totalTime.toSeconds();
        }
        return 0;
    }
}

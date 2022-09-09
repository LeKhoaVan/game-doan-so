package cybersoft.java18.gamedoanso.model;

import java.time.LocalDateTime;

public class Gues {
	private final int value;
	private final String gameSession;
	private final LocalDateTime timestamp;
	private final int result;
	
	public Gues(int value, String gameSession, int result) {
		this.gameSession = gameSession;
		this.value = value;
		this.result = result;
		this.timestamp = LocalDateTime.now();
	}

	public Gues(int value, LocalDateTime moment, String session_id, int result) {
		this.gameSession = session_id;
		this.value = value;
		this.result = result;
		this.timestamp = moment;
	}


	public int getValue() {
		return value;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getResult() {
		return result;
	}

	public String getGameSession() {
		return gameSession;
	}

	@Override
	public String toString() {
		return "Gues{" +
				"value=" + value +
				", gameSession='" + gameSession + '\'' +
				", timestamp=" + timestamp +
				", result=" + result +
				'}';
	}
}

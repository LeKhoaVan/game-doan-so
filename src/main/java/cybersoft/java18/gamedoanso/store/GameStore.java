package cybersoft.java18.gamedoanso.store;

import java.util.ArrayList;
import java.util.List;

import cybersoft.java18.gamedoanso.model.GameSession;
import cybersoft.java18.gamedoanso.model.Player;
import cybersoft.java18.gamedoanso.model.Gues;

public class GameStore {
	private List<Player> players;
	private List<GameSession> gameSessions;
	private List<Gues> gues;
	
	GameStore(){
		players = new ArrayList<>();
		gameSessions = new ArrayList<>();
		gues = new ArrayList<>();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<GameSession> getGameSessions() {
		return gameSessions;
	}

	public List<Gues> getGues() {
		return gues;
	}
	
	
}

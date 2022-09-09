package cybersoft.java18.gamedoanso.service;


import cybersoft.java18.gamedoanso.model.GameSession;
import cybersoft.java18.gamedoanso.model.Gues;
import cybersoft.java18.gamedoanso.model.Player;
import cybersoft.java18.gamedoanso.repository.GameSessionRepository;
import cybersoft.java18.gamedoanso.repository.GuessRepository;
import cybersoft.java18.gamedoanso.repository.PlayerRepository;


import java.util.ArrayList;
import java.util.List;


public class GameService {
	private static GameService INSTANCE;
	public static GameService getINSTANCE(){
		if(INSTANCE == null){
			INSTANCE = new GameService();
		}
		return INSTANCE;
	}
	private final GameSessionRepository gameSessionRepository;
	private final GuessRepository guessRepository;
	private final PlayerRepository playerRepository;
	
	private GameService() {
		this.gameSessionRepository = new GameSessionRepository();
		this.guessRepository = new GuessRepository();
		this.playerRepository = new PlayerRepository();
	}
	
	public Player dangNhap(String username, String password) {

		Player player = playerRepository.findByUsername(username);

		if(player == null){
			return null;
		}
		if(!player.getPassword().equals(password)){
			return null;
		}
		return player;
	}
	
	public Player dangKy(String username, String password, String name) {
		if(!isVaild(username, password, name)) {
			return null;
		}

		boolean exist = playerRepository.existedByUsername(username);

		if(exist) {
			return null;
		}
		
		Player player = new Player(username, password, name);
		playerRepository.save(player);
		
		return player; 
						
	}
	
	private boolean isVaild(String username, String password, String name) {
		if(username == null || "".equals(username.trim())) {
			return false;
		}
		
		if(password == null || "".equals(password)) {
			return false;
		}
		
		if(name == null || "".equals(name)) {
			return false;
		}
		
		return true;
	}

	public GameSession getCurrentUsername(String username) {
		List<GameSession> gameSessions = gameSessionRepository.findByUsername(username);
//		Supplier<GameSession>
//		  th = () -> {
//			return createGame(username);
//		  };

		GameSession activeGame = gameSessions.isEmpty()
				? createGame(username)
				: gameSessions.stream().filter(p -> p.getIsActive())
				.findFirst().orElseGet(() -> createGame(username));
		activeGame.setGues(guessRepository.findBySession(activeGame.getId()));
		return activeGame;


	}

	private GameSession createGame(String username) {
		GameSession gameSession = new GameSession(username);
		gameSession.setActive(true);
		gameSessionRepository.deactivateAllGames(username);

		gameSessionRepository.save(gameSession);
		return gameSession;
	}

	public void saveGuess(Gues gues) {
		guessRepository.save(gues);
	}

	public GameSession getGameSession(String gameSession_id) {
		return gameSessionRepository.findGameSessionById(gameSession_id);
	}

	public void Completed(String gameSession_id) {
		gameSessionRepository.gameCompleted(gameSession_id);
	}

	public void newGame(String username) {
		createGame(username);
	}

	public void endTimeGame(String gameSession_id) {
		gameSessionRepository.endTimeToGame(gameSession_id);
	}

	public ArrayList<GameSession> ratingAllPlayer() {
		return gameSessionRepository.ratingPlayer();
	}
}

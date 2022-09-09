package cybersoft.java18.gamedoanso.servlet;

import cybersoft.java18.gamedoanso.model.GameSession;
import cybersoft.java18.gamedoanso.model.Gues;
import cybersoft.java18.gamedoanso.model.Player;
import cybersoft.java18.gamedoanso.service.GameService;
import cybersoft.java18.gamedoanso.utils.JspUtils;
import cybersoft.java18.gamedoanso.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


@WebServlet(name = "gameServlet", urlPatterns = {
        UrlUtils.GAME,
        UrlUtils.XEP_HANG,
        UrlUtils.NEW_GAME,
        UrlUtils.ROOT
})
public class GameServlet extends HttpServlet {
    private GameService gameService;

    @Override
    public void init() throws ServletException {
        super.init();
        gameService = GameService.getINSTANCE();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtils.GAME:
            case UrlUtils.NEW_GAME: {
                Player player = (Player) req.getSession().getAttribute("player");
                GameSession gameSession = gameService.getCurrentUsername(player.getUsername());

                req.setAttribute("game", gameSession);
                req.getRequestDispatcher(JspUtils.GAME).forward(req, resp);

                break;
            }
            case UrlUtils.XEP_HANG: {
                rankPlayer(req, resp);
                req.getRequestDispatcher(JspUtils.XEP_HANG).forward(req, resp);
                break;
            }
            case UrlUtils.ROOT: {
                Player player = (Player) req.getSession().getAttribute("player");
                req.getRequestDispatcher(JspUtils.ROOT).forward(req, resp);
                break;
            }
            default: {
                resp.sendRedirect(req.getContextPath() + UrlUtils.ERORR_404);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtils.GAME -> {
                processGame(req, resp);
            }
            case UrlUtils.NEW_GAME -> {
                createNewGame(req, resp);
            }
            default -> {
                resp.sendRedirect(req.getContextPath() + UrlUtils.ERORR_404);
            }
        }
    }

    private void rankPlayer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<GameSession> rank = gameService.ratingAllPlayer();

        Collections.sort(rank, new Comparator<GameSession>() {
            public int compare(GameSession o1, GameSession o2) {
                if (o1.getTotalGuess() < o2.getTotalGuess())
                    return -1;
                if (o1.getTotalGuess() == o2.getTotalGuess())
                    return 0;
                return 1;
            }
        });
        req.setAttribute("rankPlayer", rank);

    }

    private void createNewGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Player playerCurrent = (Player) req.getSession().getAttribute("player");
        gameService.newGame(playerCurrent.getUsername());
        resp.sendRedirect(req.getContextPath() + UrlUtils.GAME);
    }

    private void processGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String gameSession_id = req.getParameter("game-session");
        int guess = Integer.parseInt(req.getParameter("guess"));
        GameSession gameSession = gameService.getGameSession(gameSession_id);

        if (gameSession == null) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + UrlUtils.DANG_NHAP);
            return;
        }

        if (gameSession.getTarget() == guess) {
            gameService.Completed(gameSession_id);
            gameService.endTimeGame(gameSession_id);
        }
        gameSession.getGues().add(createGuess(gameSession, guess));
        resp.sendRedirect(req.getContextPath() + UrlUtils.GAME);
    }

    private Gues createGuess(GameSession gameSession, int guessNumber) {
        int result = Integer.compare(guessNumber, gameSession.getTarget()); // Integer.compare(a,b); a>b=1, a<b=-1; a==b=0
        Gues newGuess = new Gues(guessNumber, gameSession.getId(), result);
        gameService.saveGuess(newGuess);
        return newGuess;
    }
}

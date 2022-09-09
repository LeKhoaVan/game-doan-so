package cybersoft.java18.gamedoanso.filter;

import cybersoft.java18.gamedoanso.model.Player;
import cybersoft.java18.gamedoanso.utils.UrlUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = UrlUtils.ALL)
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if(isLogin(req) || isAuthUrl(req)){
            chain.doFilter(req, resp);
        }
        else {
            resp.sendRedirect(req.getContextPath() + UrlUtils.DANG_NHAP);
        }
    }

    private boolean isAuthUrl(HttpServletRequest req) {
        if(req.getServletPath().startsWith(UrlUtils.DANG_KY) || req.getServletPath().startsWith(UrlUtils.DANG_NHAP)){
            return true;
        }
        return false;
    }

    private boolean isLogin(HttpServletRequest req) {
        Player currentPlayer = (Player) req.getSession().getAttribute("player");
        return currentPlayer != null;
    }
}

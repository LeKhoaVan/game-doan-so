package cybersoft.java18.gamedoanso.servlet;

import cybersoft.java18.gamedoanso.utils.JspUtils;
import cybersoft.java18.gamedoanso.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name= "errorServlet" , urlPatterns = {
        UrlUtils.ERORR_404,
        UrlUtils.ERORR_500
})
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch(req.getServletPath()){
            case UrlUtils.ERORR_404 : {
                req.getRequestDispatcher(JspUtils.ERORR_404).forward(req, resp);
            }
            case UrlUtils.ERORR_500 : {
                req.getRequestDispatcher(JspUtils.ERORR_500).forward(req, resp);
            }
            default: {
                resp.sendRedirect(req.getServletPath() + JspUtils.ERORR_404);
            }
        }
    }
}

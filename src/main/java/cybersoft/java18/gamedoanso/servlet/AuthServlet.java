package cybersoft.java18.gamedoanso.servlet;

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

@WebServlet(name= "authServlet", urlPatterns = {
        UrlUtils.DANG_KY,
        UrlUtils.DANG_NHAP,
        UrlUtils.DANG_XUAT
})
public class AuthServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              switch (req.getServletPath()){
                  case UrlUtils.DANG_KY : {
                      req.getRequestDispatcher(JspUtils.DANG_KY).forward(req,resp);
                      break;
                  }
                  case UrlUtils.DANG_NHAP: {
                      req.getRequestDispatcher(JspUtils.DANG_NHAP).forward(req,resp);
                      break;
                  }
                  case UrlUtils.DANG_XUAT: {
                      req.getSession().invalidate(); // hủy tất cả các session đã tạo
                      resp.sendRedirect(req.getContextPath() + UrlUtils.DANG_NHAP);
                      break;
                  }
                  default: {
                      resp.sendRedirect(req.getContextPath() + UrlUtils.ERORR_404 );
                  }
              } 
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch(req.getServletPath()){
            case UrlUtils.DANG_KY : {
                processRegister(req, resp);
                break;
            }
            case UrlUtils.DANG_NHAP: {
                processLogin(req, resp);
                break;
            }
            default: {
                resp.sendRedirect(req.getContextPath() + UrlUtils.ERORR_404 );
            }
        }
    }

    private void processLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            Player player = GameService.getINSTANCE().dangNhap(username, password);

            if(player == null) {
                req.setAttribute("error", "thông tin đăng nhập chưa chính xác");
                this.doGet(req, resp);
            }
            else {
                req.getSession().setAttribute("player", player);
                resp.sendRedirect(req.getContextPath() + UrlUtils.GAME);
            }

    }

    private void processRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        Player player = GameService.getINSTANCE().dangKy(username,password,name);
        if(player != null){
            req.getSession().setAttribute("player", player);
            resp.sendRedirect(req.getContextPath() + UrlUtils.GAME);
        }
        else {
            req.setAttribute("error", "Thông tin người dùng đã được sử dụng hoặc thông tin không hợp lệ");
            doGet(req, resp);
        }
    }

}

package kr.magazin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;

public class AdminMagazinHeadline implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String mg_board_num_param = request.getParameter("mg_board_num");
        int mg_board_num = (mg_board_num_param != null && !mg_board_num_param.isEmpty()) ? Integer.parseInt(mg_board_num_param) : 0;

        // 현재 상태를 반대로 설정
        MagazinDAO dao = MagazinDAO.getInstance();
        dao.updateHeadline(mg_board_num);

        // check 속성을 request에 추가
        request.setAttribute("check", true);

        return "/WEB-INF/views/magazin/adminHeadline.jsp";
    }
}
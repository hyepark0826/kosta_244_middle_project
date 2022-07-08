import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.sql.MyConnection;


@WebServlet("/boardlist")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");//ISO_88859_1
		PrintWriter out=response.getWriter();//응답출력스트림 얻기
		HttpSession session=request.getSession();

        String boardNo=request.getParameter("boardNo");

        //DB와의 연결
		Connection con=null;
		//SQL송신
		PreparedStatement pstmt=null;
		//응답결과
		ResultSet rs=null;
		String result=null;
		System.out.println(boardNo);
		
		try {
			con=MyConnection.getConnection();
			String selectBoardSQL="SELECT * FROM board WHERE board_no=?";
			pstmt=con.prepareStatement(selectBoardSQL);
			pstmt.setString(1, boardNo);
			rs=pstmt.executeQuery();
			if(rs.next()) {//전달받은 글번호와 일치하는 DB 데이터가 있을 경우에는
                //공지사항 상세 보기 페이지로 이동하고 
                //DB에서 해당 글의 정보를 상세보기 페이지에 채워 넣어준다
				response.sendRedirect("http://localhost:8888/front/project_html/noticeView.html");
                //?.... 해당 페이지로 이동한 다음에 
                //어떻게 상세페이지내용을 채워주지....
		    }else {//입력한 정보와 일치하는 게시글이 없으면
				result="{\"status\":0}";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MyConnection.close(rs,pstmt,con);
		}
		
		out.print(result);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
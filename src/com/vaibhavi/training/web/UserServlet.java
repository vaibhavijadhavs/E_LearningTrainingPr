package com.vaibhavi.training.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaibhavi.training.dao.AdminLoginDao;
import com.vaibhavi.training.dao.CourseDao;
import com.vaibhavi.training.dao.EnrollDao;
import com.vaibhavi.training.dao.UserDao;
import com.vaibhavi.training.model.AdminLoginBean;
import com.vaibhavi.training.model.Course;
import com.vaibhavi.training.model.Enroll;
import com.vaibhavi.training.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDAO; 
    private CourseDao courseDAO;
    private EnrollDao enrollDAO;
    
    public UserServlet() {
    	this.userDAO=new UserDao();
    	this.courseDAO=new CourseDao();
    	this.enrollDAO=new EnrollDao();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, 
	IOException {
	
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/adminLogin":
				showAdminForm(request,response);
				break;
			case "/loginClick":
				afterAdminLogin(request,response);
				break;
			case "/regUserList":
				showRegUserList(request,response);
				break;
			case "/userLoginClick":
				afterUserLogin(request,response);
				break;
			
			case "/insertCourse":
				insertCourse(request,response);
				break;
			case "/newCourse":
				showNewCourseForm(request,response);
				break;
			case "/enroll":
				showEnroll(request,response);
				break;
			case "/updateCourse":
				updateCourse(request,response);
				break;
			case "/enrollinsert":
				insertEnrollInfo(request,response);
				break;
			case "/enrollSuccess":
				enrollSuccess(request,response);
				break;
				
			default:
				listCourse(request, response);
				break;
			}
		}
			catch (SQLException ex) {
				throw new ServletException(ex);
			}
			
		
		
	}
	
	
	private void listCourse(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Course> listUser = courseDAO.selectAllCourse();
		System.out.println("1:"+listUser.size());
		request.setAttribute("listUser", listUser);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("userList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showRegUserList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("RegUserList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void enrollSuccess(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("enrollSuccess.jsp");
		dispatcher.forward(request, response);
	}
	

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("userForm.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showEnroll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("enroll.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showNewCourseForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("AddNewCourse.jsp");
		dispatcher.forward(request, response);
	}
	private void showAdminForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp");
		dispatcher.forward(request, response);
	}
	private void afterAdminLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		AdminLoginDao loginDao = new AdminLoginDao();
		
		String username = request.getParameter("email");
		String password = request.getParameter("pwd");
		AdminLoginBean loginBean = new AdminLoginBean();
		loginBean.setUsername(username);
		loginBean.setPassword(password);
		
		if (loginDao.validate(loginBean))
		{
			response.sendRedirect("adminloginSuccess.jsp");
			
		}
		else 
		{
			//HttpSession session = request.getSession();
			response.sendRedirect("error.jsp");
			
		}
		
	}
	
	
	private void afterUserLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("userLogin.jsp");
		dispatcher.forward(request, response);
	}
	
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		
		//user_id, name, phone_no, email,address,reg_date,password,upload_photo
		int id=Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		Long phn_No = Long.parseLong(request.getParameter("phone_no"));
		String email = request.getParameter("email");
		String addrs= request.getParameter("address");
		String regDATE = request.getParameter("reg_date");
		String pwd= request.getParameter("password");
		String photo= request.getParameter("upload_photo");
		
		
		User newUser = new User(id,name, phn_No,email, addrs,regDATE,pwd,photo);
		userDAO.insertUser(newUser);
		response.sendRedirect("list");
	}
	private void updateCourse(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		
		//user_id, name, phone_no, email,address,reg_date,password,upload_photo
		int id=0;
		String name = request.getParameter("name");
		Long phn_No = Long.parseLong(request.getParameter("phone_no"));
		String email = request.getParameter("email");
		String addrs= request.getParameter("address");
		String regDATE = request.getParameter("reg_date");
		String pwd= request.getParameter("password");
		String photo= request.getParameter("upload_photo");
		
		
		User newUser = new User(id,name, phn_No,email, addrs,regDATE,pwd,photo);
		userDAO.insertUser(newUser);
		response.sendRedirect("list");
	}

	
	
	private void insertCourse(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		
		//user_id, name, phone_no, email,address,reg_date,password,upload_photo
		int id=Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("nm");
		String desc = request.getParameter("des");
		String fees= request.getParameter("fee");
		String resource = request.getParameter("rsrc");
		
		System.out.println("name:"+name);
		
		Course newCourse = new Course(id,name, desc,fees, resource);
		courseDAO.insertUser(newCourse);
		response.sendRedirect("list");
	}

	private void insertEnrollInfo (HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		
		//user_id, name, phone_no, email,address,reg_date,password,upload_photo
		int Uid=Integer.parseInt(request.getParameter("Uid"));
		String password = request.getParameter("pwd");
		int Cid = Integer.parseInt(request.getParameter("Cid"));
		String mail= request.getParameter("mail");
		String payment = request.getParameter("payment");
		
		System.out.println("name:"+Uid);
		
		Enroll newEnroll = new Enroll(Uid, password, Cid, mail, payment);
		enrollDAO.insertEnroll(newEnroll);
		response.sendRedirect("enrollSuccess");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

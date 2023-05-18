package kuit.springbasic.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.web.dao.UserDao;
import kuit.springbasic.web.domain.User;
import kuit.springbasic.web.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

import static kuit.springbasic.config.Constant.USER_SESSION_KEY;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserDao userDao;


    @RequestMapping("/form")
    public String ForwardController() {
        return "/user/form";
    }


    @RequestMapping("/signup")
    public String CreateUserController(@ModelAttribute User user, HttpServletRequest request) throws SQLException {

        userDao.insert(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/updateForm")
    public String UpdateUserFormController(@RequestParam String userId, HttpServletRequest request) throws SQLException {
        User user = userDao.findByUserId(userId);
        if (user != null){
            request.setAttribute("user",user);
            return "/user/updateForm";

        }
        return"redirect:/";
    }

    @RequestMapping("/update")
    public String UpdateUserController(@ModelAttribute User user, HttpServletRequest request){
        userDao.update(user);
        return "redirect:/user/list";

    }

    @RequestMapping("/list")
    public String ListUserController(@ModelAttribute String userId, HttpServletRequest request) throws SQLException{
        if (UserSessionUtils.isLoggedIn(request.getSession())){
            request.setAttribute("users",userDao.findAll());
            return "/user/list";
        }
        return"redirect:/user/loginForm";
    }

}

package com.hubu.controller;


import cn.dsna.util.images.ValidateCode;
import com.hubu.pojo.Role;
import com.hubu.pojo.User;
import com.hubu.mapper.RoleMapper;
import com.hubu.mapper.UserMapper;
import com.hubu.service.RoleService;
import com.hubu.service.UserService;
import com.github.pagehelper.PageInfo;
import com.hubu.utils.MD5Util;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {


    @Resource
    private UserService userService;


    @Resource
    private RoleService roleService;



    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;


    //登录功能
    @RequestMapping("/login")
    public String login(@Param("username") String username, @Param("userpassword") String userpassword, String imgCode, HttpSession session) {
        //获取图片中的验证码
        String code = (String) session.getAttribute("code");

        //判断验证码是否一致
        //不区分大小写
        if(imgCode.equalsIgnoreCase(code))
        {
            User user = userService.login(username,userpassword);
            if(user!=null){
                session.setAttribute("username",username);
                session.setAttribute("user",user);
                if(user.getUserrole()==1)
                {
                    return "welcome";
                }
                else if(user.getUserrole()==2)
                {
                    return "welcome1";
                }
                else
                {
                    return "welcome2";
                }
            }
            else{
                return "login";
            }
        }
        else{
            return "login";
        }

    }


    //验证码
    @RequestMapping("/imgCode")
    public void imgCode(HttpServletResponse response,HttpSession session){
        ValidateCode validateCode=new ValidateCode(100,40,4,15);
        String code= validateCode.getCode();
        session.setAttribute("code",code);
        try {
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //用户管理(查询功能)
    @RequestMapping("/showUser")
    public String shooList(Model model,@RequestParam(defaultValue = "1") int pageNum,String condition){
        //先查询数据
        PageInfo<User> pageInfo = userService.showUser(pageNum,condition);

        model.addAttribute("condition", condition);

        //利用model将集合数据存储起来
        model.addAttribute("pageInfo",pageInfo);
        return "userList";
    }


    //返回登录页面
    @RequestMapping("/index")
    public String index(){
        return "login";
    }



    //密码修改跳转界面
    @RequestMapping("/password")
    public String password(HttpSession session)
    {
        User user =(User) session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "password";
        }
        else if(user.getUserrole()==2)
        {
            return "password1";
        }
        else
        {
            return "password2";
        }
    }



    //密码修改
    @RequestMapping("/changepassword")
    public String changepassword(String oldPassword,String newPassword,String reNewPassword,HttpSession session){
        User user = (User) session.getAttribute("user");
        String pwd=user.getUserpassword();

        if(!Objects.equals(pwd, oldPassword))
        {
            return "login";            //旧密码输入错误,则返回登录页面
        }
        else
        {
            if(!Objects.equals(newPassword, reNewPassword))
            {
                //新密码两次不相同,则返回设置密码界面,重新设置
                if(user.getUserrole()==1)
                {
                    return "password";
                }
                else if(user.getUserrole()==2)
                {
                    return "password1";
                }
                else
                {
                    return "password2";
                }
            }
            else
            {
                User u=new User();
                u.setId(user.getId());
                u.setUserpassword(newPassword);
                int i=userService.updatepwd(u);
                System.out.println(i);
                //密码更新成功则返回欢迎页面
                if(user.getUserrole()==1)
                {
                    return "welcome";
                }
                else if(user.getUserrole()==2)
                {
                    return "welcome1";
                }
                else
                {
                    return "welcome";
                }
            }
        }

    }


    /**
     * 查看功能
     */
    @RequestMapping("/viewUser")
    public String viewUser(Long id,Model model){
        //通过id查询用户信息
        User smbmsUser = userService.viewUser(id);
        //存储数据
        model.addAttribute("user",smbmsUser); return "userView";
    }

    /**
     *添加功能
     */
    @RequestMapping("/add")
    public String add(Model model){
        //查询所有的角色名称跳转到页面
        List<Role> smbmsRoles = roleService.showRole();
        model.addAttribute("roles",smbmsRoles);
        return "userAdd";
    }

    @RequestMapping("/addUser")
    public String addUser(User user){
        //System.out.println("====================="+smbmsUser);
        int i = userService.addUser(user);
        if(i>0){
            //增加成功
            return "redirect:/showUser";
        }else{
            return "userAdd";
        }

    }

    @RequestMapping("/updateView")
    public String update(Model model,Long id){
        //查询所有的角色名称跳转到页面
        List<Role> roles = roleService.showRole();
        model.addAttribute("roles",roles);
        //通过id查询用户信息，并填充到文本框中
        User smbmsUser =userService.viewUser(id);
        //存储数据
        model.addAttribute("users",smbmsUser);
        return "userUpdate";
    }

    //修改功能
    @RequestMapping("/updateUser")
    public String updateUser(User user){
        int i = userService.updateUser(user);
        if(i>0){
            //修改成功
            return "redirect:/showUser";
        }
        else
        {
            return "/userUpdate";
        }

    }

    //删除功能
    @RequestMapping("/deleteUser")
    public String deleteUser(Long id){
        int i = userService.deleteUser(id);
        if(i>0){
            //删除成功
            return "redirect:/showUser";
        }else{
            return "error";
        }

    }


    //跳转注册页面
    @RequestMapping(value = "/toreg")
    public String Toregister()
    {
        return "register";
    }

    //注册
    @RequestMapping("/register")
    public String register(User user)
    {
        int register = userService.register(user);
        if(register>0)
        {
            return "login";
        }
        else
        {
            return "register";
        }
    }

}
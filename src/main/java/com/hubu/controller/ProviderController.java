package com.hubu.controller;

import com.hubu.pojo.Provider;
import com.hubu.pojo.User;
import com.hubu.service.ProviderService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class ProviderController {
    @Resource
    private ProviderService providerService;
    //查询
    @RequestMapping("/showProvider")
    public String providerList(Model model, @RequestParam(defaultValue ="1") int pageNum, String name, HttpSession session){
        PageInfo<Provider> pageInfo = providerService.showProvider(pageNum,name);

        //把集合数据存储起来
        model.addAttribute("name",name);
        model.addAttribute("pageInfo",pageInfo);
        User user =(User) session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "providerList";
        }
        else
        {
            return "providerList1";
        }
    }
    //查看功能
    @RequestMapping("/viewProvider")
    public String viewUser(Long id,Model model,HttpSession session){
        //通过id查询用户信息
        Provider Provider=providerService.viewProvider(id);

        //存储数据
        model.addAttribute("user",Provider);
        User user=(User) session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "providerView";
        }
        else
        {
            return "providerView1";
        }
    }

    @RequestMapping("/addView")
    public String add(Model model,Long id,HttpSession session){
        User user=(User) session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "providerAdd";
        }
        else
        {
            return "providerAdd1";
        }
    }

    @RequestMapping("/addProvider")
    public String addProvider(Provider Provider,HttpSession session){

        System.out.println("====================="+Provider);
        int i = providerService.addProvider(Provider);
        User user=(User)session.getAttribute("user");
        if(i>0)
        {
                //增加成功
                return "redirect:/showProvider";
        }
        else {
            if(user.getUserrole()==1)
            {
                return "providerAdd";
            }
            else
            {
                return "providerAdd1";
            }
        }

    }

    @RequestMapping("/UpdateView")
    public String update(Model model,Long id,HttpSession session)
    {
        //通过id查询用户信息，并填充到文本框中
        Provider provider=providerService.viewProvider(id);

        //存储数据
        model.addAttribute("users",provider);
        User user=(User)session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "providerUpdate";
        }
        else
        {
            return "providerUpdate1";
        }
    }

    //修改功能
    @RequestMapping("/updateProvider")
    public String updateProvider(Provider smbmsProvider,HttpSession session){
        int i = providerService.updateProvider(smbmsProvider);
        User user=(User) session.getAttribute("user");
        if(i>0){
            //修改成功
            return "redirect:/showProvider";
        }else{
            if(user.getUserrole()==1)
            {
                return "updateProvider";
            }
            else
            {
                return "updateProvider1";
            }
        }
    }

    //删除功能
    @RequestMapping("/deleteProvider")
    public String deleteProvider(Long id){
        int i = providerService.deleteProvider(id);
        if(i>0){
            //删除成功
            return "redirect:/showProvider";
        }else{
            return "error";
        }
    }
}

package com.hubu.controller;

import com.hubu.pojo.Bill;
import com.hubu.pojo.Provider;
import com.hubu.pojo.User;
import com.hubu.service.BillService;
import com.hubu.service.ProviderService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BillController {


    @Resource
    private BillService billService;


    @Resource
    private ProviderService providerService;

    @RequestMapping("/showbill")
    public String showbill(@RequestParam(defaultValue = "1") int pageNum, Model model, String productname, Long providerid, Integer ispayment, HttpSession session) {
        PageInfo<Bill> pageInfo = billService.showbill(pageNum, productname, providerid, ispayment);

        model.addAttribute("productname", productname);
        model.addAttribute("providerid", providerid);
        model.addAttribute("ispayment", ispayment);

        model.addAttribute("pageinfo", pageInfo);

        List<Provider> provider = providerService.showallpro();


        model.addAttribute("providers", provider);

        User user=(User) session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "billList";
        }
        else if(user.getUserrole()==2)
        {
            return "billList1";
        }
        else
        {
            return "billList2";
        }
    }


    //查看订单信息
    @RequestMapping("/billview")
    public String billview(Long id, Model model,HttpSession session) {
        Bill billview = billService.billview(id);
        model.addAttribute("bill", billview);
        User user=(User) session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "billView";
        }
        else if(user.getUserrole()==2)
        {
            return "billView1";
        }
        else
        {
            return "billView2";
        }
    }


    //跳转修改订单信息
    @RequestMapping("/billupdateshow")
    public String billupdateshow(Long id, Model model,HttpSession session) {
        //查询订单信息
        Bill billview = billService.billview(id);
        model.addAttribute("bill", billview);
        User user=(User) session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "billUpdate";
        }
        else if(user.getUserrole()==2)
        {
            return "billUpdate1";
        }
        else
        {
            return "billUpdate2";
        }
    }


    //修改订单信息
    @RequestMapping("/billupdate")
    public String billupdate(Bill bill,HttpSession session) {
        int i = billService.billupdate(bill);
        User user=(User) session.getAttribute("user");
        if (i > 0) {
            return "redirect:/showbill";
        } else {
            if(user.getUserrole()==1)
            {
                return "billUpdate";
            }
            else if(user.getUserrole()==2)
            {
                return "billUpdate1";
            }
            else
            {
                return "billUpdate2";
            }
        }
    }


    //删除订单信息
    @RequestMapping("/billdelete")
    public String billdelete(Long id) {
        int i = billService.billdelete(id);
        if (i > 0) {
            return "redirect:/showbill";
        } else {
            return "error";
        }
    }

    //跳转到添加订单界面
    @RequestMapping("/billaddview")
    public String billaddview(Model model,HttpSession session) {
        //查询所有供应商的所有信息
        List<Provider> showallpro = providerService.showallpro();
        model.addAttribute("providers", showallpro);
        User user=(User) session.getAttribute("user");
        if(user.getUserrole()==1)
        {
            return "billAdd";
        }
        else if(user.getUserrole()==2)
        {
            return "billAdd1";
        }
        else
        {
            return "billAdd2";
        }
    }

    //添加订单保存
    @RequestMapping("/billadd")
    public String billadd(Bill bill) {
        int i = billService.addbill(bill);
        if (i > 0) {
            return "redirect:/showbill";
        } else {
            return "error";
        }
    }
}

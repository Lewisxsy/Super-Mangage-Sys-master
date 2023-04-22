package com.hubu.service;

import com.hubu.pojo.Bill;
import com.hubu.mapper.BillMapper;
import com.hubu.mapper.ProviderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BillService {
    @Resource
    private BillMapper billMapper;

    @Resource
    private ProviderService providerService;


    @Resource
    private ProviderMapper providerMapper;

    //查询订单并分页
    public PageInfo<Bill> showbill(int pageNum, String productname, Long providerid, Integer ispayment) {


        //当前页号,页面容量
        PageHelper.startPage(pageNum, 3);


        Example example = new Example(Bill.class);
        Example.Criteria criteria = example.createCriteria();

        if (productname != null) {
            criteria.andLike("productname", '%' + productname + '%');
        }

        if (providerid != null && providerid != 0) {
            criteria.andEqualTo("providerid", providerid);
        }

        if (ispayment != null && ispayment != 0) {
            criteria.andEqualTo("ispayment", ispayment);
        }


        //查询数据库
        List<Bill> bills = billMapper.selectByExample(example);

        bindbill(bills);

        PageInfo<Bill> pageInfo = new PageInfo<>(bills);

        return pageInfo;
    }


    //给bill表中的proname赋值(proname不是数据库中的内容,但是查询的时候会用到)
    public void bindbill(List<Bill> bill) {
        for (Bill bills : bill) {
            bills.setProname(providerService.selectpro(bills.getProviderid()).getProname());
        }
    }

    //查看订单信息(通过主键id)
    public Bill billview(Long id) {
        Bill bill = billMapper.selectByPrimaryKey(id);
        bindproname(bill);
        return bill;
    }


    //给bill表中的proname赋值
    public void bindproname(Bill bill) {
        bill.setProname(providerService.selectpro(bill.getProviderid()).getProname());
    }


    //修改订单信息
    public int billupdate(Bill bill) {
        int i = billMapper.updateByPrimaryKeySelective(bill);
        return i;
    }


    //删除订单信息
    public int billdelete(Long id) {
        int i = billMapper.deleteByPrimaryKey(id);
        return i;
    }


    //添加订单信息
    public int addbill(Bill bill) {
        int i = billMapper.insertSelective(bill);
        return i;
    }
}

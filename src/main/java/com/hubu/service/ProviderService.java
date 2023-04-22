package com.hubu.service;

import com.hubu.pojo.Provider;
import com.hubu.mapper.ProviderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ProviderService {
    @Resource
    private ProviderMapper ProviderMapper;


    //通过主键id查找
    public Provider selectpro(Long id) {
        return ProviderMapper.selectByPrimaryKey(id);
    }


    //查询所有供应商信息
    public List<Provider> showallpro() {
        return ProviderMapper.selectAll();
    }


    //查询功能并分页
    public PageInfo<Provider> showProvider(int pageNum, String name) {
        //当前页  页面容量
        PageHelper.startPage(pageNum, 7);
        Example example = new Example(Provider.class);
        Example.Criteria criteria = example.createCriteria();
        //判断
        if (!StringUtils.isEmpty(name)) {
            //通过用户名进行模糊查询
            criteria.andLike("proname",'%'+name+'%');
        }
        //判断


        List<Provider> Providers = ProviderMapper.selectByExample(example);
        //可以得到用户对象中的userRole
        //那么角色表中的id就可以赋值了
        PageInfo<Provider> pageInfo=new PageInfo<Provider>(Providers);
        return pageInfo;
    }

    //通过用户id查询用户信息
    public Provider viewProvider(Long id){
        Provider Provider=ProviderMapper.selectByPrimaryKey(id);

        return Provider;
    }

    //增加
    @Transactional(propagation = Propagation.REQUIRED)
    public int addProvider(Provider smbmsProvider){

        return ProviderMapper.insertSelective(smbmsProvider);
    }

    //修改
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateProvider(Provider Provider){
        return ProviderMapper.updateByPrimaryKeySelective(Provider);
    }

    //删除
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteProvider(Long id){
        return ProviderMapper.deleteByPrimaryKey(id);
    }
}

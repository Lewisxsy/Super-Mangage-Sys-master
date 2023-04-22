package com.hubu.service;

import com.hubu.pojo.User;
import com.hubu.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource RoleService roleService;
    //登录
    public User login(String username, String password){
        User user=new User();
        user.setUsername(username);
        user.setUserpassword(password);
        return userMapper.selectOne(user);
    }

    //查询功能并分页(条件查询+模糊查询)
    public PageInfo<User> showUser(int pageNum,String condition){
        //当前页  页面容量
        PageHelper.startPage(pageNum, 5);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();


        //
        if (condition!=null) {
            criteria.orEqualTo("usercode", condition).orEqualTo("username", condition)
                    .orEqualTo("phone", condition);
        }
        if(condition!=null){
            criteria.orLike("usercode",'%'+condition+'%').orLike("username",'%'+condition+'%')
                    .orLike("phone",'%'+condition+'%');
        }


        List<User> user = userMapper.selectByExample(example);
        //可以得到用户对象中的userRole //那么角色表中的id就可以赋值了
        bindUser(user);
        PageInfo<User> pageInfo=new PageInfo<User>(user);
        return pageInfo;
    }


    public void bindUser(List<User> list)
    {
        for(User user:list)
        {
            user.setRolename(roleService.showRoleName(user.getUserrole()).getRolename());

        }
    }


    //修改密码
    public int updatepwd(User user){

        int i = userMapper.updateByPrimaryKeySelective(user);
        return i;
    }




    //通过用户id查询用户信息
    public User viewUser(Long id){
        User user =userMapper.selectByPrimaryKey(id);
        bindTest(user);
        return user;
    }

    public void bindTest(User smbmsUser){
        smbmsUser.setRolename(roleService.showRoleName(smbmsUser.getUserrole()).getRolename());
    }

    //添加
    @Transactional(propagation = Propagation.REQUIRED)
    public int addUser(User user){
        return userMapper.insertSelective(user);
    }

    //修改
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateUser(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }


    //删除
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteUser(Long id){
        return userMapper.deleteByPrimaryKey(id);
    }



    //注册新用户
    public int register(User user)
    {
        int insert = userMapper.insertSelective(user);
        return insert;
    }

}

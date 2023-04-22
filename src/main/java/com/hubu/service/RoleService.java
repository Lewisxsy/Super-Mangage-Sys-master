package com.hubu.service;

import com.hubu.pojo.Role;
import com.hubu.mapper.RoleMapper;
import org.springframework.stereotype.Service;
;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleMapper RoleMapper;
    //通过id查询角色名称
    public Role showRoleName(Long id){
        return RoleMapper.selectByPrimaryKey(id);
    }
    public List<Role> showRole()
    {
        return RoleMapper.selectAll();
    }


}

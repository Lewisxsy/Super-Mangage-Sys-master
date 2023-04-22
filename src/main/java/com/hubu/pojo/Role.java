package com.hubu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="role")
public class Role {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String rolecode;

    private String rolename;

    private Long createdby;

    private Date creationdate;

    private Long modifyby;

    private Date modifydate;

}
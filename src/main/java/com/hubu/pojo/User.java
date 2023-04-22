package com.hubu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")

public class User {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String usercode;

    private String username;

    private String userpassword;

    private Integer gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String phone;

    private String address;

    private Long userrole;

    private Long createdby;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationdate;

    private Long modifyby;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifydate;


    public int getAge() {
        //计算年龄
        Calendar newDate=Calendar.getInstance();   //当前日期
        Calendar birth=Calendar.getInstance();
        birth.setTime(birthday);            //出生日期
        return newDate.get(Calendar.YEAR)-birth.get(Calendar.YEAR);     //当前年份-出生年份
    }

    //忽略映射(数据库中没有的信息)
    @Transient
    private int age;


    @Transient
    private String rolename;

}
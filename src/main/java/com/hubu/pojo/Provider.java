package com.hubu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="provider")
public class Provider {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String procode;

    private String proname;

    private String prodesc;

    private String procontact;

    private String prophone;

    private String proaddress;

    private String profax;

    private Long createdby;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationdate;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifydate;

    private Long modifyby;

}
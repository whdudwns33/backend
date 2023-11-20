package com.kh.mini.vo.Member;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
public class MemberVO {
    private String id;
    private String pwd;
    private String name;
    private String email;
    private Date join;
}
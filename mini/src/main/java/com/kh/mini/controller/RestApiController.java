package com.kh.mini.controller;
import com.kh.mini.dao.MemberDAO;
import com.kh.mini.vo.Member.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class RestApiController {
    // get 방식 요청
    @GetMapping("/name")
    public String getHello() {
        return "get 방식에 대한 응답";
    }
    @GetMapping("/board/{pageNo}/{commNo}")
    public String getVariable(@PathVariable String pageNo, @PathVariable String commNo) {
        return "GET 방식 : " + pageNo + "/" + commNo;
    }
    @GetMapping("/search")
    public String getReaustParam(@RequestParam String model, @RequestParam String price, @RequestParam String company) {
        return "모델 : " + model + "| 가격 : " + price + "| 제조사 : " + company;
    }
    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> getMemberList() {
        List<MemberVO> list = new ArrayList<>();
        MemberDAO dao = new MemberDAO();
        list = dao.memberSelect("ALL");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/member")
    public ResponseEntity<Boolean> setMember(@RequestBody Map<String, String> regData) {
        // get("id") key값으로 프론트와 동일하게 지정
        String getId = regData.get("id");
        String getPwd = regData.get("pwd");
        String getName = regData.get("name");
        String getEmail = regData.get("email");
        MemberDAO dao = new MemberDAO();
        boolean isReg =dao.memberRegister(getId, getPwd, getName, getEmail);
        return new ResponseEntity<>(isReg, HttpStatus.OK);
    }

}

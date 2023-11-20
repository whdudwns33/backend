package com.kh.mini.controller;

import com.kh.mini.dao.MemberDAO;
import com.kh.mini.vo.Member.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 3000번에서 요청을 8111번으로 하면 브라우저에서 오류를 잡는데, 그 방법을 방지하기 위함
@CrossOrigin(origins = "http://localhost:3000")
// rest 컨트롤러, restfull api임을 명시
@RestController
//
@RequestMapping("/users")
public class MemberController {
    // post 로그인
    @PostMapping("/login")
    // Map <key type, value type>
    public ResponseEntity<Boolean> memberLogin(@RequestBody Map<String, String> loginData) {
        String id = loginData.get("id");
        String pw =loginData.get("pw");
        System.out.println(id);
        System.out.println(pw);
        MemberDAO dao = new MemberDAO();
        boolean rst = dao.loginCheck(id, pw);
        return new ResponseEntity<>(rst, HttpStatus.OK);
    }

    // get: 회원 정보 조회
    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> memberList(@RequestParam String id) {
        System.out.println("id"+ id);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.memberSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}

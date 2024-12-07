package com.ncp.moeego.member.controller;

import com.ncp.moeego.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import com.ncp.moeego.member.bean.JoinDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public ResponseEntity mainP() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/join")
    public ResponseEntity joinProcess(@RequestBody JoinDTO joinDTO) {
        joinDTO.setGender(3);   //추후 수정
        boolean check = memberService.write(joinDTO);
        if(check) return ResponseEntity.ok("ok");
        else return  ResponseEntity.badRequest().body("값이 잘못 되었습니다");
    }
}

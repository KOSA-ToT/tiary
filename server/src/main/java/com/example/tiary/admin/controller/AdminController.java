package com.example.tiary.admin.controller;

import com.example.tiary.admin.service.AdminService;
import com.example.tiary.comment.dto.request.CommentRequestDTO;
import com.example.tiary.users.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
	// 작가 신청
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    // 작가 신청 버튼 눌렀을 때
    @GetMapping("/approving")
    public ResponseEntity approve(@AuthenticationPrincipal UserDto users) {
        return new ResponseEntity<>(adminService.addApproval(users), HttpStatus.OK);
    }
    //작가 리스트 불러올 때
    @GetMapping("/showList")
    public ResponseEntity showList(@AuthenticationPrincipal UserDto users) {
        return new ResponseEntity<>(adminService.listApproval(), HttpStatus.OK);
    }
    //작가 수락할 때
    @PatchMapping("/acceptWriter/{userId}")
    public ResponseEntity acceptWriter(@PathVariable("userId") Long userId, @AuthenticationPrincipal UserDto users) {
        return new ResponseEntity<>(adminService.accept(userId), HttpStatus.OK);
    }
    //작가 거절할 때
    @PatchMapping("/rejectWriter/{userId}")
    public ResponseEntity rejectWriter(@PathVariable("userId") Long userId, @AuthenticationPrincipal UserDto users) {
        return new ResponseEntity<>(adminService.reject(userId), HttpStatus.OK);
    }
    // 공지사항
}

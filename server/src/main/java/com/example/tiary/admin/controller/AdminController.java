package com.example.tiary.admin.controller;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tiary.admin.service.AdminService;
import com.example.tiary.comment.dto.request.CommentRequestDTO;
import com.example.tiary.users.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.tiary.myPage.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {

	private final UserService userService;
    private final AdminService adminService;

	public AdminController(UserService userService, AdminService adminService) {
		this.userService = userService;
        this.adminService = adminService;
	}
	// 작가 신청
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
    //작가 신청중인지 확인할 때
    @GetMapping("/confirmWriter/{userId}")
    public ResponseEntity confirmWriter(@PathVariable("userId") Long userId, @AuthenticationPrincipal UserDto users) {
        return new ResponseEntity<>(adminService.confirm(userId), HttpStatus.OK);
    }
	// 공지사항


}

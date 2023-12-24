package com.example.tiary.admin.controller;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.myPage.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}
	// 작가 신청

	// 공지사항
	@GetMapping("/{userId}/notice")
	public ResponseEntity readNotice(@PathVariable("userId") Long userId, Pageable pageable){
		try{
			Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 4, pageable.getSort());
			return ResponseEntity.ok(userService.showMyArticle(userId,fixedPageable));
		}catch(Exception e){
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("공지사항 조회에 실패했습니다.");
		}
	}
}

package com.example.tiary.comment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.entity.Article;
import com.example.tiary.article.repository.ArticleRepository;
import com.example.tiary.comment.dto.request.CommentRequestDTO;
import com.example.tiary.comment.dto.response.CommentResponseDTO;
import com.example.tiary.comment.entity.Comment;
import com.example.tiary.comment.repository.CommentRepository;
import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.users.entity.Users;
import com.example.tiary.users.repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;
	private final UsersRepository usersRepository;

	public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository,
		UsersRepository usersRepository) {
		this.commentRepository = commentRepository;
		this.articleRepository = articleRepository;
		this.usersRepository = usersRepository;
	}

	// 회원 댓글 등록
	@Transactional
	public CommentResponseDTO create(CommentRequestDTO commentRequestDTO, Long articleId, Long userId) {
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
		Comment comment = commentRequestDTO.toEntityUser(article, users);
		Comment parentComment;
		if (commentRequestDTO.getParentId() != null) { // 부모 댓글이 있을 경우
			parentComment = commentRepository.findById(commentRequestDTO.getParentId())
				.orElseThrow(() -> new EntityNotFoundException(
					new BusinessLogicException(ExceptionCode.PARENT_COMMENTS_NOT_FOUND)));
			comment.updateParent(parentComment);
		}
		return CommentResponseDTO.from(commentRepository.save(comment));
	}

	// 비회원 댓글 등록
	@Transactional
	public CommentResponseDTO guestCreate(CommentRequestDTO commentRequestDTO, Long articleId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
		Comment comment = commentRequestDTO.toEntityGuest(article);
		Comment parentComment;
		if (commentRequestDTO.getParentId() != null) {    // 부모 댓글이 있을 경우
			parentComment = commentRepository.findById(commentRequestDTO.getParentId())
				.orElseThrow(() -> new EntityNotFoundException(
					new BusinessLogicException(ExceptionCode.PARENT_COMMENTS_NOT_FOUND)));
			comment.updateParent(parentComment);
		}
		return CommentResponseDTO.from(commentRepository.save(comment));
	}

	// 전체 댓글
	@Transactional(readOnly = true) // 직관적으로 이거는 조회하는 메서드임을 알려줌
	public List<CommentResponseDTO> readCommentList(Long articleId) {
		List<Comment> commentList = commentRepository.findAllByArticleIdOrderByParentIdAscCreatedAtAsc(articleId);
		List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
		// 댓글의 아이디를 키로 사용하는 맵
		Map<Long, CommentResponseDTO> commentResponseDTOMap = new HashMap<>();
		commentList.forEach(comment -> {
			CommentResponseDTO commentResponseDTO = new CommentResponseDTO().from(comment);
			commentResponseDTOMap.put(commentResponseDTO.getId(), commentResponseDTO);

			// 프로필 이미지 URL 추가
			if (comment.getUsers() != null) {
				commentResponseDTO.setUserProfileImageUrl(comment.getUsers().getUserPicture());
				commentResponseDTO.setUserId(comment.getUsers().getId());
			}

			// 부모 댓글이 있는 경우(부모 댓글 id를 가진 댓글 children에 넣음
			if (comment.getParent() != null) {
				CommentResponseDTO parentDTO = commentResponseDTOMap.get(comment.getParent().getId());
				if (parentDTO.getChildren() == null) {    // 부모 댓글의 children 리스트가 null인 경우 새로운 리스트를 할당
					parentDTO.setChildren(new ArrayList<>());
				}
				parentDTO.getChildren().add(commentResponseDTO);
			} else { // 최상위 부모 댓글의 경우
				commentResponseDTOList.add(commentResponseDTO);
			}
		});
		return commentResponseDTOList;
	}

	// 익명 댓글 비밀번호 확인
	public boolean confirmPassword(Long commentId, String password) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENTS_NOT_FOUND));
		return comment.getPassword().equals(password);
	}

	// 회원 수정
	@Transactional
	public CommentResponseDTO update(Long commentId, CommentRequestDTO commentRequestDTO, Long articleId, Long userId) {
		Users users
			= usersRepository.findById(userId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENTS_NOT_FOUND));

		if(!Objects.equals(userId, comment.getUsers().getId()))
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);

		Optional.ofNullable(commentRequestDTO.getContent()).ifPresent(comment::updateContent);
		return CommentResponseDTO.from(commentRepository.save(comment));
	}

	// 비회원 수정
	@Transactional
	public CommentResponseDTO guestUpdate(Long commentId, Long articleId, CommentRequestDTO commentRequestDTO) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENTS_NOT_FOUND));

		Optional.ofNullable(commentRequestDTO.getContent()).ifPresent(comment::updateContent);
		return CommentResponseDTO.from(commentRepository.save(comment));
	}

	// 회원 댓글 삭제
	@Transactional
	public String delete(Long commentId, Long articleId, Long userId) {
		Users users
			= usersRepository.findById(userId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
		commentRepository.deleteById(commentId);
		return "회원 댓글 삭제 완료";
	}

	// 비회원 삭제
	@Transactional
	public String guestDelete(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENTS_ALREADY_DELETE));
		commentRepository.deleteById(commentId);
		return "비회원 댓글 삭제 완료";
	}
}

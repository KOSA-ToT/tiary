package com.example.tiary.admin.repository;

import com.example.tiary.admin.entity.WriteApproval;
import com.example.tiary.article.entity.Article;
import com.example.tiary.myPage.entity.Subscribe;
import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.users.entity.Users;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<WriteApproval, Long> {
    List<WriteApproval> findAll();
}

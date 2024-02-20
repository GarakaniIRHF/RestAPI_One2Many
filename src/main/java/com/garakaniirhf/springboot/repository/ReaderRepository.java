package com.garakaniirhf.springboot.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garakaniirhf.springboot.model.*;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
  List<Reader> findByBookId(Long bookId);
  
  @Transactional
  void deleteByBookId(long bookId);
}

package com.garakaniirhf.springboot.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.garakaniirhf.springboot.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findByRated(boolean rated);

  List<Book> findByCaptionContaining(String caption);
}

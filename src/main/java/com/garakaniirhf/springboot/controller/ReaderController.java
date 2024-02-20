package com.garakaniirhf.springboot.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garakaniirhf.springboot.exception.ResourceNotFoundException;
import com.garakaniirhf.springboot.model.Reader;
import com.garakaniirhf.springboot.repository.BookRepository;
import com.garakaniirhf.springboot.repository.ReaderRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ReaderController {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private ReaderRepository readerRepository;

  @GetMapping("/books/{bookId}/readers")
  public ResponseEntity<List<Reader>> getAllReadersByBookId(@PathVariable(value = "bookId") Long bookId) {
    if (!bookRepository.existsById(bookId)) {
      throw new ResourceNotFoundException("Not found Book with id = " + bookId);
    }

    List<Reader> readers = readerRepository.findByBookId(bookId);
    return new ResponseEntity<>(readers, HttpStatus.OK);
  }

  @GetMapping("/readers/{id}")
  public ResponseEntity<Reader> getReadersByBookId(@PathVariable(value = "id") Long id) {
    Reader reader = readerRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Reader with id = " + id));

    return new ResponseEntity<>(reader, HttpStatus.OK);
  }

  @PostMapping("/books/{bookId}/readers")
  public ResponseEntity<Reader> createReader(@PathVariable(value = "bookId") Long bookId,
      @RequestBody Reader readerRequest) {
    Reader reader = bookRepository.findById(bookId).map(book -> {
      readerRequest.setBook(book);
      return readerRepository.save(readerRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

    return new ResponseEntity<>(reader, HttpStatus.CREATED);
  }

  @PutMapping("/readers/{id}")
  public ResponseEntity<Reader> updateReader(@PathVariable("id") long id, @RequestBody Reader readerRequest) {
    Reader reader = readerRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("ReaderId " + id + "not found"));

    reader.setContent(readerRequest.getContent());

    return new ResponseEntity<>(readerRepository.save(reader), HttpStatus.OK);
  }

  @DeleteMapping("/readers/{id}")
  public ResponseEntity<HttpStatus> deleteReader(@PathVariable("id") long id) {
    readerRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/books/{bookId}/readers")
  public ResponseEntity<List<Reader>> deleteAllReadersOfBook(@PathVariable(value = "bookId") Long bookId) {
    if (!bookRepository.existsById(bookId)) {
      throw new ResourceNotFoundException("Not found Book with id = " + bookId);
    }

    readerRepository.deleteByBookId(bookId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}


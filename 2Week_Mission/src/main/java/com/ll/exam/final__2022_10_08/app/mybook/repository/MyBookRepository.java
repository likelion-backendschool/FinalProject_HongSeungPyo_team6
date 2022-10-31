package com.ll.exam.final__2022_10_08.app.mybook.repository;

import com.ll.exam.final__2022_10_08.app.mybook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook,Long> {
}

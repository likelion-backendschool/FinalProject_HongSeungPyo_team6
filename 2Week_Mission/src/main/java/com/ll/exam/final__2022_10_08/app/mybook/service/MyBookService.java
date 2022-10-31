package com.ll.exam.final__2022_10_08.app.mybook.service;

import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.mybook.entity.MyBook;
import com.ll.exam.final__2022_10_08.app.mybook.repository.MyBookRepository;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBookService {
    private final MyBookRepository myBookRepository;

    @Transactional
    public void create(Member member, Product product) {
        MyBook myBook = MyBook.builder()
                            .member(member)
                            .product(product)
                            .build();
        myBookRepository.save(myBook);
    }
}

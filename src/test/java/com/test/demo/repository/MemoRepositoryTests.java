package com.test.demo.repository;

import com.test.demo.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

/*    @Commit
    @Transactional
    @Test*/
    public void testDeleteQueryMethod() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    public void testQueryMethodWithPageable() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    public void testQueryMethods() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }


    @Test
    public void testPageDefault(){

        //1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);

        System.out.println("------------------------------------------------");

        System.out.println("Total Pages: " + result.getTotalPages());

        System.out.println("Total Count: " + result.getTotalElements());

        System.out.println("Page Number: " + result.getNumber());

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }

    }

//  getOne Deprecated
    public void testSelect2(){
        Long mno = 10L;

        Memo memo = memoRepository.getOne(mno);
        System.out.println("===============================");

        System.out.println(memo);


    }


    public void testSelect1(){
        Long mno = 10L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("===============================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }


    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder().memoText("Sample...." + i).build();
            memoRepository.save(memo);
        });
    }

    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }


}

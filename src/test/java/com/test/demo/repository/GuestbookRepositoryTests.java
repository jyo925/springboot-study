package com.test.demo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.test.demo.entity.Guestbook;
import com.test.demo.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void testQuery2() {
//        다중 항목 검색
//        조건1. 제목 혹은 내용에 특정 키워드가 있다.
//        조건2. gno >= 0

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }


    public void testQuery1() {
//        단일 항목 검색

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

//        조건을 넣는 컨테이너 
//        Predicate를 상속받은 BooleanBuilder
        BooleanBuilder builder = new BooleanBuilder();

//        조건
        BooleanExpression expression = qGuestbook.title.contains(keyword);

//        조건 결합
        builder.and(expression);

//        QuerydslPredicateExecutor의 findAll 함수에 조건과 페이지 정보 파라미터로 전달
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    public void updateTest() {
//        값을 변경하여 update 수행 시 moddate 칼럼 값 변경 되는지 테스트
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeContent("Changed Content....");
            guestbook.changeTitle("Changed Title...");

            guestbookRepository.save(guestbook);
        }

    }

    public void insertDummies() {

        IntStream.rangeClosed(1, 300).forEach(i->{

            Guestbook guestbook = Guestbook.builder()
                    .title("Title....." + i)
                    .content("Content........" + i)
                    .writer("user" + (i%10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));

        });
    }
}

package com.test.demo.service;

import com.test.demo.dto.GuestbookDTO;
import com.test.demo.entity.Guestbook;

public interface GuestbookService {

    //방명록 등록
    Long register(GuestbookDTO dto);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}

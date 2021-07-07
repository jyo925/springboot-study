package com.test.demo.service;

import com.test.demo.dto.GuestbookDTO;
import com.test.demo.dto.PageRequestDTO;
import com.test.demo.dto.PageResultDTO;
import com.test.demo.entity.Guestbook;
import com.test.demo.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository; //final로 선언

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO.....................");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

       return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        //Guestbook을 GuestbookDTO로 변환하는 코드
//        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));
//        동일 코드
        Function<Guestbook, GuestbookDTO> fn = new Function<Guestbook, GuestbookDTO>() {
            @Override
            public GuestbookDTO apply(Guestbook guestbook) {
                return entityToDto(guestbook);
            }
        };

        return new PageResultDTO<>(result, fn);
    }


}

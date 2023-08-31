package com.example.books.service;

import com.example.books.domain.Author;
import com.example.books.domain.dto.AuthorDTO;
import com.example.books.repos.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepo authorRepo;
    private final AuthorDTOMapper authorDTOMapper;

    // вынести в отдельный утил класс
    private Pageable createPageRequestUsing(int page, int size){
        return PageRequest.of(page,size);
    }

    public Page<AuthorDTO> getAuthorPages(int page, int size){
            Pageable pageRequest = createPageRequestUsing(page,size);
            List<Author> authorList = authorRepo.findAll();
            List<AuthorDTO>allAthtorDTOList = authorList
                    .stream()
                    .map(authorDTOMapper::toDto)
                    .toList();

            int start = (int) pageRequest.getOffset();
            int end = Math.min((start+pageRequest.getPageSize()), allAthtorDTOList.size());

            List<AuthorDTO> pageContent = allAthtorDTOList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allAthtorDTOList.size());
    }
    public TreeSet<String> getAuthorsNames(){
        return authorRepo.findAll()
                .stream()
                .map((obj) -> Objects.toString(obj.getAuthorName(), null)).collect(Collectors.toCollection(TreeSet::new));
    }
    public Long findAuthorIdByName(String authorName) {
        return authorRepo.findByAuthorName(authorName).getId();
    }

}

package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.genre.GenreCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.GenreResponse;
import com.sidn.metruyenchu.novelservice.entity.Genre;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.GenreMapper;
import com.sidn.metruyenchu.novelservice.repository.GenreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @Slf4j
    public class GenreService {
        GenreRepository genreRepository;
        GenreMapper genreMapper;

        //CRUD genre
        //get all genre
    //    @PreAuthorize("hasRole('ADMIN')")
        public List<GenreResponse> getAllGenre() {
            return genreRepository.findAll().stream()
                    .map(genreMapper::toGenreResponse)
                    .collect(Collectors.toList());
        }

        //get genre by id
        public GenreResponse getGenreById(String genreId) {
            return genreRepository.findById(genreId)
                    .map(genreMapper::toGenreResponse)
                    .orElseThrow(() -> new AppException(ErrorCode.GENRE_NOT_FOUND)
                    );
        }

        //create genre
        public GenreResponse createGenre(GenreCreationRequest request) {
            Genre genre = genreMapper.toGenre(request);

            genreRepository.findByName(genre.getName())
                    .ifPresent(checkGenre -> {
                        throw new AppException(ErrorCode.GENRE_ALREADY_EXISTS);
                    });
            try {
                genre = genreRepository.save(genre);
            } catch (Exception exception) {
                throw new AppException(ErrorCode.UNKNOWN_ERROR);
            }

            return genreMapper.toGenreResponse(genre);
        }

        //update genre
        public GenreResponse updateGenre(String genreId, GenreCreationRequest request) {
            Genre genre = genreRepository.findById(genreId).orElseThrow(
                    () -> new AppException(ErrorCode.GENRE_NOT_FOUND)
            );

            genre.setName(request.getName());

            try {
                genre = genreRepository.save(genre);
            } catch (Exception exception) {
                throw new AppException(ErrorCode.UNKNOWN_ERROR);
            }

            return genreMapper.toGenreResponse(genre);
        }

        //delete genre
        public void deleteGenre(String genreId) {
    //        if (!genreRepository.existsById(genreId)) {
    //            throw new AppException(ErrorCode.GENRE_NOT_FOUND);
    //        }
    //        genreRepository.deleteById(genreId);
            Genre genre = genreRepository.findById(genreId).orElseThrow(
                    () -> new AppException(ErrorCode.GENRE_NOT_FOUND)
            );
            genre.setIsDeleted(true);
            try {
                genre = genreRepository.save(genre);
            } catch (Exception exception) {
                throw new AppException(ErrorCode.UNKNOWN_ERROR);
            }

        }

        public List<GenreResponse> getActivatingGenre() {
            return genreRepository.findAllByIsActiveAndIsDeleted(true,false).stream().map(genreMapper::toGenreResponse).toList();
        }

    }

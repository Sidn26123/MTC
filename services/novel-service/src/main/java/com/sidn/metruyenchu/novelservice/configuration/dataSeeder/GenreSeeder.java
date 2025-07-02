package com.sidn.metruyenchu.novelservice.configuration.dataSeeder;

import com.sidn.metruyenchu.novelservice.entity.Genre;
import com.sidn.metruyenchu.novelservice.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GenreSeeder {

    private final GenreRepository genreRepository;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    CommandLineRunner seedGenres() {
        return args -> {
            if (genreRepository.count() > 0) {
                System.out.println("Genre data already exists, skipping seeder.");
                return;
            }

            List<String> genreNames = Arrays.asList(
                // Genre names from Image 2 (Thể loại)
                "Tiên Hiệp",
                "Huyền Huyễn",
                "Khoa Huyễn",
                "Võng Du",
                "Đô Thị",
                "Đồng Nhân",
                "Dã Sử",
                "Canh Kỳ",
                "Huyền Nghi",
                "Kiếm Hiệp",
                "Kỳ Ảo",
                "Light Novel"
            );

            List<Genre> genres = genreNames.stream()
                .map(name -> Genre.builder()
                    .name(name)
                    .isActive(true)
                    .isDeleted(false)
                    .build())
                .toList();

            genreRepository.saveAll(genres);
            System.out.println("Genre seeder completed. Added " + genres.size() + " entries.");
        };
    }
}
package com.sidn.metruyenchu.novelservice.configuration.dataSeeder;

import com.sidn.metruyenchu.novelservice.entity.MainCharacterTrait;
import com.sidn.metruyenchu.novelservice.repository.MainCharacterTraitRepository;
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
public class MainCharacterTraitSeeder {

    private final MainCharacterTraitRepository mainCharacterTraitRepository;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 3)
    CommandLineRunner seedMainCharacterTraits() {
        return args -> {
            if (mainCharacterTraitRepository.count() > 0) {
                System.out.println("MainCharacterTrait data already exists, skipping seeder.");
                return;
            }

            List<String> traitNames = Arrays.asList(
                // MainCharacterTrait names from Image 2 (Tính cách nhân vật chính)
                "Điềm Đạm",
                "Nhiệt Huyết",
                "Võ Sĩ",
                "Thiết Huyết",
                "Nhẹ Nhàng",
                "Cơ Trí",
                "Lãnh Khốc",
                "Kiêu Ngạo",
                "Ngụ Ngốc",
                "Giao Hoạt"
            );

            List<MainCharacterTrait> traits = traitNames.stream()
                .map(name -> MainCharacterTrait.builder()
                    .name(name)
                    .isActive(true)
                    .isDeleted(false)
                    .build())
                .toList();

            mainCharacterTraitRepository.saveAll(traits);
            System.out.println("MainCharacterTrait seeder completed. Added " + traits.size() + " entries.");
        };
    }
}
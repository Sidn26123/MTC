package com.sidn.metruyenchu.novelservice.configuration.dataSeeder;

import com.sidn.metruyenchu.novelservice.entity.Sect;
import com.sidn.metruyenchu.novelservice.repository.SectRepository;
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
public class SectSeeder {

    private final SectRepository sectRepository;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    CommandLineRunner seedSects() {
        return args -> {
            if (sectRepository.count() > 0) {
                System.out.println("Sect data already exists, skipping seeder.");
                return;
            }

            List<String> sectNames = Arrays.asList(
                // Sect names from Image 1 (Lưu phái)
                "Hệ Thống",
                "Lão Gia",
                "Bàn Thờ",
                "Tùy Thân",
                "Phàm Nhân",
                "Võ Đích",
                "Xuyên Qua",
                "Nữ Cường",
                "Khế Ước",
                "Trọng Sinh",
                "Hồng Lâu",
                "Học Viện",
                "Biến Thân",
                "Cổ Ngu",
                "Chuyển Thế",
                "Xuyên Sách",
                "Đàn Xuyên",
                "Phế Tài",
                "Dưỡng Thành",
                "Cơm Mềm",
                "Võ Hạn",
                "Mary Sue",
                "Cá Mận",
                "Xây Dựng Thế Lực",
                "Xuyên Nhanh",
                "Nữ Phụ",
                "Vả Mặt",
                "Sảng Văn",
                "Xuyên Không",
                "Ngọt Sủng",
                "Ngự Thú",
                "Điền Viên",
                "Toàn Dân",
                "Mỹ Thực",
                "Phản Phái",
                "Sau Màn",
                "Thiên Tài"
            );

            List<Sect> sects = sectNames.stream()
                .map(name -> Sect.builder()
                    .name(name)
                    .isActive(true)
                    .isDeleted(false)
                    .build())
                .toList();

            sectRepository.saveAll(sects);
            System.out.println("Sect seeder completed. Added " + sects.size() + " entries.");
        };
    }
}
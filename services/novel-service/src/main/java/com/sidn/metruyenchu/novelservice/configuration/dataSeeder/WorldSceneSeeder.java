package com.sidn.metruyenchu.novelservice.configuration.dataSeeder;

import com.sidn.metruyenchu.novelservice.entity.WorldScene;
import com.sidn.metruyenchu.novelservice.repository.WorldSceneRepository;
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
public class WorldSceneSeeder {

    private final WorldSceneRepository worldSceneRepository;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    CommandLineRunner seedWorldScenes() {
        return args -> {
            if (worldSceneRepository.count() > 0) {
                System.out.println("WorldScene data already exists, skipping seeder.");
                return;
            }

            List<String> worldSceneNames = Arrays.asList(
                    // Vietnamese novel categories from the image
                    "Đông Phương Huyền Huyễn",
                    "Dị Thế Đại Lục",
                    "Vương Triều Tranh Bá",
                    "Cao Võ Thế Giới",
                    "Tây Phương Kỳ Huyễn",
                    "Hiện Đại Ma Pháp",
                    "Hắc Ám Huyễn Tưởng",
                    "Lịch Sử Thần Thoại",
                    "Võ Hiệp Huyễn Tưởng",
                    "Cổ Võ Tương Lai",
                    "Tu Chân Văn Minh",
                    "Huyền Tưởng Tu Tiên",
                    "Hiện Đại Tu Chân",
                    "Thần Thoại Tu Chân",
                    "Cổ Điển Tiên Hiệp",
                    "Viễn Cổ Hồng Hoang",
                    "Đô Thị Sinh Hoạt",
                    "Đô Thị Dị Năng",
                    "Thanh Xuân Vườn Trường",
                    "Ngụ Ngạc Minh Tinh",
                    "Thương Chiến Chức Trảng",
                    "Giả Không Lịch Sử",
                    "Lịch Sử Quân Sự",
                    "Dân Gian Truyền Thuyết",
                    "Lịch Sử Quan Trường",
                    "Hư Nghĩ Vong Du",
                    "Du Hí Dị Giới",
                    "Điện Tử Canh Kỳ",
                    "Thể Dục Canh Kỳ",
                    "Cổ Võ Cơ Giáp",
                    "Thế Giới Tương Lai",
                    "Tinh Tế Văn Minh",
                    "Tiến Hóa Biến Dị",
                    "Mạt Thế Nguy Cơ",
                    "Thời Không Xuyên Toa",
                    "Quỷ Bí Huyền Nghị",
                    "Kỳ Diệu Thế Giới",
                    "Trịnh Tham Thôi Lý",
                    "Thâm Hiểm Sinh Tồn",
                    "Cung Vi Trạch Đấu",
                    "Kinh Thương Chứng Điên",
                    "Tiên Lữ Kỳ Duyên",
                    "Hào Môn Thế Gia",
                    "Dị Tộc Luyện Tinh",
                    "Ma Pháp Huyền Tinh",
                    "Tinh Tế Luyến Ca",
                    "Linh Khí Khôi Phục",
                    "Chư Thiên Vạn Giới",
                    "Nguyên Sinh Huyễn Tưởng",
                    "Yêu Đương Thường Ngày",
                    "Điền Sinh Đồng Nhân",
                    "Cáo Tiêu Thố Tạo"
            );

            List<WorldScene> worldScenes = worldSceneNames.stream()
                    .map(name -> WorldScene.builder()
                            .name(name)
                            .isActive(true)
                            .isDeleted(false)
                            .build())
                    .toList();

            worldSceneRepository.saveAll(worldScenes);
            System.out.println("WorldScene seeder completed. Added " + worldScenes.size() + " entries.");
        };
    }
}
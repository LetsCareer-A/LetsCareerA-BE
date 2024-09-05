package com.example.letscareer.career.domain.dto.converter;

import com.example.letscareer.career.domain.dto.CareerDTO;
import com.example.letscareer.career.domain.model.Career;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CareerConverter {

    // Career 리스트 -> CareerDTO 리스트 변환 로직
    public static List<CareerDTO> convertToCareerDTOList(List<Career> careers) {
        return careers.stream()
                .map(CareerConverter::convertToCareerDTO)
                .collect(Collectors.toList());
    }

    // Career 페이지 -> CareerDTO 리스트 변환 로직
    public static List<CareerDTO> convertToCareerDTOList(Page<Career> careers) {
        return careers.getContent().stream()
                .map(CareerConverter::convertToCareerDTO)
                .collect(Collectors.toList());
    }

    // Career -> CareerDTO 변환 로직 메서드
    private static CareerDTO convertToCareerDTO(Career career) {
        return new CareerDTO(
                career.getCareerId(),
                career.getCategory().getValue(),
                career.getTitle(),
                toSummary(career.getSituation())
        );
    }

    private static String toSummary(String content) {
        return content.length() > 25 ? content.substring(0, 25) + "..." : content;
    }
}

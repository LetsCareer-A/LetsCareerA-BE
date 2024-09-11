package com.example.letscareer.career.domain.dto.converter;

import com.example.letscareer.appealCareer.domain.model.AppealCareer;
import com.example.letscareer.career.domain.dto.CareerDTO;
import com.example.letscareer.career.domain.dto.CareerWithAppealDTO;
import com.example.letscareer.career.domain.model.Career;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CareerConverter {

    // Career 리스트 -> CareerDTO 리스트 변환 로직
    public static List<CareerWithAppealDTO> convertToCareerWithAppealDTOList(List<Career> careers, List<AppealCareer> appealCareers) {
        // Career ID를 기준으로 어필 여부를 확인하는 맵 생성
        Map<Long, Boolean> appealMap = appealCareers.stream()
                .collect(Collectors.toMap(ac -> ac.getCareer().getCareerId(), ac -> true));

        return careers.stream()
                .map(career -> convertToCareerWithAppealDTO(career, appealMap.getOrDefault(career.getCareerId(), false)))
                .collect(Collectors.toList());
    }

    public static CareerWithAppealDTO convertToCareerWithAppealDTO(Career career, boolean isAppeal) {
        return new CareerWithAppealDTO(
                career.getCareerId(),
                career.getCategory().getValue(),
                career.getTitle(),
                toSummary(career.getSituation()),
                isAppeal
        );
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

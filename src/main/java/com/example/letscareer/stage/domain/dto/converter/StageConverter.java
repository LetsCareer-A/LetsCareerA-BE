package com.example.letscareer.stage.domain.dto.converter;

import com.example.letscareer.appealCareer.domain.model.AppealCareer;
import com.example.letscareer.self_intro.domain.dto.SelfIntroDTO;
import com.example.letscareer.self_intro.domain.model.SelfIntro;
import com.example.letscareer.stage.domain.dto.AppealCareerDTO;
import com.example.letscareer.stage.domain.model.Stage;
import com.example.letscareer.schedule.domain.model.Schedule;
import com.example.letscareer.stage.domain.dto.StageDTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class StageConverter {
    public static List<SelfIntroDTO> toSelfIntroDTOs(List<SelfIntro> selfIntros) {
        return selfIntros.stream()
                .map(SelfIntroDTO::from)
                .collect(Collectors.toList());
    }

    public static List<AppealCareerDTO> toAppealCareerDTOs(List<AppealCareer> appealCareers) {
        return appealCareers.stream()
                .map(AppealCareerDTO::from)
                .collect(Collectors.toList());
    }

    public static List<StageDTO> toStageDTOs(List<Stage> stages, Schedule schedule) {
        return stages.stream().map(stage -> {
            String dday = !schedule.isAlways() ? String.valueOf(calculateDday(stage.getDate())) : "";
            return StageDTO.from(stage, schedule.isAlways(), dday);
        }).collect(Collectors.toList());
    }

    private static int calculateDday(LocalDate date) {
        return Period.between(LocalDate.now(), date).getDays();
    }
}

package com.example.letscareer.stage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    DOC ("서류") , MID("중간"), INT("면접");
    private final String value;
}

package com.students.studentProfile.enums;

import java.util.EnumMap;

public class BatchEnumMap {
    public static EnumMap<BatchEnum, Integer> batches = new EnumMap<>(BatchEnum.class);

    static{
        batches.put(BatchEnum.SPRING_2024, 1);
        batches.put(BatchEnum.FALL_2024, 2);
        batches.put(BatchEnum.SPRING_2025, 3);
        batches.put(BatchEnum.FALL_2025, 4);
    }
}

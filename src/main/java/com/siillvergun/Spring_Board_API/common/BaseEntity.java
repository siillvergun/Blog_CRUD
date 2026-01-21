package com.siillvergun.Spring_Board_API.common;

import java.time.LocalDateTime;

public class BaseEntity {
    // 베이스 엔티티 만들어서 상속받기
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

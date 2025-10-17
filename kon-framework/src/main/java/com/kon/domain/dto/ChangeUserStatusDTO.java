package com.kon.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 35238
 * @date 2023/10/26 0026 18:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserStatusDTO {
    private Long userId;
    private String status;
}
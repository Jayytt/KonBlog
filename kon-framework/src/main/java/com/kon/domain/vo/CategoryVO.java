package com.kon.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO {
    //分类id
    private Long id;
    //父类id
    private Long pid;
    //分类名称
    private String name;

}

package com.achobeta.www.service.tasks.dto;

import com.achobeta.www.service.entity.ClassInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author jett
 * @since 2024/1/25
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SchoolInfoRespDTO {
    // class total
    private Long total;

    // class list
    private List<ClassInfo> infos;
}

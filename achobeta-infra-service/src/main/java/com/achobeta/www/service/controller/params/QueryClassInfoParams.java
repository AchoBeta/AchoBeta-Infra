package com.achobeta.www.service.controller.params;


import com.achobeta.www.common.util.Pagination;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
/**
 * @author jettcc in 2024/1/27
 * @version 1.0
 */
@Getter
@Setter
public class QueryClassInfoParams {
    private Long id;

    private String className;

    @Min(0)
    private Integer grade;

    private String collegeName;

    @Min(0)
    private Integer collegeId;

    Pagination pagination;
}

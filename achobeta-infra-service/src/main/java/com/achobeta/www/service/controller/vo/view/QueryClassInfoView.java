package com.achobeta.www.service.controller.vo.view;

import lombok.Builder;

import java.io.Serializable;

/**
 * @author jettcc in 2024/1/27
 * @version 1.0
 */
@Builder(toBuilder = true)
public class QueryClassInfoView implements Serializable {
    private Long id;
    private String className;
    private String grade;
    private String collegeName;
    private Integer studentNum;
    private Integer college;
}
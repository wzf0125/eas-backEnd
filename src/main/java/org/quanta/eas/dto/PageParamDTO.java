package org.quanta.eas.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/12
 */
@Data
public class PageParamDTO {
    @NotNull(message = "分页参数不能为空")
    @Min(value = 0, message = "起始页下标为1")
    Integer currentPage;
    @NotNull(message = "分页参数不能为空")
    @Max(value = 100, message = "页过大")
    Integer pageSize;

    public Integer getStart() {
        return (currentPage - 1) * pageSize;
    }
}

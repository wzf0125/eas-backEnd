package org.quanta.eas.utils;

import lombok.Data;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/12
 */
@Data
public class PageQueryUtils {
    public Integer currentPage;
    public Integer pageSize;
    public Integer start;

    public PageQueryUtils(Integer currentPage, Integer pageSize) {
        AssertParam.isNull("分页参数不能为空", currentPage, pageSize);
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.start = (currentPage - 1) * pageSize;
    }
}

package org.quanta.eas.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: 课表
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTableVO {
    List<ClassInfoVO> classList;
}

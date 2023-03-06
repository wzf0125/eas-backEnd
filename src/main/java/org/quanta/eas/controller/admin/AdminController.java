package org.quanta.eas.controller.admin;

import org.quanta.eas.annotation.Permission;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
@RestController
@RequestMapping("/admin")
@Permission({RoleConstants.ROLE_ADMIN})
public class AdminController  extends BaseController {
}

package com.kopever.peach.service.user.web;

import com.kopever.peach.common.Dozer;
import com.kopever.peach.service.framework.domain.HttpResponse;
import com.kopever.peach.service.user.domain.HttpUserMessage;
import com.kopever.peach.service.user.domain.data.PeachUserDO;
import com.kopever.peach.domain.user.vo.PeachUserVO;
import com.kopever.peach.service.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public HttpResponse<PeachUserVO> getUserByUsername(@PathVariable("username") String username) {
        HttpResponse<PeachUserVO> response = new HttpResponse<>();

        try {
            PeachUserDO userDO = userService.getUserByUsername(username);
            if (userDO != null) {
                response.setResult(Dozer.map(userDO, PeachUserVO.class));
            } else {
                response.setCodeMessage(
                        HttpUserMessage.USER_NOT_EXIST.getCode(),
                        HttpUserMessage.USER_NOT_EXIST.getMessage());
            }
        } catch (Exception e) {
            logger.error("UserController.getUserByUsername.Exception", e);
            response.setCodeMessage(
                    HttpUserMessage.GET_USER_EXCEPTION.getCode(),
                    HttpUserMessage.GET_USER_EXCEPTION.getMessage());
        }

        return response;
    }

}

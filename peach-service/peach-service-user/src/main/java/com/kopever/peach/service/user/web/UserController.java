package com.kopever.peach.service.user.web;

import com.kopever.peach.common.Dozer;
import com.kopever.peach.domain.HttpResponse;
import com.kopever.peach.service.user.domain.UserHttpMessage;
import com.kopever.peach.service.user.domain.data.UserDO;
import com.kopever.peach.domain.user.vo.UserVO;
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
    public HttpResponse<UserVO> getUserByUsername(@PathVariable("username") String username) {
        HttpResponse<UserVO> response = new HttpResponse<>();

        try {
            UserDO userDO = userService.getUserByUsername(username);
            if (userDO != null) {
                response.setResult(Dozer.map(userDO, UserVO.class));
            } else {
                response.setCodeMessage(
                        UserHttpMessage.USER_NOT_EXIST.getCode(),
                        UserHttpMessage.USER_NOT_EXIST.getMessage());
            }
        } catch (Exception e) {
            logger.error("UserController.getUserByUsername.Exception", e);
            response.setCodeMessage(
                    UserHttpMessage.GET_USER_EXCEPTION.getCode(),
                    UserHttpMessage.GET_USER_EXCEPTION.getMessage());
        }

        return response;
    }

}

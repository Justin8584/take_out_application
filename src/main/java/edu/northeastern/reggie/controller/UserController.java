package edu.northeastern.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.northeastern.reggie.common.R;
import edu.northeastern.reggie.entity.User;
import edu.northeastern.reggie.service.UserService;
import edu.northeastern.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * SMS Verification
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {

        // get phone number
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {

            // generate random verification code
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info(" Code is {}", code);

            // SMS service API (aliyun cloud)
            //SMSUtils.sendMessage("Reggie", "", phone,code);

            // save the random verification code in Session
            //session.setAttribute(phone, code);

            // save into Redis, have 5 min validation
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            R.success("SMS send successfully. ");
        }
        return R.error("Something went wrong ! ");
    }

    /**
     * log in
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info("map:{}", map.toString());
        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        //从Session中获取保存的验证码
        //Object codeInSession = session.getAttribute(phone);

        //从redis中获取保存的验证码
        Object codeInSession =redisTemplate.opsForValue().get(phone);

        //进行验证码比对（页面提交的验证码和Session中保存的验证码比对）
        if (codeInSession != null && codeInSession.equals(code)) {

            //如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {

                //判断当前手机号是否为新用户，如果是新用户则自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());

            // 如果登录成功，可以删除缓存验证码
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("Login Failed !");
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return R.success("Logou Successfully !!!");
    }
}

import com.code.pojo.dto.LoginDTO;
import com.code.service.ILogin;
import com.code.service.impl.LoginStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author : YangPing
 * @date 2022/4/11 23:58
 */
@Slf4j
public class test {

    public static void main(String[] args) {
        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setUsername("test");
        loginDTO.setPassword("123456");
        loginDTO.setLoginType("phone");

        System.out.println( ObjectUtils.isNotEmpty(loginDTO));

        ILogin normal = LoginStrategyFactory.getLoginStance(loginDTO.getLoginType());
        normal.login(loginDTO);
//        log.info("loginDTO={}",loginDTO);
    }
}

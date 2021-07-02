import com.king.MymvcWebApplication;
import com.king.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.constraints.NotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MymvcWebApplication.class)
@WebAppConfiguration
public class BaseTest {

    @Test
    public void test01(){
        getStr();
    }

    @NotNull
    private String getStr(){
        return null;
    }
}
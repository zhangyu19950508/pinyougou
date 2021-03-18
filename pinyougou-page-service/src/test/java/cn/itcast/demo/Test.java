package cn.itcast.demo;

import com.pinyougou.page.service.ItemPageService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class Test {

    @Autowired
    private ItemPageService itemPageService;

    @org.junit.Test
    public void test(){
        itemPageService.getItemHtml(149187842867948L);
    }

}

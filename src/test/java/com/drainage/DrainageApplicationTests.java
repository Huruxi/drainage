package com.drainage;

import com.drainage.entity.Admin;
import com.drainage.mapper.IAdminMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class DrainageApplicationTests {

    @Autowired
    private IAdminMapper adminMapper;

    @Test
    void contextLoads() {

        Admin admin = new Admin();
        admin.setName("admin");
        admin.setPassword("123456");

        int i = adminMapper.insert(admin);
        System.out.println(i);

        List<Admin> admins = adminMapper.selectList(null);
        System.out.println(admins.size());
    }

}

package com.yvan.testpage;

import com.yvan.pageobject.BaseException;
import com.yvan.pageobject.Browser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.logging.Logger;

/**
 * Function：测试用例的基类
 * a. 使用TestNG框架来管理用例，在BaseCase类中实现@BeforeSuite、@BeforeClass、@BeforeTest、@BeforeMethod及其对应的After方法等；
 * b. 一些常用的与用例相关的方法；
 * c. 公共变量等。
 * Created by YangWang on 2018-01-04 21:01.
 */
public class BaseCase {
    public static String url = "http://localhost:8081/scm/pages/pub/login.html"; // 测试地址
    public static String username = "user"; // 用户名
    public static String password = ""; // 密码
    static Logger log = Logger.getLogger(BaseCase.class.getName());

    static Browser driver;

    @BeforeSuite
    public void initSuite() {
        //初始化整个项目，如配置数据同步
    }

    @BeforeClass
    public void initTest() throws BaseException {
        //初始化测试类，如打开浏览器
        driver = new Browser();
    }

    @AfterClass
    public void close() {
        //关闭浏览器等操作，当然你也可以放在@AfterTest或@AfterMethod
        driver.close();
    }
}

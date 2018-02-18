package com.yvan.testpage;

import com.yvan.pageobject.BaseException;
import com.yvan.pageobject.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Function：登录测试
 * Created by YangWang on 2018-01-04 22:00.
 */
public class LoginTest extends BaseCase {
    LoginPage loginPage;

    @Test(invocationCount = 1)
    public void testCase1() throws BaseException {
        driver.open(url);
        driver.sleep(100);
        loginPage=new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoaded()); // 断言登录标题
    }

    @Test(dataProvider = "loginUsers")
    public void testCase2(String user, String password) throws BaseException {
        driver.open(url);
        driver.sleep(100);
        loginPage=new LoginPage(driver);
        loginPage.login(user, password);
        driver.sleep(100);
        Assert.assertTrue(loginPage.isLogged());
    }

    @DataProvider(name = "loginUsers")
    public Object[][] loginUsers() {
        return new Object[][]{{"baili", "baili"}, {"admin", "admi"}};
    }

}

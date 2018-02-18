package com.yvan.testpage;

import com.yvan.pageobject.BaseException;
import com.yvan.pageobject.Browser;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.logging.Logger;

/**
 * 这个demo只是展示基础功能
 */
public class BaiduDemo {

    Browser driver;
    String baseUrl;
    static Logger log = Logger.getLogger(BaiduDemo.class.getName());

    String searchBox = "id=>kw";
    String searchBtn = "id=>su";

    @BeforeMethod
    public void setUp() throws BaseException {
        baseUrl = "https://www.baidu.com";
        driver = new Browser();
    }

    // 定义对象数组
    @DataProvider(name = "search")
    public Object[][] Keys() {
        return new Object[][]{
                {"Java"}, {"testNG"}, {"arrow"}, {"Selenium"}};
    }

    @Test(dataProvider = "search")
    public void testSearch(String searchKey) throws InterruptedException {
        driver.open(baseUrl);
        driver.inputText(searchBox, searchKey);
        driver.click(searchBtn);
        Thread.sleep(2000);
        Assert.assertEquals(driver.getTitle(), searchKey + "_百度搜索");
        log.info("PASSED");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

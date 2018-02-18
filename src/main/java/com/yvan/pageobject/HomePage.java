package com.yvan.pageobject;

/**
 * Function：主页
 * Created by YangWang on 2018-01-04 21:56.
 */
public class HomePage {
    private Browser driver;

    public HomePage(Browser driver) {
        this.driver = driver;
    }

    // 页面元素的索引，UI变更在此修改
    public static class getElementIndex {
        public static final String TITLE = "scm系统";
        public static final String USER_BUTTEN_XPATH = "";
        public static final String IFRAME_ID = "";
        public static final String HOME_BUTTEN_ID = "";
        public static final String SignOut_BUTTEN_ID = "";
    }

    public void clickLeftNavHome() throws BaseException {
        driver.click(getElementIndex.HOME_BUTTEN_ID);
    }

    public String getUserText() throws BaseException {
        return driver.getText(getElementIndex.USER_BUTTEN_XPATH);
    }

    public void clickUser() throws BaseException {
        driver.click(getElementIndex.USER_BUTTEN_XPATH);
    }

    public void getQuit() throws BaseException {
        driver.click(getElementIndex.SignOut_BUTTEN_ID);
    }

}

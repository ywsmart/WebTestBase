package com.yvan.testpage;

import com.yvan.pageobject.Browser;
import com.yvan.pageobject.GlobalSettings;

/**
 * Function：测试用例的操作类
 * 主要封装一些其他公有的测试动作
 * Created by YangWang on 2018-01-04 21:22.
 */
public class TestAction extends BaseCase {
    Browser driver;
    int timeout = Integer.parseInt(GlobalSettings.timeout);

}

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class Tc002_LoginTest extends BaseClass {

    @Test(groups = {"sanity","Master"})
    public void verify_login(){

        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clicklogin();
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            lp.clicklogin();
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetpage = macc.isMyAccountPageExists();

            Assert.assertTrue(targetpage);
        }
        catch (Exception e)
        {
            Assert.fail();
        }

    }
}

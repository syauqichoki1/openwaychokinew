const { Builder, By, Key, until } = require('selenium-webdriver');

(async function openGmail() {
    let driver = await new Builder().forBrowser('chrome').build();

    try {
        // Step 1: Navigate to Gmail
        await driver.get('https://mail.google.com/mail/');

        // Step 2: Enter Email
        await driver.wait(until.elementLocated(By.id("identifierId")), 10000).sendKeys("syauqiopenway@gmail.com", Key.RETURN);

        // Step 3: Wait & Enter Password
        await driver.wait(until.elementLocated(By.name("password")), 10000).sendKeys("syauqiopenway123", Key.RETURN);

        // Step 4: Wait for Inbox to Load
        await driver.wait(until.titleContains("Inbox"), 15000);

        // Step 5: Fetch Unread Emails
        let unreadEmails = await driver.findElements(By.css("tr.zA.zE")); // Gmail unread emails use 'zA zE' class
        
        if (unreadEmails.length > 0) {
            let lastUnreadEmail = unreadEmails[unreadEmails.length - 1];
            let emailTitle = await lastUnreadEmail.findElement(By.css("span.bog")).getText();
            console.log("Last Unread Email Title:", emailTitle);
        } else {
            console.log("No unread emails found.");
        }

    } catch (error) {
        console.error("Error:", error);
    } finally {
        await driver.quit();
    }
})();

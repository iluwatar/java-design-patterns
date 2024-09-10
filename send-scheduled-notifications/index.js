const sdk = require("node-appwrite");
const axios = require("axios");

const client = new sdk.Client();
client.setEndpoint(process.env.PUBLIC_API_URL).setProject(process.env.APPWRITE_PROJECT_ID).setKey(process.env.PUBLIC_APPWRITE_API_KEY);

const database = new sdk.Databases(client, "event");

function cleanAndTrimContent(html) {
    const plainText = html.replace(/<[^>]*>/g, "");
    return plainText.substring(0, 100);
}

async function sendNotification(userId, content) {
    const ONESIGNAL_APP_ID = process.env.ONESIGNAL_APP_ID;
    const ONESIGNAL_API_KEY = process.env.ONESIGNAL_API_KEY;

    try {
        const response = await axios.post(
            "https://onesignal.com/api/v1/notifications",
            {
                app_id: ONESIGNAL_APP_ID,
                include_external_user_ids: [userId],
                contents: { en: content },
            },
            {
                headers: {
                    Authorization: `Basic ${ONESIGNAL_API_KEY}`,
                    "Content-Type": "application/json",
                },
            }
        );
        return response.data;
    } catch (error) {
        console.error("Error sending notification:", error);
        throw error;
    }
}

async function processNotifications() {
    try {
        const now = new Date().toISOString();

        const notifications = await database.listDocuments("66dabdc8001707f3431d", [sdk.Query.equal("Confirm", true), sdk.Query.lessThanEqual("PushDate", now)]);

        for (const notification of notifications.documents) {
            const recipientUserIds = notification.RecipientUserId;
            const trimmedContent = cleanAndTrimContent(notification.Content);

            for (const userId of recipientUserIds) {
                await sendNotification(userId, trimmedContent);
            }

            console.log(`Notifications sent for: ${notification.Title}`);
        }
    } catch (error) {
        console.error("Error processing notifications:", error);
    }
}

processNotifications();

package com.edricchan.rssreader.data;

import com.edricchan.rssreader.object.NotificationItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Todo: Remove this asap. Only meant for development purposes.
 * Todo: Implement FCM.
 */
public class NotificationContent {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<NotificationItem> ITEMS = new ArrayList<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            ITEMS.add(new NotificationItem("Notification #" + i, "Message content goes here"));
        }
    }
}

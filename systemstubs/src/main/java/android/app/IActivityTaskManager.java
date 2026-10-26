package android.app;

import android.content.pm.ParceledListSlice;
import android.os.IBinder;

public interface IActivityTaskManager {

    abstract class Stub extends android.os.Binder implements IServiceConnection {
        public static IActivityTaskManager asInterface(IBinder obj) {
            throw new RuntimeException("Stub!");
        }
    }

    java.util.List<ActivityManager.RunningTaskInfo> getTasks(int maxNum, boolean filterOnlyVisibleRecents, boolean keepIntentExtra, int displayId);

    void registerTaskStackListener(ITaskStackListener listener);
    void unregisterTaskStackListener(ITaskStackListener listener);

    ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(int maxNum, int flags,
                                                                     int userId);

}

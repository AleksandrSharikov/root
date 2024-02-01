package com.hstat.tgb.dev;


import org.springframework.stereotype.Service;

@Service
public class MemoryMonitorService {

    public void monitor() {
        // Get the current thread's stack size information
        long maxStackSize = getDefaultThreadStackSize();
        long usedStackSize = maxStackSize - Runtime.getRuntime().freeMemory();

        // Display stack size information
        System.out.println("Stack:");
        System.out.println("  Size: " + maxStackSize / 1024 + " KB"); // Convert to KB for better readability
        System.out.println("  Used: " + usedStackSize / 1024 + " KB");
    }

    private long getDefaultThreadStackSize() {
        long defaultStackSize = -1;

        try {
            String stackSizeStr = System.getProperty("thread.stack.size");
            if (stackSizeStr != null) {
                defaultStackSize = Long.parseLong(stackSizeStr);
            }
        } catch (NumberFormatException e) {
            // Handle the exception as needed
            e.printStackTrace();
        }

        return defaultStackSize;
    }
}



package edu.northeastern.reggie.common;

/**
 * Base on ThreadLocal, help save the user and current userID
 * for Thread Use, only remain in the current Thread (every response)
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getCurrentId() {
        return threadLocal.get();
    }
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

}

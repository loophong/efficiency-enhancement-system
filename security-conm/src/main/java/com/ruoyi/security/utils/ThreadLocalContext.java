package com.ruoyi.security.utils;

/**
 * ThreadLocal上下文工具类，用于在同一线程中的不同方法之间传递数据
 */
public class ThreadLocalContext {
    
    // 用于存储关联ID的ThreadLocal
    private static final ThreadLocal<Long> RELATED_ID_HOLDER = new ThreadLocal<>();
    
    /**
     * 设置关联ID
     * 
     * @param relatedId 关联ID
     */
    public static void setRelatedId(Long relatedId) {
        RELATED_ID_HOLDER.set(relatedId);
    }
    
    /**
     * 获取关联ID
     * 
     * @return 关联ID，如果未设置则返回null
     */
    public static Long getRelatedId() {
        return RELATED_ID_HOLDER.get();
    }
    
    /**
     * 清除关联ID
     * 为了防止内存泄漏，在使用完毕后应该调用此方法清除ThreadLocal中的数据
     */
    public static void clearRelatedId() {
        RELATED_ID_HOLDER.remove();
    }
} 
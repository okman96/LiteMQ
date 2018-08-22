package com.okman.litemq;

/**
 * 总控配置
 *
 * @auth waxuan
 * @since 2018年8月20日下午4:22:59
 */
public class Config {

	/**
	 * 是否持久化
	 */
	private boolean isPersistence = false;
	
	/**
	 * 持久化所在目录
	 */
	private String persistenceDir = "litemq/";
	
	/**
	 * 持久化文件后缀名
	 */
	private String persistenceSuffix = ".ldb";
	
	/**
	 * 启动时进行持久化文件加载
	 */
	private boolean isPersistenceInitLoad = false;
	
	private static boolean initialized = false;
	
	private Config() {
        //解决反射破坏到单例
        synchronized (Config.class) {
            if (!initialized) {
                initialized = true;
            } else {
                throw new RuntimeException("禁止初始化...");
            }
        }
    }
	    
    public static Config getInstance() {
        return LazyHolder.instance;
    }

	private static class LazyHolder {
        private static final Config instance = new Config();
    }

	public boolean getIsPersistence() {
		return isPersistence;
	}

	public void setIsPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}

	public String getPersistenceDir() {
		return persistenceDir;
	}

	public void setPersistenceDir(String persistenceDir) {
		this.persistenceDir = persistenceDir;
	}

	public void setPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}

	public String getPersistenceSuffix() {
		return persistenceSuffix;
	}

	public void setPersistenceSuffix(String persistenceSuffix) {
		this.persistenceSuffix = persistenceSuffix;
	}

	public boolean getIsPersistenceInitLoad() {
		return isPersistenceInitLoad;
	}

	public void setIsPersistenceInitLoad(boolean isPersistenceInitLoad) {
		this.isPersistenceInitLoad = isPersistenceInitLoad;
	}

}

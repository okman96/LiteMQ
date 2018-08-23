package com.okman.litemq.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.okman.litemq.Config;
import com.okman.litemq.core.element.IElement;
import com.okman.litemq.core.queue.IQueue;

/**
 * 持久化
 *
 * @auth waxuan
 * @since 2018年8月20日下午4:16:00
 */
public class Persistence {

	private static final Log logger = LogFactory.getLog(Persistence.class);
	
    private static boolean initialized = false;
    
    private Persistence() {
        //解决反射破坏到单例
        synchronized (Persistence.class) {
            if (!initialized) {
                initialized = true;
            } else {
                throw new RuntimeException("禁止初始化...");
            }
        }
    }
    
    public static Persistence getInstance() {
        return LazyHolder.instance;
    }
    
	/**
	 * 保存持久化队列元素
	 *
	 * @auth waxuan
	 * @since 2018年8月21日下午2:47:09
	 * @param e
	 */
	public void save(String prefix, IElement e) {
		ObjectOutputStream oo = null;
		try {
			File dir = this.getFile();
			File file = new File(dir, prefix + e.getIndex() + Config.getInstance().getPersistenceSuffix());
			oo = new ObjectOutputStream(new FileOutputStream(file));
			oo.writeObject(e);
		} catch (Exception ex) {
			logger.error("###### save ######", ex);
		} finally {
			if (oo != null) {
				try {
					oo.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除持久化队列元素
	 *
	 * @auth waxuan
	 * @since 2018年8月21日下午2:47:09
	 * @param e
	 */
	public void delete(IElement e) {
		try {
			File file = new File(Config.getInstance().getPersistenceDir(), e.getIndex() + Config.getInstance().getPersistenceSuffix());
			file.deleteOnExit();
		} catch (Exception ex) {
			logger.error("###### delete ######", ex);
		}
	}
	
	/**
	 * 根据文件获取元素
	 *
	 * @auth waxuan
	 * @since 2018年8月22日上午10:48:12
	 * @param file
	 * @return
	 */
	public IElement get(File file) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			IElement e = (IElement) ois.readObject();
			System.out.println("---------" + e.getIndex() + "-------");
			return e;
		} catch (Exception ex) {
			logger.error("###### get ######", ex);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 启动推送持久化中的元素
	 *
	 * @auth waxuan
	 * @since 2018年8月22日下午12:02:05
	 * @param queue
	 */
	public synchronized void initLoad(IQueue<IElement> queue, String prefix) {
		Config config = Config.getInstance(); 
		if (config.getIsPersistenceInitLoad()) {
			if (config.getIsPersistence()) {
    			File dir = new File(config.getPersistenceDir());
    			if (dir.exists()) {
    				File[] fileArr = dir.listFiles();
    				if (fileArr.length > 0) {
    					IElement[] elements = new IElement[fileArr.length];
    					for (int i = 0;i < fileArr.length;i++) {
    						File file = fileArr[i];
    						if (file.getName().indexOf(prefix) == 0) {
    							elements[i] = get(fileArr[i]);
        						fileArr[i].delete();
    						}
    					}
    					for (IElement e : elements) {
    						queue.offer(e);
    					}
    				}
    			}
    		}
		}
	}
	
	private File getFile() throws IOException {
		File f = new File(Config.getInstance().getPersistenceDir());
		if (!f.exists()) {
			f.mkdirs();
		}
		return f;
	}

	private static class LazyHolder {
        private static final Persistence instance = new Persistence();
    }
	
}

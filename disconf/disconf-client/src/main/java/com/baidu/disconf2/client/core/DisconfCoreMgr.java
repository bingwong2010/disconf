package com.baidu.disconf2.client.core;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf2.client.common.inter.IDisconfUpdate;
import com.baidu.disconf2.client.common.model.DisconfCenterFile;
import com.baidu.disconf2.client.common.model.DisconfCenterItem;
import com.baidu.disconf2.client.core.inner.DisconfCenterStore;

/**
 * 仓库模块统一对外接口
 * 
 * @author liaoqiqi
 * @version 2014-6-6
 */
public class DisconfCoreMgr {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(DisconfCoreMgr.class);

    private DisconfCoreMgr() {

    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static DisconfCoreMgr instance = new DisconfCoreMgr();
    }

    public static DisconfCoreMgr getInstance() {
        return SingletonHolder.instance;
    }

    // 核心仓库
    private DisconfCenterStore disconfCenterStore = new DisconfCenterStore();

    /**
     * 批量添加配置文件
     * 
     * @return
     */
    public void transformScanFiles(List<DisconfCenterFile> disconfCenterFiles) {

        for (DisconfCenterFile disconfCenterFile : disconfCenterFiles) {
            disconfCenterStore.storeOneFile(disconfCenterFile);
        }
    }

    /**
     * 获取配置文件的数据
     * 
     * @return
     */
    public Map<String, DisconfCenterFile> getConfFileMap() {
        return disconfCenterStore.getConfFileMap();
    }

    /**
     * 批量添加配置项
     * 
     * @return
     */
    public void transformScanItems(List<DisconfCenterItem> disconfCenterItems) {

        for (DisconfCenterItem disconfCenterItem : disconfCenterItems) {
            disconfCenterStore.storeOneItem(disconfCenterItem);
        }
    }

    /**
     * 获取配置项的数据
     * 
     * @return
     */
    public Map<String, DisconfCenterItem> getConfItemMap() {
        return disconfCenterStore.getConfItemMap();
    }

    /**
     * 是否有这个配置
     * 
     * @return
     */
    public boolean hasThisConf(String key) {

        // 配置文件
        if (disconfCenterStore.getConfFileMap().containsKey(key)) {
            return true;
        }

        // 配置项
        if (disconfCenterStore.getConfItemMap().containsKey(key)) {
            return true;
        }

        return false;
    }

    /**
     * 添加一个更新 的回调函数
     */
    public void addUpdateCallback(String key, IDisconfUpdate iDisconfUpdate) {

        if (disconfCenterStore.getConfFileMap().containsKey(key)) {

            disconfCenterStore.getConfFileMap().get(key)
                    .getDisconfCommonCallbackModel().getDisconfConfUpdates()
                    .add(iDisconfUpdate);
        }
    }

    /**
     * 添加一个更新 的回调函数
     */
    public void addUpdateCallbackList(String key,
            List<IDisconfUpdate> iDisconfUpdateList) {

        if (disconfCenterStore.getConfFileMap().containsKey(key)) {

            disconfCenterStore.getConfFileMap().get(key)
                    .getDisconfCommonCallbackModel().getDisconfConfUpdates()
                    .addAll(iDisconfUpdateList);

        } else {

            if (disconfCenterStore.getConfItemMap().containsKey(key)) {

                disconfCenterStore.getConfItemMap().get(key)
                        .getDisconfCommonCallbackModel()
                        .getDisconfConfUpdates().addAll(iDisconfUpdateList);
            }
        }
    }

    /**
     * 获取配置文件数据，根据文件名及Key名获取数据
     * 
     * @param fileName
     * @param keyName
     * @return
     */
    public Object getConfigFile(String fileName, String keyName) {

        return null;
    }

    /**
     * 获取配置项数据, 根据 KEy获取
     * 
     * @param keyName
     * @return
     */
    public Object getConfigItem(String keyName) {

        return null;
    }
}
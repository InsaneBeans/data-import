package com.bonc.data.util.serialize;

/**
 * 序列化接口
 * 
 * @author huh
 *
 */
public interface Serial {
    /**
     * 序列化对象到文件，默认文件名和位置
     * 
     * @param object 序列化对象
     */
    String serializeToString(Object object);
    /**
     * 序列化对象到文件，默认文件名和位置
     * 
     * @param object 序列化对象
     */
    String serializeToFile(Object object);
    /**
     * 序列化对象，可设置文件名（默认文件位置）
     * 
     * @param object   需要序列化的对象
     * @param fullFileName 输出序列化结果的完整文件名
     */
    String serializeToFile(Object object, String fullFileName);
    /**
     * 序列化对象，可设置文件名和文件位置
     * 
     * @param object    需要序列化的对象
     * @param pathName  输出序列化文件的路径
     * @param fileName  输出序列化文件的文件名
     */
    String serializeToFile(Object object, String pathName, String fileName);
    /**
     * 反序列化文件
     * 
     * @param fileName 反序列化文件名
     * @param clazz    反序列化对象类型
     */
    <T> T deserializeFromFile(String fileName, Class<T> clazz);
    /**
     * 反序列化文件,可设置文件名和文件位置
     * 
     * @param pathName 反序列化文件的路径
     * @param fileName 反序列化的文件
     * @param clazz    反序列化对象类
     */
    <T> T deserializeFromFile(String pathName, String fileName, Class<T> clazz);
    /**
     * 反序列化字符串
     * 
     * @param stringContent  字符串内容
     * @param clazz    反序列化结果类型对象
     */
    <T> T deserializeFromString(String stringContent, Class<T> clazz);
}
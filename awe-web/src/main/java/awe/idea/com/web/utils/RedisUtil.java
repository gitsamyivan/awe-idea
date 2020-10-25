/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package awe.idea.com.web.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name="redisTemplate")
    private ValueOperations<String, Object> valueOperations;
    @Resource(name="redisTemplate")
    private HashOperations<String, String, Object> hashOperations;
    @Resource(name="redisTemplate")
    private ListOperations<String, Object> listOperations;
    @Resource(name="redisTemplate")
    private SetOperations<String, Object> setOperations;
    @Resource(name="redisTemplate")
    private ZSetOperations<String, Object> zSetOperations;
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire){
        valueOperations.set(key, value);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        valueOperations.set(key, value);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        T value = (T)valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public Object get(String key, long expire) {
        Object value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }


    public void delete(String key) {
        redisTemplate.delete(key);
    }


    //hash操作

    /**
     * 添加 Hash 键值对
     * @param key 存储键名
     * @param hashKey
     * @param hashValue
     */
    public void putHash(String key, String hashKey, Object hashValue){
        hashOperations.put(key, hashKey, hashValue);
    }

    /**
     * 批量添加 hash 的 键值对
     * 有则覆盖,没有则添加
     * @param key
     * @param map
     */
    public void putHashAll(String key,Map<String,Object> map){
        hashOperations.putAll(key, map);
    }

    /**
     * 添加 hash 键值对. 不存在的时候才添加
     * @param key
     * @param hashKey
     * @param hashValue
     * @return
     */
    public boolean putHashIfAbsent(String key, String hashKey, Object hashValue){
        return hashOperations.putIfAbsent(key, hashKey, hashValue);
    }


    /**
     * 删除指定 hash 的 HashKey
     * @param key
     * @param hashKeys
     * @return 删除成功的 数量
     */
    public Long deleteHash(String key, Object ...hashKeys){
        return hashOperations.delete(key, hashKeys);
    }


    /**
     * 获取指定 key 下的 hashkey
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHashKey(String key,Object hashKey){
        return hashOperations.get(key, hashKey);
    }


    /**
     * 获取 key 下的 所有  hashkey 和 value
     * @param key
     * @return
     */
    public Map<String,Object> getHashEntries(String key){
        return hashOperations.entries(key);
    }

    /**
     * 获取指定 hash 下面的 键值对 数量
     * @param key
     * @return
     */
    public Long hashSize(String key){
        return hashOperations.size(key);
    }


    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }
}

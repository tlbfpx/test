package tlb.mall.common.util.util.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.util.Map;

public class RedisUtil {

	private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);
	// 数据库的下标
	private static final Integer DEFAULT_DB_INDEX = 0;
	
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
	
	@Resource
	private ShardedJedisPool shardedJedisPool;

	private <K> K execute(Function<Jedis, K> fun, String key, Integer index) {
		if(StringUtils.isNotEmpty(key)){
				ShardedJedis shardedJedis = null;
				Boolean selectDB = false;
				Jedis jedis = null;
					// 从连接池中获取到jedis分片对象
					try {
						shardedJedis = shardedJedisPool.getResource();
						jedis = shardedJedis.getShard(key);
						if (index != null && index.intValue() != DEFAULT_DB_INDEX) {// 选中传入的数据库
							selectDB = true;
							jedis.select(index);
						}
						return fun.callback(jedis);
					} catch (Exception e) {
					}finally {
					if (selectDB && null != jedis) {// 恢复默认选中数据库
						jedis.select(DEFAULT_DB_INDEX);
					}
					if (null != shardedJedis) {
						// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
						shardedJedis.close();
					}
				}
		}
		return null;
	}

	/**
	 * 设置一个值 key - value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key, final String value) {
		return this.set(DEFAULT_DB_INDEX, key, value);
	}

	/**
	 * 存入自定义对象 key-obj
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public String set(final String key, final Object obj) {
		return this.execute(new Function<Jedis, String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.set(key.getBytes(), SerializeUtil.serialize(obj));
			}
		}, key, DEFAULT_DB_INDEX);
	}

	/**
	 * 存入自定义对象 key-obj
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public String set(final String key, final Object obj, final Integer expire) {
		return this.execute(new Function<Jedis, String>() {
			@Override
			public String callback(Jedis jedis) {
				String str = jedis.set(key.getBytes(),SerializeUtil.serialize(obj));
				jedis.expire(key, expire);
				return str;
			}
		}, key, DEFAULT_DB_INDEX);
	}

	/**
	 * 获取对象
	 * 
	 * @param key
	 * @return
	 */
	public <T> T getObj(final String key, Class<T> t) {
		log.info("获取缓存对象key="+key.getBytes());
		return this.execute(new Function<Jedis, T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T callback(Jedis e) {
				byte[] value = e.get(key.getBytes());
				return (T) SerializeUtil.unserialize(value);
			}
		}, key, DEFAULT_DB_INDEX);
	}

	/**
	 * 设置一个值 key - value
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(Integer index, final String key, final String value) {
		log.info("新增缓存key="+key);
		return this.execute(new Function<Jedis, String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.set(key, value);
			}
		}, key, index);
	}

	/**
	 * 设置一个值，并且指定生存时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public String set(final String key, final String value,
			final Integer seconds) {
		return this.set(DEFAULT_DB_INDEX, key, value, seconds);
	}

	/**
	 * 设置一个值，并且指定生存时间
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public String set(Integer index, final String key, final String value,
			final Integer seconds) {
		return this.execute(new Function<Jedis, String>() {
			@Override
			public String callback(Jedis jedis) {
				String str = jedis.set(key, value);
				jedis.expire(key, seconds);
				return str;
			}
		}, key, index);
	}

	/**
	 * 为key设置生存时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(final String key, final Integer seconds) {
		return this.expire(DEFAULT_DB_INDEX, key, seconds);
	}

	/**
	 * 为key设置生存时间
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(Integer index, final String key, final Integer seconds) {
		return this.execute(new Function<Jedis, Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.expire(key, seconds);
			}

		}, key, index);
	}

	/**
	 * 散列保存数据
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 *            设置一个key
	 * @param field
	 *            对象字段
	 * @param value
	 *            对象字段值
	 * @param seconds
	 *            生存时间
	 * @return
	 */
	public Long hset(Integer index, final String key, final String field,
			final String value, final Integer seconds) {
		return this.execute(new Function<Jedis, Long>() {
			@Override
			public Long callback(Jedis jedis) {
				Long hset = jedis.hset(key, field, value);
				jedis.expire(key, seconds);
				return hset;
			}
		}, key, index);
	}

	/**
	 * 散列保存数据
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 *            设置一个key
	 * @param field
	 *            对象字段
	 * @param value
	 *            对象字段值
	 * @param seconds
	 *            生存时间
	 * @return
	 */
	public Long hset(final String key, final String field, final String value,
			final Integer seconds) {
		return this.hset(DEFAULT_DB_INDEX, key, field, value, seconds);
	}

	/**
	 * 散列保存多个数据
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 *            设置一个key
	 * @param field
	 *            对象字段
	 * @param value
	 *            对象字段值
	 * @param seconds
	 *            生存时间
	 * @return
	 */
	public String hmset(Integer index, final String key,
			final Map<String, String> hash, final Integer seconds) {
		return this.execute(new Function<Jedis, String>() {
			@Override
			public String callback(Jedis jedis) {
				String hset = jedis.hmset(key, hash);
				jedis.expire(key, seconds);
				return hset;
			}
		}, key, index);
	}

	/**
	 * 散列保存多个数据
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 *            设置一个key
	 * @param field
	 *            对象字段
	 * @param value
	 *            对象字段值
	 * @param seconds
	 *            生存时间
	 * @return
	 */
	public String hmset(final String key, final Map<String, String> hash,
			final Integer seconds) {
		return this.hmset(DEFAULT_DB_INDEX, key, hash, seconds);
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public String hget(final String key, final String field) {
		return this.hget(DEFAULT_DB_INDEX, key, field);
	}

	/**
	 * 获取数据
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 * @return
	 */
	public String hget(Integer index, final String key, final String field) {
		return this.execute(new Function<Jedis, String>() {
			@Override
			public String callback(Jedis e) {
				return e.hget(key, field);
			}
		}, key, index);
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return this.get(DEFAULT_DB_INDEX, key);
	}

	/**
	 * 获取数据
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 * @return
	 */
	public String get(Integer index, final String key) {
		log.info("从缓存中获取key="+key);
		return this.execute(new Function<Jedis, String>() {
			@Override
			public String callback(Jedis e) {
				return e.get(key);
			}
		}, key, index);
	}

	/**
	 * 散列获取数据
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(Integer index, final String key) {
		return this.execute(new Function<Jedis, Map<String, String>>() {
			@Override
			public Map<String, String> callback(Jedis e) {
				@SuppressWarnings("unused")
				Map<String, String> hgetAll = e.hgetAll(key);
				return e.hgetAll(key);
			}
		}, key, index);
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(final String key) {
		return this.hgetAll(DEFAULT_DB_INDEX, key);
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 * @return
	 */
	public Long del(final String key) {
		return this.del(DEFAULT_DB_INDEX, key);
	}

	/**
	 * 删除key
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 * @return
	 */
	public Long del(Integer index, final String key) {
		log.info("删除缓存key="+key);
		return this.execute(new Function<Jedis, Long>() {
			@Override
			public Long callback(Jedis e) {
				return e.del(key);
			}
		}, key, index);
	}

	/**
	 * 单个数据删除散列
	 * 
	 * @param index
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long hdel(Integer index, final String key, final String fields) {
		return this.execute(new Function<Jedis, Long>() {
			@Override
			public Long callback(Jedis e) {
				return e.hdel(key, fields);
			}
		}, key, index);
	}

	/**
	 * 单个数据删除散列
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Long hdel(final String key, final String field) {
		return this.hdel(DEFAULT_DB_INDEX, key, field);
	}

	/**
	 * 判断一个KEY是否存在
	 * 
	 * @param index
	 *            数据库名
	 * @param key
	 * @return
	 */
	public Boolean exists(Integer index, final String key) {
		return this.execute(new Function<Jedis, Boolean>() {
			@Override
			public Boolean callback(Jedis e) {
				return e.exists(key);
			}
		}, key, index);
	}

	/**
	 * 判断一个KEY是否存在
	 * 
	 * @param key
	 * @return
	 */
	public Boolean exists(final String key) {
		return this.exists(DEFAULT_DB_INDEX, key);
	}
}

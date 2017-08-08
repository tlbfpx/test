package tlb.mall.user.web.util.redis;

import redis.clients.jedis.ShardedJedis;

public interface JedisDataSource {
    
    ShardedJedis getRedisClient();

    void returnResource(ShardedJedis shardedJedis);

    void returnResource(ShardedJedis shardedJedis, boolean broken);
}

local val = redis.call('hexists', KEYS[1], ARGV[1])
if val == 0 then
    return 0;
else
    val = redis.call('hget', KEYS[1], ARGV[1])
    local count = tonumber(val)
    if count > 1 then
        redis.call('hincrby', KEYS[1], ARGV[1], -1)
        return 1
    else
        redis.call('del', KEYS[1])
        return 1
    end
end
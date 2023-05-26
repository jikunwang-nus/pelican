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
        --[judge if wait queue exists and not empty]
        local v = redis.call('lpop', KEYS[2])
        if (v ~= nil or (type(v) == 'boolean' and v))then
            redis.call('pexpire', KEYS[1], -1)
            return 1;
        else
            redis.call('pexpire', KEYS[1], -2)
            redis.call('publish', KEYS[3], v)
            return 1
        end
    end
end
local value = redis.call('exists',KEYS[1])
if value == 0 then
    redis.call('setex',KEYS[1],ARGS[2])
    redis.call('hset',KEYS[1],ARGS[1],1)
    return 1
else
    -- if exists,judge reentry
    local current = redis.call('hexists',KEYS[1],ARGS[1])
    if current == 1 then
        redis.call('hincrby',KEYS[1],ARGS[1])
        return 1
    else
        return 0
    end
end
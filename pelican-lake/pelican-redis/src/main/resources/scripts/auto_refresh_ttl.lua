local val = redis.call('exists', KEYS[1])
if val == 0 then
    return 0;
end
val = redis.call('hexists', KEYS[1], ARGV[1])
if val == 0 then
    return 0;
else
    redis.call('expire', KEYS[1], ARGV[2])
    return 1;
end
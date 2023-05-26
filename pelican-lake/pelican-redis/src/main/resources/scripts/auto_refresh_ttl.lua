if redis.call('pttl', KEYS[1]) <= 0 then
    return 0;
end
if redis.call('hexists', KEYS[1], ARGV[1]) == 0 then
    return 0;
end
redis.call('pexpire', KEYS[1], ARGV[2])
return 1;

package com.cny.service;

import com.cny.dao.ProductRepository;
import com.cny.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author : chennengyuan
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LocalCacheService localCacheService;

    @Resource(name = "myRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Product findById(Long id) {
        String key = "product:" + id;
        //1、先查询本地缓存
        Product product = (Product) localCacheService.get(key);
        if (product == null) {
            //2、再查询Redis缓存
            String productJsonStr = (String) redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(productJsonStr)) {
                //3、缓存中都不存在,则查询数据库(此处忽略加锁)
                product = productRepository.findById(id).get();

                //4.保存到Redis和本地缓存
                redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(product));
                localCacheService.set(key, product);
            }
        }

        return product;
    }
}

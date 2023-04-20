package com.cny.controller;

import com.cny.entity.Product;
import com.cny.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chennengyuan
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    //一般给前端反馈的结果会封装为一个对象
    //比如ResultBean，封装响应码及响应的结果
    //此处我们省略，直接返回一个对象
    @Autowired
    private ProductService productService;

    //http://localhost:8080/product/detail?id=6
    @GetMapping("/detail")
    public Product getDetails(Long id) {
        log.info("客户端请求商品id为{}的信息", id);
        return productService.findById(id);
    }
}

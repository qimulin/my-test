package zhou.wu.mytest.lambda.filter;


import lombok.*;

/**
 * Created by lin.xc on 2019/7/11
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id; //商品编号
    private String name; //商品名称
    private Double price; //商品价格
    private String dirName; //商品类别
}

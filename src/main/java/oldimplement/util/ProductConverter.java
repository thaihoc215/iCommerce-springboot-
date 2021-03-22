package oldimplement.util;

import oldimplement.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class ProductConverter {

    /*public static ProductDto toProductDto(Product product) {

        if (product == null) {
            return null;
        }

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setColor(product.getColor().getName());
        productDto.setBrand(product.getBrand().getName());
        productDto.setQuantity(product.getQuantity());
        return productDto;
    }

    public static List<ProductDto> toProductDtoList(List<Product> productList) {
        if(productList.isEmpty()) {
            return new ArrayList<>();
        }

        List<ProductDto> productDtos = new ArrayList<>();
        productList.forEach(product -> productDtos.add(toProductDto(product)));
        return productDtos;
    }*/
}

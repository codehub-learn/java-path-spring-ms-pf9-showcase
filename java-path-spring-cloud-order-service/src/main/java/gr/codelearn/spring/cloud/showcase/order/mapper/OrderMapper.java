package gr.codelearn.spring.cloud.showcase.order.mapper;

import gr.codelearn.spring.cloud.showcase.core.base.BaseMapper;
import gr.codelearn.spring.cloud.showcase.core.transfer.resource.OrderResource;
import gr.codelearn.spring.cloud.showcase.order.domain.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderResource> {
}

package kz.tmq.tmq_online_store.business.service.impl;

import kz.tmq.tmq_online_store.business.entity.Order;
import kz.tmq.tmq_online_store.business.entity.OrderItem;
import kz.tmq.tmq_online_store.business.repository.OrderItemRepository;
import kz.tmq.tmq_online_store.business.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

}

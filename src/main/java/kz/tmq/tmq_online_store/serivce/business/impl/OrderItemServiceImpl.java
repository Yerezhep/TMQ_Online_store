package kz.tmq.tmq_online_store.serivce.business.impl;

import kz.tmq.tmq_online_store.repository.business.OrderItemRepository;
import kz.tmq.tmq_online_store.serivce.business.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

}

package kz.tmq.tmq_online_store.auth.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class CommonMapper {

    private final ModelMapper modelMapper;

    public CommonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T, S> S convertTo(T data, Class<S> type) {
        return modelMapper.map(data, type);
    }

}

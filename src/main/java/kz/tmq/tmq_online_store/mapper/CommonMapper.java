package kz.tmq.tmq_online_store.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommonMapper {

    private final ModelMapper modelMapper;

    public CommonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T, S> S convertTo(T data, Class<S> type) {
        return modelMapper.map(data, type);
    }

    public <T, S> List<S> convertToList(List<T> lists, Class<S> type) {
        return lists.stream()
                .map(list -> convertTo(list, type))
                .collect(Collectors.toList());
    }

}

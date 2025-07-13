package com.finemeet.userservice.service;

import com.finemeet.userservice.dto.AddressRequestDto;
import com.finemeet.userservice.dto.AddressResponseDto;
import com.finemeet.userservice.entity.Address;
import com.finemeet.userservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        log.info("Creating address with request: {}", addressRequestDto);

        Address address = modelMapper.map(addressRequestDto, Address.class);
        log.info("Creating address with request: {}", address);

        Address savedAddress = addressRepository.save(address);

        log.info("Address created successfully: {}", savedAddress);
        return modelMapper.map(savedAddress, AddressResponseDto.class);
    }

}

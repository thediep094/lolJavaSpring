package com.example.lol.lol.services.Iml;


import com.example.lol.lol.Repositories.ShipingAddressRepository;
import com.example.lol.lol.model.ShipingAddress;
import com.example.lol.lol.services.domain.ShipingAddressService;
import com.example.lol.lol.services.dto.ShipingAddressDTO;
import com.example.lol.lol.services.mapper.ShipingAddressMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShipingAddress}.
 */
@Service
@Transactional
public class ShipingAddressServiceImpl implements ShipingAddressService {

    private final Logger log = LoggerFactory.getLogger(ShipingAddressServiceImpl.class);

    private final ShipingAddressRepository shipingAddressRepository;

    private final ShipingAddressMapper shipingAddressMapper;
    public ShipingAddressServiceImpl(
        ShipingAddressRepository shipingAddressRepository,
        ShipingAddressMapper shipingAddressMapper
    ) {
        this.shipingAddressRepository = shipingAddressRepository;
        this.shipingAddressMapper = shipingAddressMapper;
    }

    @Override
    public ShipingAddressDTO save(ShipingAddressDTO shipingAddressDTO) {
        log.debug("Request to save ShipingAddress : {}", shipingAddressDTO);
        ShipingAddress shipingAddress = shipingAddressMapper.toEntity(shipingAddressDTO);
        shipingAddress = shipingAddressRepository.save(shipingAddress);
        ShipingAddressDTO result = shipingAddressMapper.toDto(shipingAddress);
        return result;
    }

    @Override
    public Optional<ShipingAddressDTO> partialUpdate(ShipingAddressDTO shipingAddressDTO) {
        log.debug("Request to partially update ShipingAddress : {}", shipingAddressDTO);

        return shipingAddressRepository
            .findById(shipingAddressDTO.getId())
            .map(
                existingShipingAddress -> {
                    shipingAddressMapper.partialUpdate(existingShipingAddress, shipingAddressDTO);

                    return existingShipingAddress;
                }
            )
            .map(shipingAddressRepository::save)
            .map(shipingAddressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShipingAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipingAddresses");
        return shipingAddressRepository.findAll(pageable).map(shipingAddressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShipingAddressDTO> findOne(Long id) {
        log.debug("Request to get ShipingAddress : {}", id);
        return shipingAddressRepository.findById(id).map(shipingAddressMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipingAddress : {}", id);
        shipingAddressRepository.deleteById(id);
    }


}

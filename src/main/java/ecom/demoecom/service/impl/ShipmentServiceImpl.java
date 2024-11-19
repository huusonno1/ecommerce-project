package ecom.demoecom.service.impl;

import ecom.demoecom.entity.Shipment;
import ecom.demoecom.repo.ShipmentRepository;
import ecom.demoecom.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;


    @Override
    public void saveShipment(Shipment shipment) {
        shipmentRepository.save(shipment);
    }
}

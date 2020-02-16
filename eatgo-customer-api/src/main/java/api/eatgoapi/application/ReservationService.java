package api.eatgoapi.application;

import api.eatgoapi.domain.Reservation;
import api.eatgoapi.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(
            Long restaurnatId, Long userId, String name,
            String date, String time,
            Integer partySize) {

        Reservation reservation = Reservation.builder()
                .restaurantId(restaurnatId)
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();

        return reservationRepository.save(reservation);
    }

}

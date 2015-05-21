package com.betfair.aping.betting.enums;

import javax.annotation.Generated;

@Generated("pt.paulosantos.betfair.aping.codegen")
public enum PersistenceType {
    /**
    *Lapse the order when the market is turned in-play
    */
    LAPSE,
    /**
    *Persist the order to in-play. The bet will be place automatically into the in-play market at the start of the event.
    */
    PERSIST,
    /**
    *Put the order into the auction (SP) at turn-in-play
    */
    MARKET_ON_CLOSE
}

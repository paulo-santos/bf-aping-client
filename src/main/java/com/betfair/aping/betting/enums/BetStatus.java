package com.betfair.aping.betting.enums;

import javax.annotation.Generated;

@Generated("pt.paulosantos.betfair.aping.codegen")
public enum BetStatus {
    /**
    *A matched bet that was settled normally
    */
    SETTLED,
    /**
    *A matched bet that was subsequently voided by Betfair, before, during or after settlement
    */
    VOIDED,
    /**
    *Unmatched bet that was cancelled by Betfair (for example at turn in play).
    */
    LAPSED,
    /**
    *Unmatched bet that was cancelled by an explicit customer action.
    */
    CANCELLED
}

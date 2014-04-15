package com.betfair.aping.betting.enums;

import javax.annotation.Generated;

@Generated("pt.paulosantos.betfair.aping.codegen")
public enum MarketSort {
    /**
    *Minimum traded volume
    */
    MINIMUM_TRADED,
    /**
    *Maximum traded volume
    */
    MAXIMUM_TRADED,
    /**
    *Minimum available to match
    */
    MINIMUM_AVAILABLE,
    /**
    *Maximum available to match
    */
    MAXIMUM_AVAILABLE,
    /**
    *The closest markets based on their expected start time
    */
    FIRST_TO_START,
    /**
    *The most distant markets based on their expected start time
    */
    LAST_TO_START
}

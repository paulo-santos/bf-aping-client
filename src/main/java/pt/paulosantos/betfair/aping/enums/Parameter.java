package pt.paulosantos.betfair.aping.enums;

/**
 * Created by Paulo. 19-04-2014.
 */
public enum Parameter {
    BET_IDS("betIds"),
    BET_STATUS("betStatus"),
    CURRENCY_CODE("currencyCode"),
    CUSTOMER_REF("customerRef"),
    EVENT_IDS("eventIds"),
    EVENT_TYPE_IDS("eventTypeIds"),
    FROM_RECORD("fromRecord"),
    FILTER("filter"),
    GRANULARITY("granularity"),
    GROUP_BY("orderBy"),
    INCLUDE_BSP_BETS("includeBspBets"),
    INCLUDE_ITEM_DESCRIPTION("includeItemDescription"),
    INCLUDE_SETTLED_BETS("includeSettledBets"),
    INSTRUCTIONS("instructions"),
    LOCALE("locale"),
    MARKET_ID("marketId"),
    MARKET_IDS("marketIds"),
    MARKET_PROJECTION("marketProjection"),
    MATCH_PROJECTION("matchProjection"),
    MAX_RESULTS("maxResults"),
    NET_OF_COMMISSION("netOfCommission"),
    SORT("sort"),
    SORT_DIR("sortDir"),
    ORDER_BY("orderBy"),
    ORDER_PROJECTION("orderProjection"),
    PLACED_DATE_RANGE("placedDateRange"),
    PRICE_PROJECTION("priceProjection"),
    RECORD_COUNT("recordCount"),
    RUNNER_IDS("runnerIds"),
    SETTLED_DATE_RANGE("settledDateRange"),
    SIDE("side");


    private String parameter;

    private Parameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter(){
        return  parameter;
    }

    @Override
    public String toString(){
        return parameter;
    }
}

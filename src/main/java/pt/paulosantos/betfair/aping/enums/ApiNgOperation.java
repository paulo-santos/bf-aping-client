package pt.paulosantos.betfair.aping.enums;

import com.betfair.aping.betting.entities.*;
import com.fasterxml.jackson.core.type.TypeReference;
import pt.paulosantos.betfair.aping.dto.rpc.container.*;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public enum ApiNgOperation {

    LIST_EVENT_TYPES(
            "listEventTypes",
            EventTypeResultContainer.class,
            new TypeReference<List<EventTypeResult>>() {}),

    LIST_COMPETITIONS(
            "listCompetitions",
            CompetitionResultContainer.class,
            new TypeReference<List<CompetitionResult>>() {}),

    LIST_TIME_RANGES(
            "listTimeRanges",
            TimeRangeResultContainer.class,
            new TypeReference<List<TimeRangeResult>>() {}),

    LIST_EVENTS(
            "listEvents",
            EventResultContainer.class,
            new TypeReference<List<EventResult>>() {}),

    LIST_MARKET_TYPES(
            "listMarketTypes",
            MarketTypeResultContainer.class,
            new TypeReference<List<MarketTypeResult>>() {}),

    LIST_COUNTRIES("listCountries",
            CountryCodeResultContainer.class,
            new TypeReference<List<CountryCodeResult>>() {}),

    LIST_VENUES(
            "listVenues",
            VenueResultContainer.class,
            new TypeReference<List<VenueResult>>() {}),

    LIST_MARKET_CATALOGUE(
            "listMarketCatalogue",
            MarketCatalogueContainer.class,
            new TypeReference<List<MarketCatalogue>>() {}),

    LIST_MARKET_BOOK(
            "listMarketBook",
            MarketBookContainer.class,
            new TypeReference<List<MarketBook>>() {}),

    PLACE_ORDERS(
            "placeOrders",
            PlaceExecutionReportContainer.class,
            new TypeReference<PlaceExecutionReport>() {}),

    CANCEL_ORDERS(
            "cancelOrders",
            CancelExecutionReportContainer.class,
            new TypeReference<CancelExecutionReport>() {}),

    REPLACE_ORDERS(
            "replaceOrders",
            ReplaceExecutionReportContainer.class,
            new TypeReference<ReplaceExecutionReport>() {}),

    UPDATE_ORDERS(
            "updateOrders",
            UpdateExecutionReportContainer.class,
            new TypeReference<UpdateExecutionReport>() {}),

    LIST_CURRENT_ORDERS(
            "listCurrentOrders",
            CurrentOrderSummaryReportContainer.class,
            new TypeReference<CurrentOrderSummaryReport>() {}),

    LIST_MARKET_PROFIT_AND_LOSS(
            "listMarketProfitAndLoss",
            MarketProfitAndLossContainer.class,
            new TypeReference<List<MarketProfitAndLoss>>(){} ),

    LIST_CLEARED_ORDERS(
            "listClearedOrders",
            ClearedOrderSummaryReportContainer.class,
            new TypeReference<ClearedOrderSummaryReport>(){});

    private String operation;
    private Class<? extends Container> rpcResponseType;
    private TypeReference responseType;

    ApiNgOperation(String operation, Class<? extends Container> rpcResponseType, TypeReference responseType){
        this.operation = operation;
        this.responseType=responseType;
        this.rpcResponseType = rpcResponseType;
    }

    public String toString(){
        return operation;
    }
    public TypeReference getResponseType(){
        return responseType;
    }

    public Class<? extends Container> getRpcResponseType(){
        return rpcResponseType;
    }
}

package pt.paulosantos.betfair.aping;

import com.betfair.aping.betting.entities.*;
import com.betfair.aping.betting.enums.*;
import pt.paulosantos.betfair.aping.core.ExecutionContext;
import pt.paulosantos.betfair.aping.exceptions.APINGException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @author Paulo
 */
public interface APINGBettingService {
    /**
     *
     * @param filter The filter to select desired markets. All markets that match the criteria in the filter are selected
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of Event Types (i.e. Sports) associated with the markets selected by the MarketFilter.
     * @throws APINGException Generic exception that is thrown if this operation fails for any reason.
     */
    List<EventTypeResult> listEventTypes(@NotNull MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param filter The filter to select desired markets. All markets that match the criteria in the filter are selected.
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of Competitions (i.e., World Cup 2013) associated with the markets selected by the MarketFilter. Currently only Football markets have an associated competition.
     * @throws APINGException
     */
    List<CompetitionResult> listCompetitions(@NotNull MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param filter The filter to select desired markets. All markets that match the criteria in the filter are selected
     * @param granularity The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of time ranges in the granularity specified in the request (i.e. 3PM to 4PM, Aug 14th to Aug 15th) associated with the markets selected by the MarketFilter.
     * @throws APINGException
     */
    List<TimeRangeResult> listTimeRanges(@NotNull MarketFilter filter, @NotNull TimeGranularity granularity, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param filter The filter to select desired markets. All markets that match the criteria in the filter are selected
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of Events (i.e, Reading vs. Man United) associated with the markets selected by the MarketFilter.
     * @throws APINGException
     */
    List<EventResult> listEvents(MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param filter The filter to select desired markets. All markets that match the criteria in the filter are selected
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of market types (i.e. MATCH_ODDS, NEXT_GOAL) associated with the markets selected by the MarketFilter. The market types are always the same, regardless of locale.
     * @throws APINGException
     */
    List<MarketTypeResult> listMarketTypes(@NotNull MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param filter The filter to select desired markets. All markets that match the criteria in the filter are selected
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of Countries associated with the markets selected by the MarketFilter.
     * @throws APINGException
     */
    List<CountryCodeResult> listCountries(MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param filter The filter to select desired markets. All markets that match the criteria in the filter are selected
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of Venues (i.e. Cheltenham, Ascot) associated with the markets selected by the MarketFilter. Currently, only Horse Racing markets are associated with a Venue.
     * @throws APINGException
     */
    List<VenueResult> listVenues(@NotNull MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param filter The filter to select desired markets. All markets that match the criteria in the filter are selected
     * @param marketProjection The type and amount of data returned about the market.
     * @param sort The order of the results. Will default to RANK if not passed
     * @param maxResults limit on the total number of results returned, must be greater than 0 and less than or equal to 1000
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of information about markets that does not change (or changes very rarely).
     * You use listMarketCatalogue to retrieve the name of the market, the names of selections and other information about markets.
     * Market Data Request Limits apply to requests made to listMarketCatalogue.
     * @throws APINGException
     */
    List<MarketCatalogue> listMarketCatalogue(@NotNull MarketFilter filter, Set<MarketProjection> marketProjection, MarketSort sort, int maxResults, String locale, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param marketIds One or more market ids. The number of markets returned depends on the amount of data you request via the price projection.
     * @param priceProjection The projection of price data you want to receive in the response.
     * @param orderProjection The orders you want to receive in the response.
     * @param matchProjection If you ask for orders, specifies the representation of matches
     * @param currencyCode A Betfair standard currency code. If not specified, the default currency code is used.
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return  a list of dynamic data about markets. Dynamic data includes prices, the status of the market, the status of selections, the traded volume, and the status of any orders you have placed in the market.
     * @throws APINGException
     */
    List<MarketBook> listMarketBook(@NotNull Set<String>marketIds, PriceProjection priceProjection, OrderProjection orderProjection, MatchProjection matchProjection, String currencyCode, String locale, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param marketIds List of markets to calculate profit and loss
     * @param includeSettledBets Option to include settled bets (partially settled markets only). Defaults to false if not specified.
     * @param includeBspBets Option to include BSP bets. Defaults to false if not specified.
     * @param netOfCommission Option to return profit and loss net of users current commission rate for this market including any special tariffs. Defaults to false if not specified.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return profit and loss for a given list of markets. The values are calculated using matched bets and optionally settled bets.
     * @throws APINGException
     */
    List<MarketProfitAndLoss> listMarketProfitAndLoss(@NotNull Set<String> marketIds, boolean includeSettledBets, boolean includeBspBets, boolean netOfCommission, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     *
     * @param betIds Optionally restricts the results to the specified bet IDs.
     * @param marketIds Optionally restricts the results to the specified market IDs.
     * @param orderProjection Optionally restricts the results to the specified order status.
     * @param placedDateRange Deprecated use dateRange instead.
     *                        Optionally restricts the results to be from/to the specified placed date.
     *                        This date is inclusive, i.e. if an order was placed on exactly this date (to the millisecond) then it will be included in the results.
     *                        If the from is later than the to, no results will be returned.
     * @param orderBy Specifies how the results will be ordered. If no value is passed in, it defaults to BY_BET.
     *                Also acts as a filter such that only orders with a valid value in the field being ordered by will be returned (i.e. BY_VOID_TIME returns only voided orders,
     *                BY_SETTLED_TIME returns only settled orders and BY_MATCH_TIME returns only orders with a matched date (voided, settled, matched orders)).
     * @param sortDir Specifies the direction the results will be sorted in. If no value is passed in, it defaults to EARLIEST_TO_LATEST.
     * @param fromRecord Specifies the first record that will be returned. Records start at index zero, not at index one.
     * @param recordCount Specifies how many records will be returned, from the index position 'fromRecord'. Note that there is a page size limit of 1000.
     *                    A value of zero indicates that you would like all records (including and from 'fromRecord') up to the limit.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return  a list of your current orders.
     * Optionally you can filter and sort your current orders using the various parameters, setting none of the parameters will return all of your current orders, up to a maximum of 1000 bets, ordered BY_BET and sorted EARLIEST_TO_LATEST.
     * To retrieve more than 1000 orders, you need to make use of the fromRecord and recordCount parameters.
     * @throws APINGException
     */
    CurrentOrderSummaryReport listCurrentOrders(Set<String>betIds, Set<String>marketIds, OrderProjection orderProjection, TimeRange placedDateRange, OrderBy orderBy, SortDir sortDir, int fromRecord, int recordCount, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     * Retrieves a list of settled bets based on the bet status, ordered by settled date.
     * To retrieve more than 1000 records, you need to make use of the fromRecord and recordCount parameters. @see https://api.developer.betfair.com/services/webapps/docs/display/1smk3cen4v3lu3yomq5qye0ni/listClearedOrders+-+Roll-up+Fields+Available
     *
     * @param betStatus Restricts the results to the specified status.
     * @param eventTypeIds Optionally restricts the results to the specified Event Type IDs.
     * @param eventIds Optionally restricts the results to the specified Event IDs.
     * @param marketIds Optionally restricts the results to the specified market IDs.
     * @param runnerIds Optionally restricts the results to the specified Runners.
     * @param betIds Optionally restricts the results to the specified bet IDs.
     * @param side Optionally restricts the results to the specified side.
     * @param settledDateRange Optionally restricts the results to be from/to the specified settled date. This date is inclusive, i.e. if an order was placed on exactly this date (to the millisecond) then it will be included in the results. If the from is later than the to, no results will be returned.
     * @param groupBy How to aggregate the lines, if not supplied then the lowest level is returned, i.e. bet by bet This is only applicable to SETTLED BetStatus.
     * @param includeItemDescription If true then an ItemDescription object is included in the response.
     * @param locale The language used for the itemDescription. If not specified, the customer account default is returned.
     * @param fromRecord Specifies the first record that will be returned. Records start at index zero.
     * @param recordCount Specifies how many records will be returned, from the index position 'fromRecord'. Note that there is a page size limit of 1000. A value of zero indicates that you would like all records (including and from 'fromRecord') up to the limit.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return a list of settled bets based on the bet status, ordered by settled date.
     * @throws APINGException
     */
    ClearedOrderSummaryReport listClearedOrders(BetStatus betStatus, Set<String> eventTypeIds, Set<String> eventIds, Set<String> marketIds, Set<RunnerId> runnerIds, Set<String> betIds, Side side, TimeRange settledDateRange, GroupBy groupBy, boolean includeItemDescription, String locale, int fromRecord, int recordCount, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     * Place new orders into market. This operation is atomic in that all orders will be placed or none will be placed.
     *
     * @param marketId The market id these orders are to be placed on
     * @param instructions The number of place instructions.  The limit of place instructions per request is 200.
     * @param customerRef Optional parameter allowing the client to pass a unique string (up to 32 chars) that is used to de-dupe mistaken re-submissions.
     *                    CustomerRef can contain: upper/lower chars, digits, chars : - . _ + * : ; ~ only.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return Place report
     * @throws APINGException
     */
    PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     * Cancel all bets OR cancel all bets on a market OR fully or partially cancel particular orders on a market. Only LIMIT orders can be cancelled or partially cancelled once placed.
     *
     * @param marketId If not supplied all bets are cancelled
     * @param instructions All instructions need to be on the same market. If not supplied all bets on the market (if market id is passed) are fully cancelled.  The limit of cancel instructions per request is 60
     * @param customerRef Optional parameter allowing the client to pass a unique string (up to 32 chars) that is used to de-dupe mistaken re-submissions.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return Cancel report
     * @throws APINGException
     */
    CancelExecutionReport cancelOrders(String marketId, List<CancelInstruction> instructions, String customerRef, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     * This operation is logically a bulk cancel followed by a bulk place.
     * The cancel is completed first then the new orders are placed.
     * The new orders will be placed atomically in that they will all be placed or none will be placed.
     * In the case where the new orders cannot be placed the cancellations will not be rolled back.
     * See ReplaceInstruction.
     *
     * @param marketId The market id these orders are to be placed on
     * @param instructions The number of replace instructions.  The limit of replace instructions per request is 60.
     * @param customerRef Optional parameter allowing the client to pass a unique string (up to 32 chars) that is used to de-dupe mistaken re-submissions.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return
     * @throws APINGException
     */
    ReplaceExecutionReport replaceOrders(@NotNull String marketId, @NotNull List<ReplaceInstruction> instructions, String customerRef, @NotNull ExecutionContext executionContext) throws APINGException;

    /**
     * Update non-exposure changing fields
     *
     * @param marketId The market id these orders are to be placed on
     * @param instructions The number of update instructions.  The limit of update instructions per request is 60
     * @param customerRef Optional parameter allowing the client to pass a unique string (up to 32 chars) that is used to de-dupe mistaken re-submissions.
     * @param executionContext The executionContext containing the appKey and sessionId
     * @return Update report
     * @throws APINGException
     */
    UpdateExecutionReport updateOrders(@NotNull String marketId, @NotNull List<UpdateInstruction> instructions, String customerRef, @NotNull ExecutionContext executionContext) throws APINGException;

}

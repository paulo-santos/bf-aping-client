package pt.paulosantos.betfair.aping;

import com.betfair.aping.LoginResponse;
import com.betfair.aping.betting.entities.*;
import com.betfair.aping.betting.enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.util.HttpCookieStore;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.paulosantos.betfair.aping.core.ExecutionContext;
import pt.paulosantos.betfair.aping.enums.ApiNgOperation;
import pt.paulosantos.betfair.aping.enums.Parameter;
import pt.paulosantos.betfair.aping.exceptions.APINGException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Paulo. 19-04-2014.
 */
public abstract class AbstractSportsApiClient implements APINGBettingService {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSportsApiClient.class);

    protected static final String APP_KEY_HEADER_PROPERTY = "aping.header.appKey";

    protected static final String SESSION_TOKEN_HEADER_PROPERTY = "aping.header.ssoId";
    protected static final String DATE_FORMAT_PROPERTY = "aping.dateFormat";
    protected static final String HOST_PROPERTY = "aping.host";
    protected static final String BETTING_BASE_PATH_PROPERTY = "aping.betting.basePath";

    protected static final String HOST = "https://api.betfair.com";
    protected static final String BETTING_BASE_PATH = "/exchange/betting";
    protected static final String APP_KEY_HEADER = "X-Application";
    protected static final String SESSION_TOKEN_HEADER = "X-Authentication";
    protected static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    protected static final Properties properties = new Properties();

    protected HttpClient httpClient;
    protected ObjectMapper objectMapper;

    static {
        try {
            InputStream in = AbstractSportsApiClient.class.getResourceAsStream("/aping-client.properties");
            properties.load(in);
            in.close();

            in = AbstractSportsApiClient.class.getResourceAsStream("/aping-client-overrides.properties");
            if (in != null) {
                properties.load(in);
                in.close();
            }
        } catch (Exception e) {
        }
    }

    protected AbstractSportsApiClient() {
        initialize();
    }

    private void initialize() {
        httpClient = new HttpClient(new SslContextFactory());
        httpClient.setCookieStore(new HttpCookieStore.Empty());
        try {
            httpClient.start();
        } catch (Exception e) {
            LOG.error("Unable to start the httpClient. ", e);
        }

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.setDateFormat(new SimpleDateFormat(properties.getProperty(DATE_FORMAT_PROPERTY, DEFAULT_DATE_FORMAT)));
    }

    @Override
    public List<EventTypeResult> listEventTypes(@NotNull MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(filter);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.FILTER, filter);
        params.put(Parameter.LOCALE, locale);

        return execute(executionContext, ApiNgOperation.LIST_EVENT_TYPES, params);
    }

    @Override
    public List<CompetitionResult> listCompetitions(@NotNull MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(filter);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.FILTER, filter);
        params.put(Parameter.LOCALE, locale);

        return execute(executionContext, ApiNgOperation.LIST_COMPETITIONS, params);
    }

    @Override
    public List<TimeRangeResult> listTimeRanges(@NotNull MarketFilter filter, @NotNull TimeGranularity granularity, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(filter);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.FILTER, filter);
        params.put(Parameter.GRANULARITY, granularity);

        return execute(executionContext, ApiNgOperation.LIST_TIME_RANGES, params);
    }

    @Override
    public List<EventResult> listEvents(MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.FILTER, filter);
        params.put(Parameter.LOCALE, locale);

        return execute(executionContext, ApiNgOperation.LIST_EVENTS, params);
    }

    @Override
    public List<MarketTypeResult> listMarketTypes(@NotNull MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(filter);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.FILTER, filter);
        params.put(Parameter.LOCALE, locale);

        return execute(executionContext, ApiNgOperation.LIST_MARKET_TYPES, params);
    }

    @Override
    public List<CountryCodeResult> listCountries(MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.FILTER, filter);
        params.put(Parameter.LOCALE, locale);

        return execute(executionContext, ApiNgOperation.LIST_COUNTRIES, params);
    }

    @Override
    public List<VenueResult> listVenues(@NotNull MarketFilter filter, String locale, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(filter);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.FILTER, filter);
        params.put(Parameter.LOCALE, locale);

        return execute(executionContext, ApiNgOperation.LIST_VENUES, params);
    }

    @Override
    public List<MarketCatalogue> listMarketCatalogue(@NotNull MarketFilter filter, Set<MarketProjection> marketProjection, MarketSort sort, int maxResults, String locale, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(filter);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.FILTER, filter);
        params.put(Parameter.MARKET_PROJECTION, marketProjection);
        params.put(Parameter.SORT, sort);
        params.put(Parameter.MAX_RESULTS, maxResults);
        params.put(Parameter.LOCALE, locale);

        return execute(executionContext, ApiNgOperation.LIST_MARKET_CATALOGUE, params);
    }

    @Override
    public List<MarketBook> listMarketBook(@NotNull Set<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection, MatchProjection matchProjection, String currencyCode, String locale, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(marketIds);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.MARKET_IDS, marketIds);
        params.put(Parameter.PRICE_PROJECTION, priceProjection);
        params.put(Parameter.ORDER_PROJECTION, orderProjection);
        params.put(Parameter.MATCH_PROJECTION, matchProjection);
        params.put(Parameter.CURRENCY_CODE, currencyCode);
        params.put(Parameter.LOCALE, locale);

        return execute(executionContext, ApiNgOperation.LIST_MARKET_BOOK, params);
    }

    @Override
    public List<MarketProfitAndLoss> listMarketProfitAndLoss(@NotNull Set<String> marketIds, boolean includeSettledBets, boolean includeBspBets, boolean netOfCommission, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(marketIds);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.MARKET_IDS, marketIds);
        params.put(Parameter.INCLUDE_SETTLED_BETS, includeSettledBets);
        params.put(Parameter.INCLUDE_BSP_BETS, includeBspBets);
        params.put(Parameter.NET_OF_COMMISSION, netOfCommission);

        return execute(executionContext, ApiNgOperation.LIST_MARKET_PROFIT_AND_LOSS, params);
    }

    @Override
    public CurrentOrderSummaryReport listCurrentOrders(Set<String> betIds, Set<String> marketIds, OrderProjection orderProjection, TimeRange placedDateRange, OrderBy orderBy, SortDir sortDir, int fromRecord, int recordCount, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.BET_IDS, betIds);
        params.put(Parameter.MARKET_IDS, marketIds);
        params.put(Parameter.ORDER_PROJECTION, orderProjection);
        params.put(Parameter.PLACED_DATE_RANGE, placedDateRange);
        params.put(Parameter.ORDER_BY, orderBy);
        params.put(Parameter.SORT_DIR, sortDir);
        params.put(Parameter.FROM_RECORD, fromRecord);
        params.put(Parameter.RECORD_COUNT, recordCount);

        return execute(executionContext, ApiNgOperation.LIST_CURRENT_ORDERS, params);
    }

    @Override
    public ClearedOrderSummaryReport listClearedOrders(BetStatus betStatus, Set<String> eventTypeIds, Set<String> eventIds, Set<String> marketIds, Set<RunnerId> runnerIds, Set<String> betIds, Side side, TimeRange settledDateRange, GroupBy groupBy, boolean includeItemDescription, String locale, int fromRecord, int recordCount, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.BET_STATUS, betStatus);
        params.put(Parameter.EVENT_TYPE_IDS, eventTypeIds);
        params.put(Parameter.EVENT_IDS, eventIds);
        params.put(Parameter.MARKET_IDS, marketIds);
        params.put(Parameter.RUNNER_IDS, runnerIds);
        params.put(Parameter.BET_IDS, betIds);
        params.put(Parameter.SIDE, side);
        params.put(Parameter.SETTLED_DATE_RANGE, settledDateRange);
        params.put(Parameter.GROUP_BY, groupBy);
        params.put(Parameter.INCLUDE_ITEM_DESCRIPTION, includeItemDescription);
        params.put(Parameter.LOCALE, locale);
        params.put(Parameter.FROM_RECORD, fromRecord);
        params.put(Parameter.RECORD_COUNT, recordCount);

        return execute(executionContext, ApiNgOperation.LIST_CLEARED_ORDERS, params);
    }

    @Override
    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.MARKET_ID, marketId);
        params.put(Parameter.INSTRUCTIONS, instructions);
        params.put(Parameter.CUSTOMER_REF, customerRef);

        return execute(executionContext, ApiNgOperation.PLACE_ORDERS, params);
    }

    @Override
    public CancelExecutionReport cancelOrders(String marketId, List<CancelInstruction> instructions, String customerRef, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.MARKET_ID, marketId);
        params.put(Parameter.INSTRUCTIONS, instructions);
        params.put(Parameter.CUSTOMER_REF, customerRef);

        return execute(executionContext, ApiNgOperation.CANCEL_ORDERS, params);
    }

    @Override
    public ReplaceExecutionReport replaceOrders(@NotNull String marketId, @NotNull List<ReplaceInstruction> instructions, String customerRef, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(marketId);
        Objects.requireNonNull(instructions);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.MARKET_ID, marketId);
        params.put(Parameter.INSTRUCTIONS, instructions);
        params.put(Parameter.CUSTOMER_REF, customerRef);

        return execute(executionContext, ApiNgOperation.REPLACE_ORDERS, params);
    }

    @Override
    public UpdateExecutionReport updateOrders(@NotNull String marketId, @NotNull List<UpdateInstruction> instructions, String customerRef, @NotNull ExecutionContext executionContext) throws APINGException {
        Objects.requireNonNull(executionContext, "Execution context cannot be null");
        Objects.requireNonNull(marketId);
        Objects.requireNonNull(instructions);

        Map<Parameter, Object> params = new HashMap<Parameter, Object>();
        params.put(Parameter.MARKET_ID, marketId);
        params.put(Parameter.INSTRUCTIONS, instructions);
        params.put(Parameter.CUSTOMER_REF, customerRef);

        return execute(executionContext, ApiNgOperation.UPDATE_ORDERS, params);
    }

    //Creating a new HttpClient for each request because only the login method requires certificate authentication
    //Adding the certificate to the others requests will probably result in slower responses. Need to test it
    public LoginResponse login(String username, String password, String keyStorePath, String keyStorePassword, String appkey) throws Exception {
        SslContextFactory sslContextFactory = new SslContextFactory();

        SSLContext ctx = SSLContext.getInstance("TLS");
        KeyManager[] keyManagers = getKeyManagers("pkcs12", new FileInputStream(new File(keyStorePath)), keyStorePassword);
        ctx.init(keyManagers, null, new SecureRandom());

        sslContextFactory.setSslContext(ctx);

        HttpClient httpClient = new HttpClient(sslContextFactory);
        httpClient.start();

        ContentResponse contentResponse = httpClient
                .POST("https://identitysso.betfair.com/api/certlogin")
                .header("X-Application", appkey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .content(new StringContentProvider("username="+username+"&password="+password))
                .send();

        httpClient.stop();

        return objectMapper.readValue(contentResponse.getContentAsString(), LoginResponse.class);
    }

    private static KeyManager[] getKeyManagers(String keyStoreType, InputStream keyStoreFile, String keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyStorePassword.toCharArray());
        return kmf.getKeyManagers();
    }

    protected abstract <T> T execute(ExecutionContext executionContext, ApiNgOperation operation, Map<Parameter, Object> params)
            throws APINGException;

}


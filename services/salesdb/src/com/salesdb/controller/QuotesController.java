/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the source code license agreement you entered into with wavemaker-com*/
package com.salesdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.salesdb.FollowUps;
import com.salesdb.Quotes;
import com.salesdb.Sales;
import com.salesdb.service.QuotesService;

/**
 * Controller object for domain model class Quotes.
 * @see Quotes
 */
@RestController("salesdb.QuotesController")
@Api(value = "QuotesController", description = "Exposes APIs to work with Quotes resource.")
@RequestMapping("/salesdb/Quotes")
public class QuotesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuotesController.class);

    @Autowired
    @Qualifier("salesdb.QuotesService")
    private QuotesService quotesService;

    @ApiOperation(value = "Creates a new Quotes instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Quotes createQuotes(@RequestBody Quotes quotes) {
        LOGGER.debug("Create Quotes with information: {}", quotes);
        quotes = quotesService.create(quotes);
        LOGGER.debug("Created Quotes with information: {}", quotes);
        return quotes;
    }

    @ApiOperation(value = "Returns the Quotes instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Quotes getQuotes(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Quotes with id: {}", id);
        Quotes foundQuotes = quotesService.getById(id);
        LOGGER.debug("Quotes details with id: {}", foundQuotes);
        return foundQuotes;
    }

    @ApiOperation(value = "Updates the Quotes instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Quotes editQuotes(@PathVariable("id") Integer id, @RequestBody Quotes quotes) throws EntityNotFoundException {
        LOGGER.debug("Editing Quotes with id: {}", quotes.getId());
        quotes.setId(id);
        quotes = quotesService.update(quotes);
        LOGGER.debug("Quotes details with id: {}", quotes);
        return quotes;
    }

    @ApiOperation(value = "Deletes the Quotes instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteQuotes(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Quotes with id: {}", id);
        Quotes deletedQuotes = quotesService.delete(id);
        return deletedQuotes != null;
    }

    /**
     * @deprecated Use {@link #findQuotes(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Quotes instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Quotes> findQuotes(Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Quotes list");
        return quotesService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the list of Quotes instances matching the search criteria.")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Quotes> findQuotes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Quotes list");
        return quotesService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportQuotes(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        return quotesService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns the total count of Quotes instances.")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Long countQuotes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
        LOGGER.debug("counting Quotes");
        return quotesService.count(query);
    }

    @RequestMapping(value = "/{id:.+}/followUpses", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the followUpses instance associated with the given id.")
    public Page<FollowUps> findAssociatedFollowUpses(@PathVariable("id") Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated followUpses");
        return quotesService.findAssociatedFollowUpses(id, pageable);
    }

    @RequestMapping(value = "/{id:.+}/saleses", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the saleses instance associated with the given id.")
    public Page<Sales> findAssociatedSaleses(@PathVariable("id") Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated saleses");
        return quotesService.findAssociatedSaleses(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service QuotesService instance
	 */
    protected void setQuotesService(QuotesService service) {
        this.quotesService = service;
    }
}

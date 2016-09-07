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
import com.salesdb.Quotes;
import com.salesdb.Reps;
import com.salesdb.Tasks;
import com.salesdb.service.RepsService;

/**
 * Controller object for domain model class Reps.
 * @see Reps
 */
@RestController("salesdb.RepsController")
@Api(value = "RepsController", description = "Exposes APIs to work with Reps resource.")
@RequestMapping("/salesdb/Reps")
public class RepsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepsController.class);

    @Autowired
    @Qualifier("salesdb.RepsService")
    private RepsService repsService;

    @ApiOperation(value = "Creates a new Reps instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Reps createReps(@RequestBody Reps reps) {
        LOGGER.debug("Create Reps with information: {}", reps);
        reps = repsService.create(reps);
        LOGGER.debug("Created Reps with information: {}", reps);
        return reps;
    }

    @ApiOperation(value = "Returns the Reps instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Reps getReps(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Reps with id: {}", id);
        Reps foundReps = repsService.getById(id);
        LOGGER.debug("Reps details with id: {}", foundReps);
        return foundReps;
    }

    @ApiOperation(value = "Updates the Reps instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Reps editReps(@PathVariable("id") Integer id, @RequestBody Reps reps) throws EntityNotFoundException {
        LOGGER.debug("Editing Reps with id: {}", reps.getId());
        reps.setId(id);
        reps = repsService.update(reps);
        LOGGER.debug("Reps details with id: {}", reps);
        return reps;
    }

    @ApiOperation(value = "Deletes the Reps instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteReps(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Reps with id: {}", id);
        Reps deletedReps = repsService.delete(id);
        return deletedReps != null;
    }

    /**
     * @deprecated Use {@link #findReps(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Reps instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Reps> findReps(Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Reps list");
        return repsService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the list of Reps instances matching the search criteria.")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Reps> findReps(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Reps list");
        return repsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportReps(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        return repsService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns the total count of Reps instances.")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Long countReps(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
        LOGGER.debug("counting Reps");
        return repsService.count(query);
    }

    @RequestMapping(value = "/{id:.+}/taskses", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the taskses instance associated with the given id.")
    public Page<Tasks> findAssociatedTaskses(@PathVariable("id") Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated taskses");
        return repsService.findAssociatedTaskses(id, pageable);
    }

    @RequestMapping(value = "/{id:.+}/quoteses", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the quoteses instance associated with the given id.")
    public Page<Quotes> findAssociatedQuoteses(@PathVariable("id") Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated quoteses");
        return repsService.findAssociatedQuoteses(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service RepsService instance
	 */
    protected void setRepsService(RepsService service) {
        this.repsService = service;
    }
}

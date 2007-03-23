/*
 * Copyright 2004-2005 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package org.codehaus.groovy.grails.commons;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import groovy.lang.GroovyClassLoader;

import java.util.Map;

/**
 *  <p>Exposes all classes for a Grails application.
 * 
 * @author Steven Devijver
 * @author Graeme Rocher
 *
 * @since 0.1
 *
 * Created: Jul 2, 2005
 */
public interface GrailsApplication extends ApplicationContextAware {
    /**
     * The id of the grails application within a bean context
     */
    String APPLICATION_ID = "grailsApplication";
    /**
     * Constant used to resolve the environment via System.getProperty(ENVIRONMENT)
     */
    String ENVIRONMENT = "grails.env";
    /**
     * Constant for the development environment
     */
    String ENV_DEVELOPMENT = "development";
    /**
     * Constant for the application data source, primarly for backward compatability for those applications
     * that use ApplicationDataSource.groovy
     */
    String ENV_APPLICATION = "application";
    
    /**
     * Constant for the production environment
     */
	String ENV_PRODUCTION = "production";

    /*
     * Constant for the test environment
     */
    String ENV_TEST  = "test";

    /**
     * <p>Returns the active data source for this Grails application or null if not available.
     *
     * @return the active data source or null if not available.
     */
    public GrailsDataSource getGrailsDataSource();

    /**
     * <p>Returns the class loader instance for the Grails application</p>
     *
     * @return The GroovyClassLoader instance
     */
    public GroovyClassLoader getClassLoader();

    /**
     * Retrieves the controller that is scaffolding the specified domain class
     *
     * @param domainClass The domain class to check
     * @return An instance of GrailsControllerClass                                      
     */
    GrailsControllerClass getScaffoldingController(GrailsDomainClass domainClass);

	/**
	 * Retrieves all java.lang.Class instances loaded by the Grails class loader
	 * @return An array of classes
	 */
	public Class[] getAllClasses();

	/**
	 * Retrieves all java.lang.Class instances considered Artefacts loaded by the Grails class loader
	 * @return An array of classes
	 */
	public Class[] getAllArtefacts();

	/**
	 * 
	 * @return The parent application context
	 */
	ApplicationContext getParentContext();

	/**
	 * Retrieves a class for the given name within the GrailsApplication or returns null
	 * 
	 * @param className The name of the class 
	 * @return The class or null
	 */
	public Class getClassForName(String className);


    /**
     * This method will rebuild the constraint definitions
     * @todo move this out? Why ORM dependencies in here?
     */
    public void refreshConstraints();

    /**
     * This method will refresh the entire application
     */
    public void refresh();

    /**
     * Retrieves a Resource instance for the given Grails class or null it doesn't exist
     *
     * @param theClazz The Grails class
     * @return A Resource or null
     */
    public Resource getResourceForClass(Class theClazz);

    /**
     * <p>Call this to find out if the class you have is an artefact loaded by grails.</p>
     * @param theClazz A class to test
     * @return True if and only if the class was loaded from grails-app/
     * @since 0.5
     */
    public boolean isArtefact(Class theClazz);

    /**
     * <p>Check if the specified artefact Class has been loaded by Grails already AND is
     * of the type expected</p>
     * @param artefactType A string identifying the artefact type to check for
     * @param theClazz The class to check
     * @return True if Grails considers the class to be managed as an artefact of the type specified.
     * @since 0.5
     */
    public boolean isArtefactOfType(String artefactType, Class theClazz);

    /**
     * <p>Check if the artefact Class with the name specified is of the type expected</p>
     * @param artefactType A string identifying the artefact type to check for
     * @param className The name of a class to check
     * @return True if Grails considers the class to be managed as an artefact of the type specified.
     * @since 0.5
     */
    public boolean isArtefactOfType(String artefactType, String className);

    /**
     * <p>Gets the GrailsClass associated with the named artefact class</p>
     * <p>i.e. to get the GrailsClass for  controller called "BookController" you pass the name "BookController"</p>
     * @param artefactType The type of artefact to retrieve, i.e. "Controller"
     * @param name The name of an artefact such as "BookController"
     * @return The associated GrailsClass or null
     * @since 0.5
     */
    public GrailsClass getArtefact(String artefactType, String name);

    /**
     * <p>Obtain all the class information about the artefactType specified</p>
     * @param artefactType An artefact type identifier i.e. "Domain"
     * @return The artefact info or null if the artefactType is not recognized
     * @since 0.5
     */
    public ArtefactInfo getArtefactInfo(String artefactType);

    /**
     * <p>Get an array of all the GrailsClass instances relating to artefacts of the specified type.</p>
     * @param artefactType The type of artefact to retrieve, i.e. "Task"
     * @return An array of GrailsClasses which may empty by not null
     * @since 0.5
     */
    public GrailsClass[] getArtefacts(String artefactType);

    /**
     * <p>Get an artefact GrailsClass by a "feature" which depending on the artefact may be a URI or tag name
     * for example</p>
     * @param artefactType The type ID of the artefact, i.e. "TagLib"
     * @param featureID The "feature" ID, say a URL or tag name
     * @return The grails class or null if none is found
     * @since 0.5
     */
    public GrailsClass getArtefactForFeature(String artefactType, Object featureID);

    /**
     * <p>Registers a new artefact</p>
     * @param artefactType The type ID of the artefact, i.e. "TagLib"
     * @param artefactClass The class of the artefact. A new GrailsClass will be created automatically and added
     * to internal structures, using the appropriate ArtefactHandler
     * @return The new grails class for the artefact class
     * @since 0.5
     */
    public GrailsClass addArtefact(String artefactType, Class artefactClass);

    /**
     * <p>Registers a new artefact</p>
     * @param artefactType The type ID of the artefact, i.e. "TagLib"
     * @param artefactGrailsClass The GrailsClass of the artefact.
     * @return The supplied grails class for the artefact class
     * @since 0.5
     */
    public GrailsClass addArtefact(String artefactType, GrailsClass artefactGrailsClass);

    /**
     * <p>Register a new artefact handler</p>
     * @param handler The new handler to add
     */
    public void registerArtefactHandler(ArtefactHandler handler);

    /**
     * <p>Obtain a list of all the artefact handlers</p>
     * @return The list, possible empty but not null, of all currently registered handlers
     */
    public ArtefactHandler[] getArtefactHandlers();

	public void initialise();

    /**
     * <p>Get access to the project's metadata, specified in application.properties</p>
     * <p>This provides access to information like required grails version, application name, version etc
     * but <b>NOT</b> general application settings.</p>
     * @return A read-only Map of data about the application, not environment specific
     */
    public Map getMetadata();

}

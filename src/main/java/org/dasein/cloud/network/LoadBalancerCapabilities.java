/*
 * Copyright (C) 2014 Dell, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dasein.cloud.network;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * <p>Created by George Reese: 2/27/14 3:01 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public interface LoadBalancerCapabilities extends Capabilities{
    /**
     * Indicates the type of load balancer supported by this cloud.
     * @return the load balancer type
     * @throws org.dasein.cloud.CloudException
     *          an error occurred with the cloud provider while performing this action
     * @throws org.dasein.cloud.InternalException
     *          an error occurred within the Dasein Cloud implementation while performing this action
     */
    public @Nonnull LoadBalancerAddressType getAddressType() throws CloudException, InternalException;

    /**
     * @return the maximum number of public ports on which the load balancer can listen
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnegative int getMaxPublicPorts() throws CloudException, InternalException;

    /**
     * Gives the cloud provider's term for a load balancer (for example, "ELB" in AWS).
     * @param locale the locale for which the term should be translated
     * @return the provider term for a load balancer
     */
    public @Nonnull String getProviderTermForLoadBalancer(@Nonnull Locale locale);

    /**
     * Indicates whether a health check can be created independantly of a load balancer
     * @return false if a health check can exist without having been assigned to a load balancer
     * @throws CloudException
     * @throws InternalException
     */
    public boolean healthCheckRequiresLoadBalancer() throws CloudException, InternalException;

    /**
     * @return the degree to which endpoints should or must be part of the load balancer creation process
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Requirement identifyEndpointsOnCreateRequirement() throws CloudException, InternalException;

    /**
     * Indicates the degree to which listeners should or must be specified when creating a load balancer.
     * @return the degree to which listeners must be specified during load balancer creation
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Requirement identifyListenersOnCreateRequirement() throws CloudException, InternalException;

    /**
     * @return whether or not you are expected to provide an address as part of the create process or one gets assigned
     *         by the provider
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean isAddressAssignedByProvider() throws CloudException, InternalException;

    /**
     * Indicates whether or not VM endpoints for this load balancer should be constrained to specific data centers in
     * its region. It should be false for load balancers handling non-VM endpoints or load balancers that are free
     * to balance across any data center. When a load balancer is data-center limited, the load balancer tries to
     * balance
     * traffic equally across the data centers. It is therefore up to you to try to keep the data centers configured
     * with equal capacity.
     * @return whether or not VM endpoints are constrained to specific data centers associated with the load balancer
     * @throws CloudException    an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean isDataCenterLimited() throws CloudException, InternalException;

    /**
     * Lists the load balancing algorithms from which you can choose when setting up a load balancer listener.
     * @return a list of one or more supported load balancing algorithms
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LbAlgorithm> listSupportedAlgorithms() throws CloudException, InternalException;

    /**
     * Describes what kind of endpoints may be added to a load balancer.
     * @return a list of one or more supported endpoint types
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LbEndpointType> listSupportedEndpointTypes() throws CloudException, InternalException;

    /**
     * Lists all IP protocol versions supported for load balancers in this cloud.
     * @return a list of supported versions
     * @throws CloudException    an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     */
    public @Nonnull Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException;

    /**
     * Lists the various options for session stickiness with load balancers in this cloud.
     * @return a list of one or more load balancer persistence options for session stickiness
     * @throws CloudException    an error occurred checking support for IP versions with the cloud provider
     * @throws InternalException a local error occurred preparing the supported version
     */
    public @Nonnull Iterable<LbPersistence> listSupportedPersistenceOptions() throws CloudException, InternalException;

    /**
     * Lists the network protocols supported for load balancer listeners.
     * @return a list of one or more supported network protocols for load balancing
     * @throws CloudException    an error occurred while communicating with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public @Nonnull Iterable<LbProtocol> listSupportedProtocols() throws CloudException, InternalException;

    /**
     * Indicates whether or not endpoints may be added to or removed from a load balancer once the load balancer has
     * been created.
     * @return true if you can modify the endpoints post-create
     * @throws CloudException    an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean supportsAddingEndpoints() throws CloudException, InternalException;

    /**
     * Indicates whether or not the underlying cloud monitors the balanced endpoints and provides health status
     * information.
     * @return true if monitoring is supported
     * @throws CloudException    an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean supportsMonitoring() throws CloudException, InternalException;

    /**
     * Indicates whether a single load balancer is limited to either IPv4 or IPv6 (false) or can support both IPv4 and
     * IPv6 traffic (true)
     * @return true if a load balancer can be configured to support simultaneous IPv4 and IPv6 traffic
     * @throws CloudException    an error occurred with the cloud provider while performing this action
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing this action
     */
    public boolean supportsMultipleTrafficTypes() throws CloudException, InternalException;
}

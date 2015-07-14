package org.dasein.cloud.container;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;

/**
 * A null implementation of the Dasein Cloud Container Support. Implementation classes override selected
 * methods to indicate support for specific parts of the Container Support.
 * @author Stas Maksimov (stas.maksimov@software.dell.com)
 * @since 2015.09
 */
public interface ContainerSupport {

    /**
     * Indicates whether this account is subscribed to using containers.
     *
     * @return true if the subscription is valid for using virtual machines
     * @throws CloudException    an error occurred querying the cloud for subscription info
     * @throws InternalException an error occurred within the implementation determining subscription state
     */
    boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Provides access to meta-data about container capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    @Nonnull ContainerCapabilities getCapabilities() throws CloudException, InternalException;

    @Nonnull Iterable<Cluster> listClusters() throws CloudException, InternalException;

    @Nonnull Iterable<Scheduler> listSchedulers() throws CloudException, InternalException;

    @Nonnull String createCluster(@Nonnull String clusterName) throws CloudException, InternalException;

    void removeCluster(@Nonnull String providerClusterId) throws CloudException, InternalException;

    @Nonnull String createScheduler(@Nonnull SchedulerCreateOptions opts) throws CloudException, InternalException;

    void removeScheduler(@Nonnull String providerSchedulerId) throws CloudException, InternalException;
}
